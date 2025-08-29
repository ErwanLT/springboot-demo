# Tutoriel : Intégration d'Elasticsearch avec Spring Boot

Ce tutoriel a pour but de montrer comment intégrer Elasticsearch dans une application Spring Boot en utilisant Spring Data Elasticsearch. Nous allons construire une petite application qui permet de gérer des livres (créer, rechercher, supprimer) et de les stocker dans un index Elasticsearch.

## Qu'est-ce qu'Elasticsearch ?

Elasticsearch est un moteur de recherche et d'analyse distribué, open-source, basé sur Apache Lucene. Il est connu pour sa vitesse, sa scalabilité et sa capacité à gérer de grands volumes de données en temps réel. Il est souvent utilisé pour des cas d'usage comme la recherche de texte, la journalisation (logging), le monitoring et l'analyse de données.

## 1. Configuration du Projet

Pour commencer, nous avons besoin d'ajouter la dépendance Spring Data Elasticsearch à notre projet. 

### Dépendances Maven

Voici la dépendance nécessaire dans le fichier `pom.xml` :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

### Configuration de la Connexion

Ensuite, nous devons configurer la connexion à notre instance Elasticsearch dans le fichier `src/main/resources/application.properties` :

```properties
spring.application.name=elasticsearch-tutorial

server.port=8100

spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.username=elastic
spring.elasticsearch.password=changeme
spring.elasticsearch.ssl.verification-mode=none
```

### Activation des Repositories

Enfin, nous activons les repositories Elasticsearch dans notre classe principale avec l'annotation `@EnableElasticsearchRepositories`.

```java
@SpringBootApplication
@EnableElasticsearchRepositories
public class ElasticSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
```

## 2. Modèle de Données (Document)

Nous allons créer un modèle `Book` qui représente un document dans notre index Elasticsearch. 

L'annotation `@Document(indexName = "books")` indique que cette classe est mappée à un index nommé `books`. L'annotation `@Mapping` permet de spécifier un fichier de mapping pour définir la structure de l'index.

```java
package fr.eletutour.elastic.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "books")
@Mapping(mappingPath = "/elasticsearch/mappings/books.json")
public class Book {

    @Id
    private String id;

    @NotNull
    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String content;

    @NotNull
    @Field(type = FieldType.Keyword)
    private String author;

    // Getters et Setters...
}
```

Le fichier de mapping `src/main/resources/elasticsearch/mappings/books.json` définit les types des champs dans l'index :

```json
{
  "properties": {
    "id": { "type": "keyword" },
    "title": { "type": "text", "analyzer": "standard" },
    "content": { "type": "text", "analyzer": "standard" },
    "author": { "type": "keyword" }
  }
}
```

## 3. Repository Spring Data

Spring Data Elasticsearch simplifie grandement l'accès aux données. Il suffit de créer une interface qui étend `ElasticsearchRepository`. Spring Data se charge de l'implémentation.

Nous pouvons y définir des méthodes de recherche personnalisées. Spring Data déduit la requête à partir du nom de la méthode.

```java
package fr.eletutour.elastic.repository;

import fr.eletutour.elastic.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    List<Book> findByTitleContainingOrContentContaining(String title, String content);

    List<Book> findByAuthor(String author);
}
```

## 4. Service Métier

Le service contient la logique métier de notre application. Il utilise le `BookRepository` pour interagir avec Elasticsearch.

```java
package fr.eletutour.elastic.service;

import fr.eletutour.elastic.model.Book;
import fr.eletutour.elastic.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingOrContentContaining(query, query);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
```

## 5. Contrôleur REST

Pour finir, nous exposons notre fonctionnalité via une API REST. Le `BookController` gère les requêtes HTTP et utilise le `BookService` pour effectuer les opérations.

```java
package fr.eletutour.elastic.controller;

import fr.eletutour.elastic.model.Book;
import fr.eletutour.elastic.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @DeleteMapping("/{id}")
    public void deleteBooks(@PathVariable String id) {
        bookService.deleteBook(id);
    }
}
```

## Conclusion

Avec Spring Boot et Spring Data Elasticsearch, l'intégration d'un moteur de recherche puissant dans une application Java devient très simple. En quelques étapes, nous avons mis en place une application complète capable d'indexer et de rechercher des documents.
