---
layout: default
title: OpenAPI
---

# Générer vos clients d'API à partir de leur spécification OpenAPI
Dans le développement d’applications modernes, les [API REST](https://www.sfeir.dev/rest-definition/) jouent un rôle central pour permettre la communication entre différents services. Cependant, écrire manuellement des clients pour interagir avec ces API peut être fastidieux et source d’erreurs.  
C’est là qu’interviennent des outils comme le plugin Maven `openapi-generator-maven-plugin`, basé sur la spécification [OpenAPI](https://www.sfeir.dev/back/migrer-de-swagger-2-a-openapi-3/). Cet article explore pourquoi et comment utiliser ce plugin, ses avantages et inconvénients, ainsi qu’un cas pratique pour illustrer son application.

## Pourquoi générer ses clients d’API ?

La génération automatique de clients d’API repose sur une idée simple : transformer une spécification formelle (comme un fichier OpenAPI au format JSON ou YAML) en code prêt à l’emploi. Plutôt que de coder à la main des appels HTTP avec des bibliothèques comme `RestTemplate` ou `HttpClient`, ou de définir manuellement des modèles de données, les développeurs peuvent s’appuyer sur des outils qui produisent ce code de manière cohérente et rapide.  
Cela répond à plusieurs besoins :

- **Uniformité** : Une spécification OpenAPI agit comme un contrat entre le producteur et le consommateur de l’API, garantissant que le client généré correspond exactement aux endpoints et aux modèles définis.
- **Gain de temps** : Éviter de réécrire du code répétitif permet de se concentrer sur la logique métier.
- **Maintenance simplifiée** : Si l’API évolue, il suffit de régénérer le client à partir de la spécification mise à jour.

## ⚖️ Avantages et inconvénients

### ➕Les avantages

L’utilisation d’un générateur comme `openapi-generator-maven-plugin` offre plusieurs atouts :

- **Productivité accrue** : Le plugin génère des interfaces, des modèles et parfois des implémentations en quelques secondes, éliminant des heures de travail manuel.
- **Compatibilité avec les frameworks** : Il prend en charge des bibliothèques populaires comme **Spring** (avec [spring-boot](https://www.sfeir.dev/tag/spring-boot/) ou spring-cloud pour OpenFeign), ce qui facilite son intégration dans des projets existants.
- **Personnalisation** : Grâce à des options de configuration et des templates personnalisés, il est possible d’adapter le code généré à des besoins spécifiques.
- **Fiabilité** : Le code produit est basé sur une spécification validée, réduisant les risques d’erreurs humaines comme des fautes de frappe ou des incohérences dans les types de données.
- **Évolutivité** : Les mises à jour de l’API nécessitent simplement une nouvelle génération, sans réécrire tout le code.

### ➖ Les inconvénients

Malgré ses nombreux avantages, le plugin présente aussi quelques limites :

- **Dépendance à la spécification** : Si le fichier OpenAPI est mal conçu ou incomplet, le code généré sera imparfait. Cela nécessite une validation rigoureuse en amont.
- **Complexité initiale** : La configuration du plugin peut être intimidante pour les débutants, avec de nombreuses options à comprendre (choix du générateur, gestion des dépendances, etc.).
- **Dépendances supplémentaires** : Le code généré peut introduire des bibliothèques comme `jackson-databind-nullable` ou `feign`, ce qui augmente la taille du projet et peut compliquer la gestion des versions.
- **Manque de flexibilité** : Si des besoins spécifiques ne sont pas couverts par les templates par défaut, il faut créer des templates personnalisés, ce qui demande un effort supplémentaire.
- **Performance** : Pour des projets très simples, la génération peut sembler excessive par rapport à une implémentation manuelle légère.

## Le plugin Maven `openapi-generator-maven-plugin`

Le plugin Maven `openapi-generator-maven-plugin`, développé par **OpenAPITools**, est un outil puissant pour générer des clients, des serveurs ou des modèles à partir de spécifications OpenAPI.  
Intégré au cycle de vie de Maven, il s’exécute typiquement durant la phase generate-sources, produisant du code dans un dossier comme target/generated-sources.

[![](https://www.sfeir.dev/content/images/thumbnail/openapi-generator)](https://github.com/OpenAPITools/openapi-generator/tree/master?ref=sfeir.dev)
Projet GitHub du plugin maven

### Configuration de base

Voici un exemple minimal dans un `pom.xml` :

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.4.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/api.yaml</inputSpec>
                <generatorName>spring</generatorName>
                <apiPackage>com.example.api</apiPackage>
                <modelPackage>com.example.model</modelPackage>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Exemple de configuration

- `<inputSpec>` : Chemin vers le fichier OpenAPI.
- `<generatorName>` : Type de génération (ex. spring, java, typescript-axios).
- `<apiPackage>` et `<modelPackage>` : Packages pour les interfaces et modèles générés.

Le plugin supporte de nombreuses options, comme `<library>` pour choisir une implémentation spécifique (ex. spring-cloud pour OpenFeign) ou `<configOptions>` pour des réglages fins.

### Templating avec Mustache

Un atout majeur du plugin est sa capacité à personnaliser le code généré grâce à des fichiers de template **Mustache**.  
Par défaut, il utilise des templates prédéfinis pour chaque générateur, mais vous pouvez les surcharger en spécifiant un répertoire via `<templateDirectory>`.  
Ces fichiers Mustache (avec l’extension `.mustache`) permettent de modifier la structure du code généré, comme ajouter des annotations personnalisées ou ajuster les conventions de nommage.  
Par exemple, pour modifier le template d’une interface Spring, vous pouvez copier le template par défaut depuis le dépôt GitHub d’OpenAPI Generator, le personnaliser, et l’indiquer dans la configuration. Cela offre une flexibilité immense, bien que cela demande une bonne compréhension des variables disponibles dans le contexte Mustache.

## Cas pratique

Imaginons un projet Spring Boot qui doit consommer une API de gestion de livres. La spécification OpenAPI (books.json) définit des endpoints comme `GET /books/{authorId}` et `POST /books`. Voici comment utiliser le plugin :

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.12.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/swagger/books.json</inputSpec>
                <generatorName>spring</generatorName>
                <configOptions>
                    <sourceFolder>src/gen/main/java</sourceFolder>
                    <configPackage>fr.eletutour.clientgenerationtutorial.author.config</configPackage>
                    <dateLibrary>java8</dateLibrary>
                    <unhandledException>true</unhandledException>
                    <developerOrganization>books</developerOrganization>
                    <useTags>true</useTags>
                    <interfaceOnly>false</interfaceOnly>
                    <useResponseEntity>false</useResponseEntity>
                    <useJakartaEe>true</useJakartaEe>
                </configOptions>
                <library>spring-cloud</library>
                <apiPackage>fr.eletutour.book.consumer.api</apiPackage>
                <modelPackage>fr.eletutour.books.consumer.model</modelPackage>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Analyse de la configuration

Cette configuration utilise la version **7.12.0** (la dernière à l'heure actuelle) du plugin pour générer un client Spring compatible avec **OpenFeign** (`<library>spring-cloud>`).  
Le fichier _books.json_ dans `src/main/resources/swagger` sert de spécification. Les options `<configOptions>` permettent des réglages précis :

- `<sourceFolder>` place le code généré dans `src/gen/main/java`, intégré comme source dans le projet.
- `<useJakartaEe>true` assure la compatibilité avec Jakarta EE (annotations `jakarta.validation` au lieu de `javax.validation`), essentiel pour Spring Boot 3.x.
- `<dateLibrary>java8` utilise les API de date Java 8 (LocalDate, etc.).
- `<interfaceOnly>false` génère des implémentations complètes, tandis que `<useResponseEntity>false` évite d’envelopper les réponses dans ResponseEntity.
- Les packages (apiPackage, modelPackage, configPackage) organisent le code dans une structure claire.

### Utilisation dans le code

- **Génération** : Exécutez `mvn generate-sources`. Cela crée une interface `BookManagementApi` [annotée](https://www.sfeir.dev/back/comprendre-les-annotations-dans-spring-boot/) avec `@FeignClient` dans `fr.eletutour.book.consumer.api`.
- **Service Spring** :

```java
@Service
public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;
    private final BookManagementApi bookManagementApiClient;
    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    public AuthorService(AuthorRepository authorRepository, BookManagementApi bookManagementApiClient) {
        this.authorRepository = authorRepository;
        this.bookManagementApiClient = bookManagementApiClient;
    }

    /**
     * Récupère un auteur par son ID.
     * @param id ID de l'auteur
     * @return Auteur correspondant
     * @throws AuthorException si l'auteur n'est pas trouvé
     */
    public Author getAuthorById(Long id) throws AuthorException {
        logger.info("Récupération de l'auteur avec l'ID {}", id);
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Auteur non trouvé pour l'ID {}", id);
                    return new AuthorException(AuthorException.AuthorError.AUTHOR_NOT_FOUND, "Auteur non trouvé pour l'ID : " + id);
                });
        Author author = authorMapper.toAuthor(authorEntity);
        return enrichAuthorWithBooks(author);
    }


    /**
     * Enrichit un auteur avec ses livres en appelant l'API externe.
     * @param author Auteur à enrichir
     * @return Auteur avec la liste des livres
     */
    private Author enrichAuthorWithBooks(Author author) {
        try {
            logger.debug("Récupération des livres pour l'auteur avec l'ID {}", author.getId());
            List<Book> books = bookManagementApiClient.getBooksByAuthorId(author.getId());
            author.setBooks(books);
            logger.debug("Ajout de {} livres à l'auteur avec l'ID {}", books.size(), author.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des livres pour l'auteur avec l'ID {}", author.getId(), e);
            throw new RuntimeException("Erreur lors de la récupération des livres pour l'auteur " + author.getId(), e);
        }
        return author;
    }
}
```

- **Configuration** : Ajoutez `@EnableFeignClients` sur votre classe principale Spring Boot

```java
@SpringBootApplication
@EnableFeignClients
public class AuthorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorDemoApplication.class, args);
    }
}
```

### Résultat

Le client généré appelle l’API via OpenFeign, et les modèles (comme Book) sont automatiquement mappés depuis le JSON. Si la spécification change, un simple `mvn clean generate-sources` met à jour le client.

## Conclusion

L'utilisation d'un générateur de clients REST basé sur OpenAPI offre un gain de temps considérable en automatisant la création du code, tout en réduisant les risques d'erreurs humaines. Grâce à cet outil, il devient plus simple de maintenir la cohérence entre le client et le serveur, même lorsque l'API évolue.

Dans un projet Spring Boot, OpenAPI Generator s'intègre parfaitement avec des solutions modernes comme `Feign`, facilitant la communication avec des services distants tout en adoptant les bonnes pratiques actuelles.

Bien entendu, cette approche ne convient pas à tous les cas d’usage. Pour des API très simples ou peu sujettes à des évolutions, un client manuel basé sur `RestTemplate`, `WebClient` ou `Feign` peut être une alternative viable. Cependant, pour des systèmes complexes où la synchronisation avec un contrat OpenAPI est essentielle, l'utilisation d'un générateur devient un véritable atout.