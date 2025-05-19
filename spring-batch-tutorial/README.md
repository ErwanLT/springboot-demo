# Tutoriel Spring Batch

Ce tutoriel montre comment implémenter un traitement par lots avec Spring Batch pour gérer l'inactivation automatique des utilisateurs inactifs.

## À propos de Spring Batch

Spring Batch est un framework léger et complet pour le développement d'applications de traitement par lots. Il fournit des fonctionnalités essentielles pour :
- Le traitement de grands volumes de données
- L'exécution de tâches planifiées
- La gestion des transactions
- Le suivi et la reprise des traitements

## Fonctionnalités implémentées

- Job de désactivation des utilisateurs inactifs
- Lecture des utilisateurs actifs depuis la base de données
- Traitement des utilisateurs selon leur dernière connexion
- Écriture des modifications en base de données
- Planification automatique du job

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── config/        # Configuration du batch
│   │       ├── entities/      # Entités JPA
│   │       ├── repository/    # Repositories Spring Data
│   │       └── Main.java      # Point d'entrée
│   └── resources/
│       └── application.yml    # Configuration
```

## Configuration du Batch

```java
@Configuration
public class BatchConfiguration {

    @Bean
    public Job inactivateUsersJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("inactivateUsersJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ItemReader<User> reader, ItemProcessor<User, User> processor,
                      ItemWriter<User> writer) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<User> reader(UserRepository userRepository) {
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

    @Bean
    public ItemProcessor<User, User> processor() {
        return user -> {
            if (user.getLastLoginDate().isBefore(LocalDateTime.now().minusMonths(6))) {
                user.setStatus("inactive");
            }
            return user;
        };
    }

    @Bean
    public ItemWriter<User> writer(UserRepository userRepository) {
        return userRepository::saveAll;
    }
}
```

## Planification du Job

```java
@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job inactivateUsersJob;

    @Scheduled(cron = "*/10 * * * * ?")
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(inactivateUsersJob, jobParameters);
    }
}
```

## Modèle de données

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

    // Constructeurs, getters et setters
}
```

## Installation

1. Clonez ce dépôt
2. Naviguez dans le répertoire `spring-batch-tutorial`
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Fonctionnement

1. Le job s'exécute toutes les 10 secondes (configurable)
2. Il lit les utilisateurs actifs de la base de données
3. Pour chaque utilisateur :
   - Vérifie si la dernière connexion date de plus de 6 mois
   - Si oui, marque l'utilisateur comme inactif
4. Sauvegarde les modifications en base de données

## Bonnes pratiques

1. **Performance** :
   - Utiliser le traitement par chunks
   - Configurer la taille des chunks selon les besoins
   - Optimiser les requêtes de lecture
   - Gérer la mémoire efficacement

2. **Robustesse** :
   - Implémenter la gestion des erreurs
   - Configurer les reprises sur erreur
   - Logger les étapes importantes
   - Surveiller les performances

3. **Maintenance** :
   - Documenter les jobs et steps
   - Externaliser les paramètres
   - Mettre en place des métriques
   - Tester les scénarios critiques

## Ressources supplémentaires

- [Documentation Spring Batch](https://docs.spring.io/spring-batch/docs/current/reference/html/index.html)
- [Guide Spring Batch](https://spring.io/guides/gs/batch-processing/)
- [Spring Batch Reference](https://docs.spring.io/spring-batch/reference/)
