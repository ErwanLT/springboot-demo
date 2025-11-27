# Gérer des Requêtes Dynamiques avec Spring Boot et les Sealed Interfaces

Dans le développement d'API REST, il est courant de rencontrer des scénarios où un point d'entrée unique doit pouvoir accepter des charges utiles (payloads) JSON de structures différentes. Par exemple, un système de commande pourrait recevoir des commandes passées en ligne et des commandes effectuées en magasin, chacune avec des informations spécifiques.

Ce tutoriel explore une solution élégante pour gérer ce type de corps de requête dynamique en utilisant les **Sealed Interfaces** de Java et les fonctionnalités de **polymorphisme de Jackson** dans une application Spring Boot.

## Le Problème : Un Endpoint, Plusieurs Structures

Imaginons un endpoint `POST /orders`. Ce endpoint doit pouvoir créer une commande, que ce soit :
1.  Une **commande en ligne**, caractérisée par l'email du client et son adresse de livraison.
2.  Une **commande en magasin**, identifiée par l'ID du magasin et un indicateur pour une préparation express.

Plutôt que de créer deux endpoints distincts (`/orders/online` et `/orders/store`), nous souhaitons n'en conserver qu'un seul pour simplifier le contrat d'API.

## La Solution : Polymorphisme avec Jackson

La clé de notre solution réside dans la manière dont nous allons indiquer à Jackson comment désérialiser le JSON entrant dans le bon objet Java. Pour cela, nous utilisons une interface "scellée" (`sealed interface`) qui servira de contrat de base pour toutes nos commandes.

### 1. Le Contrat : La `Command` Sealed Interface

Nous définissons une interface `Command`. Le mot-clé `sealed` nous assure que seules les classes déclarées dans `permits` pourront l'implémenter, ce qui renforce la robustesse de notre modèle.

Grâce aux annotations Jackson, nous mettons en place le mécanisme de polymorphisme :
- `@JsonTypeInfo`: Indique que l'information de type est contenue dans le JSON lui-même, via une propriété nommée `type`.
- `@JsonSubTypes`: Énumère les correspondances entre les valeurs de la propriété `type` (ex: "online") and les classes Java concrètes (ex: `OnlineCommand.class`).

```java
// integration/dynamic-body-tutorial/src/main/java/fr/eletutour/dynamic/dto/Command.java

package fr.eletutour.dynamic.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OnlineCommand.class, name = "online"),
        @JsonSubTypes.Type(value = StoreCommand.class, name = "store")
})
public sealed interface Command
        permits OnlineCommand, StoreCommand {
}
```

### 2. Les Implémentations Concrètes

Nos deux types de commandes sont implémentés sous forme de `records` Java, ce qui nous offre une syntaxe concise pour des objets de données immuables.

**Commande en ligne (`OnlineCommand`)**
```java
// integration/dynamic-body-tutorial/src/main/java/fr/eletutour/dynamic/dto/OnlineCommand.java

package fr.eletutour.dynamic.dto;

public record OnlineCommand(
        String email,
        String deliveryAddress
) implements Command {
}
```

**Commande en magasin (`StoreCommand`)**
```java
// integration/dynamic-body-tutorial/src/main/java/fr/eletutour/dynamic/dto/StoreCommand.java

package fr.eletutour.dynamic.dto;

public record StoreCommand(
        String storeId,
        boolean express
) implements Command {
}
```

### 3. Le Point d'Entrée : `OrderController`

Le contrôleur devient remarquablement simple. Le endpoint `createOrder` attend un `Command` en `@RequestBody`. Spring Boot et Jackson s'occupent de la désérialisation, et nous n'avons pas à nous soucier du type concret à ce niveau.

Les annotations SpringDoc (`@Operation`, etc.) sont utilisées pour générer une documentation d'API claire, qui reflète bien la nature polymorphique de l'objet attendu.

```java
// integration/dynamic-body-tutorial/src/main/java/fr/eletutour/dynamic/controller/OrderController.java

@RestController
@RequestMapping("/orders")
public class OrderController {
    // ... constructeur

    @Operation(...)
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody Command command) {
        Long id = service.createOrder(command);
        return ResponseEntity.ok(id);
    }
}
```

### 4. La Logique Métier : `OrderService` et le Pattern Matching

C'est dans la couche service que la "magie" opère. Grâce aux `sealed interfaces`, nous pouvons utiliser le *pattern matching* du `switch` de Java. C'est une manière moderne, sûre et lisible de gérer les différents cas, sans avoir recours à des `if/else` et des `instanceof` fastidieux.

Le code est non seulement plus propre, mais le compilateur peut aussi vérifier que tous les cas (`OnlineCommand` et `StoreCommand`) sont bien traités.

```java
// integration/dynamic-body-tutorial/src/main/java/fr/eletutour/dynamic/service/OrderService.java

@Service
public class OrderService {
    // ...

    public Long createOrder(Command command) {
        OrderEntity entity = new OrderEntity();

        switch (command) {
            case OnlineCommand online -> {
                logger.info("Reception d'une commande en ligne");
                entity.setType("online");
                entity.setPayload(serialize(online));
            }
            case StoreCommand store -> {
                logger.info("Reception d'une commande en magasin");
                entity.setType("store");
                entity.setPayload(serialize(store));
            }
        }

        return repository.save(entity).getId();
    }
    // ...
}
```

## Exemples d'Utilisation avec cURL

Voici comment vous pouvez appeler l'API avec les deux types de charges utiles.

**Créer une commande en ligne :**
```shell
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{
  "type": "online",
  "email": "customer@example.com",
  "deliveryAddress": "123 Main St, Anytown"
}'
```

**Créer une commande en magasin :**
```shell
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{
  "type": "store",
  "storeId": "STORE-456",
  "express": true
}'
```

## Conclusion

En combinant la puissance des `sealed interfaces` de Java pour la modélisation et les annotations de polymorphisme de Jackson, nous avons créé une API robuste, propre et facile à maintenir qui gère des structures de données complexes avec un point d'entrée unique. L'ajout du *pattern matching* dans la couche service rend le traitement des différents cas à la fois sûr et élégant.