# Comment personnaliser la bannière d'un projet Spring Boot
## La bannière Spring par défaut

La bannière Spring Boot est un élément distinctif de Spring Boot, un framework Java populaire utilisé pour créer des applications web. La bannière est affichée au démarrage de chaque application Spring Boot et contient le logo Spring Boot, le nom de la version et un message personnalisable.

```text
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.6)
```

Mais saviez-vous que cette bannière est désactivable, voir même personnalisable ? Et que c'est très simple à mettre à place ?  
Non ? Et bien voyons ça.

## Désactiver la bannière

Si jamais vous ne voulez pas voir la bannière apparaitre au démarrage de l'application il existe plusieurs solutions que je vous detaillerais ici :

- depuis le fichier **application.properties**
- depuis la **méthode main** de notre application
- depuis le **fichier banner.txt** des resources du projet

### Depuis le fichier de properties

Pour désactiver la bannière Spring Boot depuis le fichier de propriétés de votre application, il ne vous faudra ajouter qu'une seule ligne à l'intérieur

```properties
spring.main.banner-mode=off
```

Si jamais votre fichier est au format **yaml**, ce n'est pas bien compliqué non plus

```yaml
spring:
  main:
    banner-mode: 'off'
```

#### Depuis le main de l'application

Pour désactiver la bannière Spring Boot depuis **la méthode main** de votre application il vous faut modifier la ligne de run pour passer de ça :

```java
SpringApplication.run(MonApp.class, args);
```

à ceci :

```java
new SpringApplicationBuilder(SpringbootBannerApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
```

### Depuis le fichier banner.txt

Pour désactiver la bannière Spring Boot depuis le fichier banner.txt vous avez 2 choix :

- Si le fichier existe, le supprimer du répertoire des ressources de l'application ou en le renommant. Spring Boot détecte automatiquement la présence du fichier **banner.txt** et l'affiche au démarrage de l'application. En supprimant ou en renommant ce fichier, Spring Boot ne trouvera plus la bannière et ne l'affichera donc pas.
- Si le fichier n'existe pas, le créer dans le repertoire des resources et le laisser vide. Au démarrage de l'application, le fichier sera detecté et son contenu sera affiché, ici rien car le fichier sera vide.

## Personaliser la bannière Spring Boot

Par défaut Spring Boot va scanner les fichiers présent dans votre répertoire de resource à la recherche d'un fichier nommé **banner.txt** et utilisera son contenu pour généré la bannière au démarrage de l'application.  
Sachez cependant que le fichier contenant votre bannière peut tout aussi bien être un fichier de type image qui sera à ce moment là convertie en ASCII avant d'être affiché (seulement version antérieur à Spring Boot 3).

### Le fichier banner.txt

Nous avons vu précédement que nous pouvions créer un fichier **banner.txt** dans le répertoire resources de notre projet et le laisser vide afin de désactiver la bannière, mais il est également possible de lui ajouter du contenu qui sera par la suite afficher.

Par exemple si je rajoute :

```text
 Oh no not again !
     (")
     \)/
    _____
    \___/

                      Hello ground !!
        .-------------'```'----....,,__                        _,
       |                               `'`'`'`'-.,.__        .'(
       |                                             `'--._.'   )
       |                                                   `'-.<
       \               .-'`'-.                            -.    `\
        \               -.o_.     _                     _,-'`\    |
         ``````''--.._.-=-._    .'  \            _,,--'`      `-._(
           (^^^^^^^^`___    '-. |    \  __,,..--'                 `
            `````````   `'--..___\    |`
                                  `-.,'

```

Dans mon fichier et que je relance mon application, je verrais en console le résultat suivant :  
![Capture-d-e-cran-2023-05-05-a--21.58.00](https://www.sfeir.dev/content/images/2023/05/Capture-d-e-cran-2023-05-05-a--21.58.00.png)

Vous pouvez aussi ajoutez des styles de police, ainsi que de la couleur dans votre fichier en ajoutant en début de lignes :

- ${AnsiStyle.STYLE_NAME} : BOLD / ITALIC / NORMAL ...
- ${AnsiColor.COLOR_NAME} : BLUE / RED / YELLOW ...
- ${AnsiBackground.COLOR_NAME}

Ce qui permet d'avoir au démarrage un résultat comme suit :  
![Capture-d-e-cran-2023-05-05-a--22.03.42](https://www.sfeir.dev/content/images/2023/05/Capture-d-e-cran-2023-05-05-a--22.03.42.png)

Attention cependant à l'utilisation des variable de style, car si vous ne précisez pas de retour à la normal, le style continuera de s'appliquer.

### Utiliser une image comme bannière

Jusqu'à la version 3 de Spring Boot, il était possible d'utiliser une image comme bannière.  
Cette dernière était convertie en ASCII avant le démarrage de l'application ce qui pouvait prendre du temps en fonction de la taille de l'image, et le rendu n'était pas forcément très comprehensible.  
Pour ce faire il fallait placer l'image souhaité dans le répertoire resource du projet, et ajouter la ligne de properties suivante :

```
spring.banner.image.location=classpath:monImage.png|jpg|gif
```

ce qui donnerait un resultat comme celui-ci  
![1_i8hz-NZtXJqZSV--DLZQDg](https://www.sfeir.dev/content/images/2023/05/1_i8hz-NZtXJqZSV--DLZQDg.webp)  
Notez cependant que les couleurs sont plus ou moins conservées lors de la transformation.