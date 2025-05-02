---
layout: default
title: Basic Auth
---

# Sécurisez vos API avec Spring Security : Basic Auth
Précédemment, vous avez pu suivre le tutoriel suivant qui explique comment créer et exposer une [API REST](https://www.sfeir.dev/rest-definition/) rapidement grâce à [Spring Boot](https://www.sfeir.dev/back/back-spring-boot/):

[Comment créer son projet Spring Boot de zéro !](https://www.sfeir.dev/back/creer-son-projet-spring-boot-de-zero/)

Et avez ainsi créé une première API.

Cette fois, nous allons aborder un aspect essentiel de toute API : **sa sécurisation**. En effet, sans mesures de sécurité adéquates, vos données peuvent être exposées à des accès non autorisés ou des attaques malveillantes.

Dans cette série d'articles, nous verrons comment sécuriser une API avec **Spring Security** et différentes méthodes d’authentification, ici il s'agira de **Basic Auth**.

## Pourquoi sécuriser une API ?

Une API REST est souvent le point d’entrée des données sensibles de votre application. Si elle n’est pas protégée, elle devient vulnérable à :

- L’accès non autorisé.
- Les attaques comme le **man-in-the-middle**, **brute force** ou **injection**.
- La divulgation de données sensibles.

[![](https://www.sfeir.dev/content/images/2024/11/D7A1882C-0B16-49FF-B6C0-4A97B6D7E74E_1_105_c.jpeg)](https://www.sfeir.dev/content/images/2024/11/D7A1882C-0B16-49FF-B6C0-4A97B6D7E74E_1_105_c.jpeg)

Tentative de brute force en cours

## Définition de **Basic Auth**

**Basic Auth** (ou **Basic Authentication**) est une méthode simple d'authentification HTTP où le client (comme un navigateur ou une application) transmet des informations d'identification (nom d'utilisateur et mot de passe) au serveur dans chaque requête.

Ces informations sont encodées en **Base64** et envoyées dans l'en-tête HTTP `Authorization`. L'encodage Base64 ne fournit aucune sécurité réelle, il s'agit simplement d'un format d'encodage lisible, et non de chiffrement. Voici un exemple d'en-tête HTTP utilisant Basic Auth :

```makefile
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
```

Dans cet exemple :

- `dXNlcm5hbWU6cGFzc3dvcmQ=` est l'encodage Base64 de `username:password`.

## Cas pratique : Ajouter Spring Security

Ajoutez les dépendances suivantes de **Spring Security** dans votre fichier `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!-- Pour les tests -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

## Notre API à sécuriser

Pour cet exemple, nous allons rester simple, nous allons avoir une API avec 2 points d'entrée :

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/goodbye")
    public String sayGoodbye() {
        return "GoodBye world!";
    }
}
```

Le premier `/hello` retournera la chaine de caractères "Hello World!" et le second `/goodbye` retournera quant à lui "GoodBye world!"

## Comportement par défaut

Par défaut, à partir du moment où vous avez rajouté la dépendance de spring-security, vous n'avez déjà plus accès librement à vos API.  
En effet si j'exécute la requête suivante [http://localhost:8083/hello](http://localhost:8083/hello?ref=sfeir.dev) via Postman / [Bruno](https://www.sfeir.dev/back/use-bruno/) ou n'importe quels autres outils de requêtage le système me renverra un code retour HTTP 401.

[![](https://www.sfeir.dev/content/images/2024/11/image-23.png)](https://www.sfeir.dev/content/images/2024/11/image-23.png)

requête en erreur

Mais alors, comment tester le résultat de ma requête ?  
La sécurité par défaut qui est mise en place lors de l'ajout de la dépendance est le **Basic Auth**, avec comme username par défaut user et un mot de passe généré aléatoirement que vous pouvez trouver dans les logs de votre application au démarrage de cette dernière avec un petit message d'avertissement.

```log
Using generated security password: 2849f6e7-4406-45fb-ae3c-0b8a091d499b

This generated password is for development use only. Your security configuration must be updated before running your application in production.
```

[![](https://www.sfeir.dev/content/images/2024/11/image-24.png)](https://www.sfeir.dev/content/images/2024/11/image-24.png)

C'est tout de suite mieux

## Configurer l’authentification avec Basic Auth

Une méthode simple pour sécuriser une API est d'utiliser **Basic Auth**, où chaque requête contient un en-tête avec un nom d’utilisateur et un mot de passe encodés en base 64.

Créez une classe `SecurityConfig` :

```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/goodbye").permitAll()
                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user1 = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        var user2 = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Notre classe de configuration

Analysons de plus près ce que fait cette classe

### securityFilterChain

Cette méthode configure le **SecurityFilterChain**, qui est une chaîne de filtres définissant les règles de sécurité pour gérer les requêtes HTTP.

Détails de la configuration :

- **Désactivation de CSRF :**

```java
.csrf(AbstractHttpConfigurer::disable)
```

Le mécanisme **CSRF (Cross-Site Request Forgery)** est désactivé ici. Ce mécanisme est utile pour les applications web avec sessions, mais souvent inutile pour les API REST.

- **Règles d'autorisation des requêtes :**

```java
.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/goodbye").permitAll()
                                .anyRequest().authenticated())
```

- Les requêtes vers le chemin `/goodbye` sont **publiques** et ne nécessitent pas d'authentification (`permitAll()`).
- Toutes les autres requêtes (`anyRequest()`) nécessitent que l'utilisateur soit authentifié (`authenticated()`).

- **Utilisation de Basic Auth :**

```java
.httpBasic(Customizer.withDefaults())
```

Cela active l'authentification **Basic Auth**, qui demande des identifiants (nom d'utilisateur et mot de passe) dans chaque requête via l'en-tête HTTP `Authorization`.

### UserDetailService

Le `UserDetailsService` est une interface de Spring Security utilisée pour récupérer les détails des utilisateurs nécessaires à l'authentification et à l'autorisation.  
Il joue **un rôle central** dans la gestion de la sécurité de l'application.

Cette interface ne contient qu'une seule méthode

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```

- Cette méthode est appelée par Spring Security pour récupérer les informations d'un utilisateur donné (en fonction de son nom d'utilisateur).
- Les informations retournées sont encapsulées dans un objet `UserDetails`, qui contient des détails sur l'utilisateur, comme :
    - Nom d'utilisateur
    - Mot de passe
    - Rôles/autorités
    - État actif ou inactif de l'utilisateur

Dans notre exemple, mes utilisateurs sont enregistrés dans un gestionnaire en mémoire (`InMemoryUserDetailsManager`).  
C'est une implémentation simple qui ne nécessite pas de base de données, idéal pour des démo ou des tests.  
Pour des applications réelles, les utilisateurs sont souvent stockés dans une base de données. et il vous reviendra donc la charge d'implémenter votre propre **UserDetailService**.

## Pour résumer

Nous avons configuré la sécurité de notre application de la façon suivante :

- Les requêtes vers le chemin `/goodbye` sont **publiques** et ne nécessitent pas d'authentification.
- Toutes les autres requêtes nécessitent que l'utilisateur soit authentifié.

[![](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.00.png)](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.00.png)

[![](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.14.png)](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.14.png)

[![](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.50.png)](https://www.sfeir.dev/content/images/2024/11/Capture-d-e-cran-2024-11-27-a--19.24.50.png)

résultat de nos requêtes

## Pour conclure

Dans cet article, nous avons vu comment sécuriser une API REST avec Spring Security en utilisant Basic Auth. Cette méthode, bien que simple à mettre en œuvre, constitue une première étape pour protéger vos points d'entrée contre les accès non autorisés.

Cette configuration est **idéale pour démarrer**, mais dans une application en production, **plusieurs améliorations doivent être envisagées**, telles que :

- L'utilisation d'une base de données pour stocker les utilisateurs et leurs rôles.
- La mise en place d'un chiffrement HTTPS pour protéger les données échangées.
- Le remplacement de Basic Auth par des mécanismes plus sécurisés comme OAuth2 ou JWT.

[![](https://www.sfeir.dev/content/images/2024/11/0687369F-FD32-4F29-8249-A94B0C8FD612_1_105_c.jpeg)](https://www.sfeir.dev/content/images/2024/11/0687369F-FD32-4F29-8249-A94B0C8FD612_1_105_c.jpeg)

Oh non, notre sécurité n'était pas suffisante ! :o

En sécurisant votre API, vous assurez non seulement la confidentialité et l'intégrité de vos données, mais vous renforcez également la confiance des utilisateurs dans votre application.  
Dans les prochains articles, nous explorerons d'autres méthodes d'authentification et verrons comment adapter Spring Security à des scénarios plus avancés.