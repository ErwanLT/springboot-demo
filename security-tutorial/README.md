# Tutoriels de Sécurité avec Spring Boot

Ce projet contient une série de tutoriels sur l'implémentation de différentes stratégies de sécurité dans une application Spring Boot.

## Tutoriels disponibles

### [Basic Auth](basic-auth-tutorial)
L'authentification basique est la méthode la plus simple pour sécuriser une API. Ce tutoriel couvre :
- Configuration de Spring Security
- Implémentation de l'authentification basique
- Gestion des utilisateurs en mémoire
- Sécurisation des endpoints
- Bonnes pratiques de sécurité

### [JWT](jwt-tutorial)
Les JSON Web Tokens (JWT) offrent une solution moderne et stateless pour l'authentification. Ce tutoriel explique :
- Génération et validation des JWT
- Configuration de Spring Security avec JWT
- Gestion des tokens d'accès et de rafraîchissement
- Sécurisation des endpoints avec JWT
- Gestion de l'expiration des tokens

### [RBAC avec JWT](jwt-rbac-tutorial)
Le contrôle d'accès basé sur les rôles (RBAC) combiné avec JWT permet une gestion fine des autorisations. Ce tutoriel détaille :
- Implémentation du RBAC
- Gestion des rôles et permissions
- Intégration avec JWT
- Sécurisation basée sur les rôles
- Gestion des autorisations

### [LDAP](ldap-tutorial)
L'intégration avec LDAP permet une authentification centralisée. Ce tutoriel montre :
- Configuration de Spring Security avec LDAP
- Connexion à un serveur LDAP
- Gestion des utilisateurs LDAP
- Mapping des rôles LDAP
- Bonnes pratiques d'intégration

## Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- Spring Boot 3.x
- IDE (IntelliJ IDEA recommandé)

## Installation
1. Clonez ce dépôt
2. Naviguez dans le répertoire souhaité (basic-auth-tutorial, jwt-tutorial, jwt-rbac-tutorial, ou ldap-tutorial)
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Structure du projet
```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── config/         # Configuration de sécurité
│   │       ├── model/          # Entités et DTOs
│   │       ├── controller/     # Contrôleurs REST
│   │       ├── service/        # Services métier
│   │       └── security/       # Classes de sécurité
│   └── resources/
│       └── application.yml    # Configuration
```

## Exemple de configuration de sécurité
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
```

## Bonnes pratiques
1. **Sécurité générale** :
   - Utiliser HTTPS en production
   - Implémenter la protection CSRF
   - Configurer les en-têtes de sécurité
   - Gérer les sessions de manière sécurisée

2. **Authentification** :
   - Utiliser des mots de passe forts
   - Implémenter la limitation des tentatives
   - Gérer l'expiration des sessions
   - Mettre en place la déconnexion

3. **Autorisation** :
   - Suivre le principe du moindre privilège
   - Valider les entrées utilisateur
   - Logger les actions sensibles
   - Mettre en place des audits

## Ressources supplémentaires
- [Documentation Spring Security](https://docs.spring.io/spring-security/reference/)
- [Guide JWT](https://jwt.io/introduction)
- [LDAP avec Spring](https://spring.io/guides/gs/authenticating-ldap/)
- [OWASP Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Spring_Security_Cheat_Sheet.html)