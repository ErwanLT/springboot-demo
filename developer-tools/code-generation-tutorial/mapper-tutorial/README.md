# Mapstruct tutorial

## Installation

Pour utiliser MapStruct dans votre projet Maven, ajoutez la dépendance suivante et configurez le plugin `maven-compiler-plugin` pour activer le processeur d'annotations. Voici un exemple de configuration dans votre fichier `pom.xml` :

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

Une fois configuré, MapStruct permet de mapper des objets avec un minimum d'effort, tout en offrant des options avancées pour personnaliser les conversions si nécessaire.
## Avantages et Inconvénients
### Avantages
- Gain de temps : MapStruct élimine le besoin d'écrire manuellement des mappers, réduisant ainsi le code répétitif (boilerplate).
- Performance : Le code étant généré à la compilation, il n'y a pas de surcharge à l'exécution, contrairement aux solutions basées sur la réflexion (comme Dozer).
- Lisibilité et maintenance : Les interfaces de mappage sont claires et faciles à comprendre, facilitant la maintenance du code.
- Flexibilité : Supporte des cas complexes comme le mappage de plusieurs objets sources, la gestion des propriétés non mappées ou l'intégration de méthodes personnalisées.
- Intégration avec les IDE : Les implémentations générées sont visibles dans /target/generated-sources/annotations/, permettant une inspection facile.

### Inconvénients
- Courbe d'apprentissage : Bien que simple dans les cas de base, les fonctionnalités avancées (expressions, décorateurs) nécessitent un temps d'adaptation.
- Dépendance au build : La génération de code repose sur la compilation, ce qui peut compliquer le débogage en cas de mauvaise configuration.
- Moins adapté aux mappages dynamiques : Conçu pour des mappages statiques, il ne convient pas aux règles de mappage changeant à l'exécution.

## Exemples
Voici des exemples concrets illustrant différentes fonctionnalités de MapStruct.
### Mappage simple
Imaginons deux classes : Client (source) et ClientDTO (destination) :
```java
public class Client {
    private String nom;
    private String prenom;
    // Constructeur + getters et setters
}

public class ClientDTO {
    private String nom;
    private String prenom;
    // Constructeur + getters et setters
}
```

Définissons une interface de mappage avec l'annotation @Mapper :
```java
@Mapper
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}
```
Lors de la compilation, MapStruct génère une implémentation automatique :
```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T07:45:34+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class ClientMapperImpl implements ClientMapper {
    @Override
    public ClientDTO toDto(Client client) {
        if (client == null) return null;
        String nom = client.getNom();
        String prenom = client.getPrenom();
        return new ClientDTO(nom, prenom);
    }

    @Override
    public Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) return null;
        String nom = clientDTO.getNom();
        String prenom = clientDTO.getPrenom();
        return new Client(nom, prenom);
    }
}
```
### Utilisation d’un autre mapper
Ajoutons des informations d’adresse :
```java
public class Adresse {
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;
    // Constructeur + getters et setters
}

public class AdresseDTO {
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;
    // Constructeur + getters et setters
}

public class Client {
    private String nom;
    private String prenom;
    private Adresse adresse;
    // Constructeur + getters et setters
}

public class ClientDTO {
    private String nom;
    private String prenom;
    private AdresseDTO adresse;
    // Constructeur + getters et setters
}
```
Deux options pour le mapper :
- Mapper tout dans une seule interface :

```java
@Mapper
public interface ClientMapper {
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
    AdresseDTO toAdresseDto(Adresse adresse);
    Adresse toAdresse(AdresseDTO adresseDTO);
}
```
- Utiliser un mapper séparé pour les adresses :

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
L’implémentation générée utilise alors AdresseMapper :
```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T07:59:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class ClientMapperImpl implements ClientMapper {
    private final AdresseMapper adresseMapper = Mappers.getMapper(AdresseMapper.class);

    @Override
    public ClientDTO toDto(Client client) {
        if (client == null) return null;
        String nom = client.getNom();
        String prenom = client.getPrenom();
        AdresseDTO adresse = adresseMapper.toDto(client.getAdresse());
        return new ClientDTO(nom, prenom, adresse);
    }
    // Méthode toEntity similaire
}
```
### Utilisation d’une méthode Java dans @Mapping
Pour concaténer nom et prenom en nomComplet :
```java
public class ClientDTO {
    private String nom;
    private String prenom;
    private String nomComplet;
    private AdresseDTO adresse;
    // Constructeur + getters et setters
}

@Mapper(uses = {AdresseMapper.class})
public interface ClientMapper {
    @Mapping(target = "nomComplet", expression = "java(client.getNom() + \" \" + client.getPrenom())")
    ClientDTO toDto(Client client);
}
```
Implémentation générée :
```java
@Override
public ClientDTO toDto(Client client) {
    if (client == null) return null;
    String nom = client.getNom();
    String prenom = client.getPrenom();
    AdresseDTO adresse = adresseMapper.toDto(client.getAdresse());
    String nomComplet = client.getNom() + " " + client.getPrenom();
    return new ClientDTO(nom, prenom, nomComplet, adresse);
}
```
### Méthode personnalisée dans le mapper
Pour formater une date :
```java
public class Commande {
    private String id;
    private Date dateCreation;
    // Getters et setters
}

public class CommandeDTO {
    private String id;
    private String dateFormatee;
    // Getters et setters
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
Implémentation générée :
```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T08:19:58+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (GraalVM Community)"
)
public class CommandeMapperImpl implements CommandeMapper {
    @Override
    public CommandeDTO toCommandeDTO(Commande commande) {
        if (commande == null) return null;
        String id = commande.getId();
        String dateFormatee = formatDate(commande.getDateCreation());
        return new CommandeDTO(id, dateFormatee);
    }
}
```
