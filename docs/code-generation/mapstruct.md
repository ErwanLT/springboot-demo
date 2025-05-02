---
layout: default
title: MapStruct
---

# MapStruct : Dites adieu au code répétitif et boostez vos mappages Java !
Dans le développement d'applications [Java](https://www.sfeir.dev/back/il-etait-une-fois-java/), la conversion d'objets d'un type à un autre est une tâche courante mais souvent fastidieuse.  
Que ce soit pour transformer des entités persistantes en objets de transfert de données (DTO) ou pour mapper des données entre différentes couches d'une application, les développeurs passent beaucoup de temps à écrire du code répétitif et sujet aux erreurs.  
C'est dans ce contexte que **MapStruct** entre en jeu. Cette [bibliothèque open-source](https://www.sfeir.dev/tag/opensource/) offre une solution efficace et automatisée pour gérer les mappages entre beans Java.  
Dans cet article, nous allons explorer ce qu'est **MapStruct**, ses avantages et inconvénients, ainsi que des exemples concrets d'utilisation, avant de conclure sur son intérêt dans les projets modernes.

## Présentation de MapStruct

MapStruct est une bibliothèque Java qui simplifie la création de mappers entre des beans (objets Java simples avec des propriétés, getters et setters). Contrairement à d'autres outils qui fonctionnent à l'exécution (runtime), MapStruct génère du code source lors de la compilation. Le principe est simple : le développeur définit une interface contenant les signatures des méthodes de mappage, et **MapStruct** se charge de créer automatiquement une implémentation concrète à partir de ces définitions.

[

GitHub - mapstruct/mapstruct: An annotation processor for generating type-safe bean mappers

An annotation processor for generating type-safe bean mappers - mapstruct/mapstruct

![](https://www.sfeir.dev/content/images/icon/pinned-octocat-093da3e6fa40-20.svg)GitHubmapstruct

![](https://www.sfeir.dev/content/images/thumbnail/mapstruct)

](https://github.com/mapstruct/mapstruct?ref=sfeir.dev)

### Installation

Pour utiliser MapStruct, il suffit d'ajouter la dépendance Maven appropriée dans votre projet et de configurer le plugin `maven-compiler-plugin` pour activer le processeur d'annotations.  
Par exemple :

```xml
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.6.3</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.14.0</version>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.6.3</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Exemple de fichier pom.xml

Une fois configuré, MapStruct permet de mapper des objets avec un minimum d'effort, tout en offrant des options avancées pour personnaliser les conversions si nécessaire.

## ⚖️ Avantages et inconvénients

### ➕ Avantages

- **Gain de temps** : MapStruct élimine le besoin d'écrire manuellement des mappers, réduisant ainsi le code répétitif (boilerplate).
- **Performance** : Comme le code est généré à la compilation, il n'y a pas de surcharge à l'exécution, contrairement aux solutions basées sur la réflexion (comme Dozer).
- **Lisibilité et maintenance** : Les interfaces de mappage sont claires et faciles à comprendre, ce qui facilite la maintenance du code.
- **Flexibilité** : MapStruct supporte des cas complexes comme le mappage de plusieurs objets sources, la gestion des propriétés non mappées ou encore l'intégration de méthodes personnalisées.
- **Intégration avec les IDE** : Les implémentations générées sont visibles dans le répertoire `/target/generated-sources/annotations/`, ce qui permet de les inspecter facilement.

### ➖ Inconvénient

- **Courbe d'apprentissage** : Bien que simple dans les cas de base, MapStruct peut nécessiter un temps d'adaptation pour maîtriser ses fonctionnalités avancées (par exemple, les expressions ou les décorateurs).
- **Dépendance au build** : Le processus de génération du code repose sur la compilation, ce qui peut compliquer le débogage si la configuration est incorrecte.
- **Moins adapté aux mappages dynamiques** : MapStruct est conçu pour des mappages statiques définis à l'avance, et ne convient pas aux scénarios où les règles de mappage changent à l'exécution.

## Exemples

Voici des exemples concrets illustrant différentes fonctionnalités de MapStruct, y compris l'utilisation de mappers externes et de méthodes personnalisées.

### Mappage simple

imaginons deux classes : une classe source `Client` et une classe destination `ClientDTO`.

```java
public class Client {
    private String nom;
    private String prenom;

    //constructeur + getter et setter
}

public class ClientDTO {
    private String nom;
    private String prenom;

    //constructeur + getter et setter
}
```

On définit une interface de mappage avec [l'annotation](https://www.sfeir.dev/back/comprendre-les-annotations-dans-spring-boot/) `@Mapper` :

```java
@Mapper
public interface ClientMapper {
    ClientDTO toDto(Client client);

    Client toEntity(ClientDTO clientDTO);
}
```

Lors de la compilation, **MapStruct** génère une implémentation qui mappe automatiquement les propriétés correspondantes.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T07:45:34+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDTO toDto(Client client) {
        if ( client == null ) {
            return null;
        }

        String nom = null;
        String prenom = null;

        nom = client.getNom();
        prenom = client.getPrenom();

        ClientDTO clientDTO = new ClientDTO( nom, prenom );

        return clientDTO;
    }

    @Override
    public Client toEntity(ClientDTO clientDTO) {
        if ( clientDTO == null ) {
            return null;
        }

        String nom = null;
        String prenom = null;

        nom = clientDTO.getNom();
        prenom = clientDTO.getPrenom();

        Client client = new Client( nom, prenom );

        return client;
    }
}
```

### Utilisation d’un autre mapper

Supposons que nous voulons enrichir nos classes `Client` et `ClientDTO` en rajoutant les informations d'adresse de ces derniers :

```java
public class Adresse {
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;

    //constructeur + getter et setter
}

public class AdresseDTO {
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;

    //constructeur + getter et setter
}
```

```java
public class Client {
    private String nom;
    private String prenom;
    private Adresse adresse;

    //constructeur + getter et setter
}

public class ClientDTO {
    private String nom;
    private String prenom;
    private AdresseDTO adresse;

    //constructeur + getter et setter
}
```

J'ai alors deux solution pour modifier mon mapper :

- Soit je rajoute les méthodes pour mapper les adresses à l'intérieur

```java
@Mapper
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
    AdresseDTO toAdresseDto(Adresse adresse);
    Adresse toAdresse(AdresseDto adresseDTO);
}
```

- Soit je définis un autre mapper spécifique aux adresses et l'utilise alors dans mon mapper client :

```java
@Mapper
public interface AdresseMapper {
    AdresseDTO toDto(Adresse adresse);
    Adresse toEntity(AdresseDTO adresseDTO);
}

@Mapper(uses = {AdresseMapper.class})
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}
```

Maintenant dans l'implémentation généré de mon ClientMapper, il utilisera le mapper des adresses :

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T07:59:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class ClientMapperImpl implements ClientMapper {

    private final AdresseMapper adresseMapper = Mappers.getMapper( AdresseMapper.class );

    @Override
    public ClientDTO toDto(Client client) {
        if ( client == null ) {
            return null;
        }

        String nom = null;
        String prenom = null;
        AdresseDTO adresse = null;

        nom = client.getNom();
        prenom = client.getPrenom();
        adresse = adresseMapper.toDto( client.getAdresse() );

        ClientDTO clientDTO = new ClientDTO( nom, prenom, adresse );

        return clientDTO;
    }

    @Override
    public Client toEntity(ClientDTO clientDTO) {
        if ( clientDTO == null ) {
            return null;
        }

        String nom = null;
        String prenom = null;
        Adresse adresse = null;

        nom = clientDTO.getNom();
        prenom = clientDTO.getPrenom();
        adresse = adresseMapper.toEntity( clientDTO.getAdresse() );

        Client client = new Client( nom, prenom, adresse );

        return client;
    }
}
```

### Utilisation d’une méthode Java dans @Mapping

Pour des transformations plus complexes, MapStruct permet d’appeler des méthodes Java directement dans l’annotation `@Mapping`. Prenons un exemple où nous voulons concaténer **nom** et **prénom** dans **une seule propriété nomComplet**.

Modifions dans un premier temps notre classe ClientDTO :

```java
public class ClientDTO {
    private String nom;
    private String prenom;
    private String nomComplet;
    private AdresseDTO adresse;
    
    //constructeur + getter et setter
}
```

j'ai rajouté ici une propriété qui ne sera présente que dans cette classe, il faut donc maintenant indiquer à notre mapper comment l'alimenter :

```java
@Mapping(target = "nomComplet", expression = "java(client.getNom() + \" \" + client.getPrenom())")
ClientDTO toDto(Client client);
```

Ici, l’annotation @Mapping utilise une expression Java pour combiner nom et prénom avec un espace entre les deux. L'implémentation générée ressemble maintenant à ceci :

```java
@Override
public ClientDTO toDto(Client client) {
    if ( client == null ) {
        return null;
    }

    String nom = null;
    String prenom = null;
    AdresseDTO adresse = null;

    nom = client.getNom();
    prenom = client.getPrenom();
    adresse = adresseMapper.toDto( client.getAdresse() );

    String nomComplet = client.getNom() + " " + client.getPrenom();

    ClientDTO clientDTO = new ClientDTO( nom, prenom, nomComplet, adresse );

    return clientDTO;
}
```

### Méthode personnalisée dans le mapper

On peut aussi définir une méthode personnalisée directement dans l’interface et l’utiliser dans un `@Mapping`. Par exemple, pour formater une date :

```java
public class Commande {
    private String id;
    private Date dateCreation;
    // getters et setters
}

public class CommandeDTO {
    private String id;
    private String dateFormatee;
    // getters et setters
}

@Mapper
public interface CommandeMapper {
    @Mapping(target = "dateFormatee", source = "dateCreation", qualifiedByName = "formatDate")
    CommandeDTO toCommandeDTO(Commande commande);

    @Named("formatDate")
    default String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
```

Ici, la méthode formatDate est définie dans l’interface avec `@Named("formatDate")`, et elle est référencée dans `@Mapping` via **qualifiedByName**. MapStruct utilisera cette méthode pour transformer la Date en une chaîne formatée.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T08:19:58+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class CommandeMapperImpl implements CommandeMapper {

    @Override
    public CommandeDTO toCommandeDTO(Commande commande) {
        if ( commande == null ) {
            return null;
        }

        String dateFormatee = null;
        String id = null;

        dateFormatee = formatDate( commande.getDateCreation() );
        id = commande.getId();

        CommandeDTO commandeDTO = new CommandeDTO( id, dateFormatee );

        return commandeDTO;
    }
}
```

## Conclusion

MapStruct est une bibliothèque puissante et pratique pour tout développeur Java cherchant à automatiser les mappages entre objets.  
Son approche basée sur la génération de code à la compilation offre un excellent compromis entre performance, lisibilité et flexibilité.

Bien qu'elle présente quelques limitations, notamment pour les mappages dynamiques, elle excelle dans les cas d'usage les plus courants, comme le transfert entre entités et DTOs dans une architecture en couches.

En somme, MapStruct mérite sa place dans l'arsenal des outils modernes de développement Java, particulièrement pour les projets nécessitant une gestion efficace et maintenable des conversions d'objets.