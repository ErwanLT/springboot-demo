---
layout: default
title: Spring Batch
---

# Planifier des tâches avec Spring Batch
**Spring Batch** est une brique essentielle du framework **Spring** qui facilite la gestion des traitements par lots. Il permet de définir des processus en plusieurs étapes pour le traitement de grandes quantités de données.  
Dans cet article, nous allons voir comment implémenter un traitement par lot avec **Spring Batch** pour un cas concret : marquer comme "inactifs" les utilisateurs qui ne se sont pas connectés depuis un certain temps à une application.

## Pré requis

Avoir lu et suivit les différentes étapes de l'article suivant

[

Comment créer son projet Spring Boot de zéro !

Débutant ou expert, lancer un projet Spring Boot de zéro n’a jamais été aussi simple !

![](https://www.sfeir.dev/content/images/icon/plume-point-32.png)sfeir.dev - Le média incontournable pour les passionnés de tech et d'intelligence artificielleYves Dautremay

![](https://www.sfeir.dev/content/images/thumbnail/Capture-d-e-cran-2023-10-27-a--09.54.06-1.png)

](https://www.sfeir.dev/back/creer-son-projet-spring-boot-de-zero/)

## Ajouter les dépendances

Avant d’entrer dans le détail de l'implémentation du batch, il est important de bien comprendre les dépendances nécessaires et leur rôle. Voici les dépendances à ajouter dans le fichier `pom.xml` et une explication pour chacune d'elles.

### spring-boot-starter-batch

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-batch</artifactId>
</dependency>
```

dépendance spring boot starter batch

- **Pourquoi l'ajouter ?** Ce starter est essentiel pour activer **Spring Batch**, un module de Spring conçu pour traiter des tâches par lot (batch processing). Il fournit les outils pour gérer de manière efficace les opérations répétitives, comme le traitement de grandes quantités de données. Il contient des classes essentielles comme `Job`, `Step`, `ItemReader`, `ItemProcessor`, et `ItemWriter` que nous utiliserons pour définir notre traitement batch.
- **Quand l'utiliser ?** Dès que vous avez des tâches à exécuter en arrière-plan, de façon automatisée ou planifiée, et que ces tâches nécessitent un traitement en masse de données, Spring Batch est une solution robuste.

### spring-boot-starter-data-jpa

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

dépendance spring boot starter JPA

- **Pourquoi l'ajouter ?** Spring Data JPA est un autre module de Spring qui simplifie l'accès et la gestion des données relationnelles dans les applications Java. Nous l'utilisons ici pour interagir avec notre base de données et récupérer les utilisateurs. Grâce à JPA, il devient simple d'effectuer des requêtes SQL complexes via des interfaces en Java, sans avoir à écrire du SQL manuel.
- **Quand l'utiliser ?** Chaque fois que votre application interagit avec une base de données relationnelle, JPA vous permet de manipuler les entités en tant qu'objets Java sans vous soucier des détails de bas niveau liés à la gestion des connexions à la base de données.

### com.h2database:h2

```xml
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

dépendance base de données H2

- **Pourquoi l'ajouter ?** H2 est une base de données embarquée légère qui peut être utilisée pour le développement et les tests. Spring Batch utilise une base de données pour stocker l'état des jobs, comme les métadonnées, les étapes de jobs, l'avancement des traitements, et pour faciliter les reprises en cas d'échec.
- **Quand l'utiliser ?** Vous pouvez utiliser H2 en phase de développement et de test, car elle est simple à configurer et s'exécute entièrement en mémoire, sans avoir besoin de configuration externe. En production, vous pourriez utiliser une base de données plus robuste comme MySQL ou [PostgreSQL](https://www.sfeir.dev/tag/postgresql/).

#### ℹ️ Version antérieur de Spring Boot

## Configuration de la base de données

Spring Batch utilise une base de données pour stocker l'historique d'exécution des jobs et des étapes. Nous allons configurer H2, une base de données en mémoire, pour cet exemple.

Voici la configuration dans le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.batch.initialize-schema=always
```

Cette configuration permet à Spring de créer automatiquement les tables nécessaires au bon fonctionnement de Spring Batch et JPA.

Nous allons aussi créer un fichier `schema-all.sql` qui sera placé dans les resources du projet

```sql
DROP TABLE users IF EXISTS;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(20),
    status VARCHAR(20),
    last_login_date DATE
);

INSERT INTO users (username, status, last_login_date) VALUES
('alice', 'active', '2024-09-15'),
('bob', 'inactive', '2024-03-01'),
('charlie', 'active', '2024-08-20'),
('david', 'inactive', '2023-12-15'),
('eve', 'active', '2024-02-25'),
('frank', 'inactive', '2024-07-10'),
('grace', 'active', '2023-11-30');
```

script d'init de la BDD

ℹ️ Spring Boot exécute **schema-@@platform@@. sql** automatiquement au démarrage. **-all** est le paramètre par défaut pour toutes les plateformes.

## Définition de l'entité `User`

Maintenant que notre base de données est prête à être utilisée, il nous faut une entité JPA pour représenter les utilisateurs dans la base de données.

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    private String status;

    public User() {
    }

    public User(String username, LocalDateTime lastLoginDate, String status) {
        this.username = username;
        this.lastLoginDate = lastLoginDate;
        this.status = status;
    }

    //getter et setter

}
```

Entity user

## Création du Repository

Il nous faut également un repository pour interagir avec la base de données.

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatus(String active);
}
```

repository

## Configuration du Job Spring Batch

Tout le code suivant sera dans une classe `BatchConfiguration`

### Définition du Job

```java
@Bean
public Job inactivateUsersJob(JobRepository jobRepository, Step step1) {
    return new JobBuilder("inactivateUsersJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(step1)
            .build();
}
```

- Ce code définit un bean `Job`. Le job est nommé `"inactivateUsersJob"`, et il est configuré pour démarrer par le `step1` défini plus bas.
- `RunIdIncrementer` permet d'ajouter un identifiant unique à chaque exécution du job, ce qui est utile pour exécuter plusieurs fois le même job sans conflit.
- Un job est composé d'une ou plusieurs étapes (steps). Ici, nous commençons avec une seule étape.

### Définition du Step

```java
@Bean
public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                  ItemReader<User> reader, ItemProcessor<User, User> processor,
                  ItemWriter<User> writer) {
    log.info("inactivateUsersJob - step1 ");
    return new StepBuilder("step1", jobRepository)
            .<User, User>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
}
```

- Ici, nous définissons une étape du job (`step1`), qui se compose de trois parties essentielles : un lecteur (`reader`), un processeur (`processor`), et un écrivain (`writer`).
- Le type de traitement se fait par blocs (chunks). Cela signifie que Spring Batch lira 10 enregistrements à la fois (grâce à `chunk(10)`), les traitera via le processeur, et les écrira ensuite.
- `StepBuilder` permet de construire cette étape avec une gestion transactionnelle grâce à `PlatformTransactionManager`.

### Définition du Reader

```java
@Bean
public ItemReader<User> reader(UserRepository userRepository) {
    log.info("inactivateUsersJob - search users");
    return new ItemReader<User>() {
        List<User> activeUsers = userRepository.findByStatus("active");

        @Override
        public User read() {
            if (!activeUsers.isEmpty()) {
                return activeUsers.removeFirst();
            }
            return null;
        }
    };
}
```

- Le `reader` est responsable de récupérer les données d'entrée. Ici, il interroge la base de données pour récupérer les utilisateurs ayant le statut `"active"`.
- Le lecteur fonctionne en parcourant une liste d'utilisateurs actifs et retourne un utilisateur à chaque appel de la méthode `read()`. Quand la liste est vide, il renvoie `null`, ce qui indique à Spring Batch qu'il n'y a plus de données à traiter.
- Le `UserRepository` est utilisé pour interroger les utilisateurs actifs dans la base de données.

### Définition du Processor

```java
@Bean
public ItemProcessor<User, User> processor() {
    return user -> {
        if (user.getLastLoginDate().isBefore(LocalDateTime.now().minusMonths(6))) {
            log.info("user {} is inactive because last login date is {}", user.getUsername(), user.getLastLoginDate());
            user.setStatus("inactive");
        }
        return user;
    };
}
```

- Le `processor` est chargé de la logique métier. Il vérifie si un utilisateur ne s'est pas connecté depuis plus de six mois, et si c'est le cas, il modifie le statut de l'utilisateur à `"inactive"`.
- Chaque utilisateur traité est passé par le processeur avant d'être envoyé au `writer`. Ce mécanisme permet d'appliquer des transformations ou des validations sur les données.

### Définition du Writer

```java
@Bean
public ItemWriter<User> writer(UserRepository userRepository) {
    log.info("inactivateUsersJob - save users");
    return userRepository::saveAll;
}
```

- Le `writer` est responsable de l'enregistrement des données traitées. Ici, il utilise le `UserRepository` pour sauvegarder tous les utilisateurs dans la base de données.
- Spring Batch accumule les utilisateurs traités dans des blocs de 10 (comme spécifié dans le `chunk`) et appelle ensuite le `writer` pour les persister.

## Configuration du Scheduler

Maintenant que notre job est configuré et prêt à l'usage, il faut maintenant configurer à quelle fréquence il sera exécuter, et pour ce faire il nous faut un `Scheduler`.

```java
@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job inactivateUsersJob;

    public BatchScheduler(JobLauncher jobLauncher, Job inactivateUsersJob) {
        this.jobLauncher = jobLauncher;
        this.inactivateUsersJob = inactivateUsersJob;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())  // Paramètre unique pour chaque exécution
                .toJobParameters();
        jobLauncher.run(inactivateUsersJob, jobParameters);
    }
}
```

### Planification avec `@Scheduled`

[L'annotation](https://www.sfeir.dev/back/comprendre-les-annotations-dans-spring-boot/) `@Scheduled(cron = "*/10 * * * * ?")` indique que la méthode `runJob()` sera exécutée toutes les 10 secondes. Le format cron utilisé ici signifie :

- `*/10` : Toutes les 10 secondes (dans un vrai contexte métier, jouer ce job toute les 10 secondes n'aurait pas grand intérêt, il faudrait plutôt le jouer 1 fois par jour)
- `* * * * ?` : Chaque minute, chaque heure, chaque jour, chaque mois, indépendamment du jour de la semaine.

Il faut aussi penser à ajouter **@EnableScheduling** dans votre classe main.

Il ne nous reste plus qu'a démarrer notre application et voir ce que nous avons en console.

```log
2024-10-01T18:36:06.587+02:00  INFO 31998 --- [demo] [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=inactivateUsersJob]] launched with the following parameters: [{'run.id':'{value=1, type=class java.lang.Long, identifying=true}'}]
2024-10-01T18:36:06.613+02:00  INFO 31998 --- [demo] [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2024-10-01T18:36:06.629+02:00  INFO 31998 --- [demo] [           main] c.e.demo.config.BatchConfiguration       : user eve is inactive because last login date is 2024-02-25T00:00
2024-10-01T18:36:06.629+02:00  INFO 31998 --- [demo] [           main] c.e.demo.config.BatchConfiguration       : user grace is inactive because last login date is 2023-11-30T00:00
2024-10-01T18:36:06.673+02:00  INFO 31998 --- [demo] [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 59ms
2024-10-01T18:36:06.681+02:00  INFO 31998 --- [demo] [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=inactivateUsersJob]] completed with the following parameters: [{'run.id':'{value=1, type=class java.lang.Long, identifying=true}'}] and the following status: [COMPLETED] in 77ms
```

## Conclusion

**Spring Batch** offre une solution pour gérer les traitements par lots, qu'il s'agisse de petites ou de grandes quantités de données.  
Dans cet article, nous avons montré comment configurer un job batch pour marquer les utilisateurs inactifs en fonction de leur dernière connexion.  
En combinant les fonctionnalités de [Spring Boot](https://www.sfeir.dev/back/back-spring-boot/), **Spring Batch**, et JPA, nous avons pu orchestrer facilement un traitement en plusieurs étapes, tout en bénéficiant d'une gestion simplifiée de la base de données et d'une planification automatique des jobs.

Ce tutoriel constitue une base solide pour implémenter des processus batch plus complexes, que ce soit pour le traitement de données en masse, des tâches répétitives ou l'automatisation de certains workflows en entreprise.  
**Spring Batch** permet non seulement de gérer les traitements lourds, mais également d'assurer une fiabilité accrue grâce à sa gestion intégrée des transactions, des erreurs et des reprises. Vous êtes maintenant prêts à explorer davantage de scénarios où ce puissant framework pourrait simplifier vos développements.