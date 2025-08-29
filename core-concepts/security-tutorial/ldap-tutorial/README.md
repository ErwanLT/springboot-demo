# Tutoriel : Authentification LDAP avec Spring Security

Ce tutoriel a pour but de montrer comment intégrer une authentification **LDAP (Lightweight Directory Access Protocol)** dans une application Spring Boot en utilisant Spring Security.

## Description

L'authentification LDAP permet de déléguer la gestion des utilisateurs et des mots de passe à un annuaire d'entreprise existant. Spring Security offre une intégration transparente avec les serveurs LDAP, permettant de sécuriser une application sans avoir à gérer les utilisateurs localement.

Ce module est un exemple de base montrant les dépendances et la configuration nécessaires.

## Dépendances Clés

Pour activer l'authentification LDAP, les dépendances suivantes sont incluses dans le `pom.xml` :

- `spring-boot-starter-data-ldap` : Fournit le support de base pour l'accès aux données LDAP.
- `spring-security-ldap` : Contient les classes d'intégration pour Spring Security.
- `unboundid-ldapsdk` : Une bibliothèque SDK pour interagir avec les serveurs LDAP. Spring Boot en démarre un en mémoire pour les tests si aucune configuration n'est fournie.

## Configuration

Voici un exemple typique de la configuration requise pour se connecter à un serveur LDAP externe.

### 1. Fichier `application.yml`

Il faut configurer les détails de connexion à votre serveur LDAP :

```yaml
spring:
  ldap:
    urls: ldap://your-ldap-server.com:389/
    username: cn=admin,dc=example,dc=com
    password: admin-password
    base: dc=example,dc=com
```

### 2. Configuration de Spring Security

Une classe de configuration de sécurité est nécessaire pour indiquer à Spring Security comment authentifier les utilisateurs et rechercher leurs rôles dans l'annuaire LDAP.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.PersonContextMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .formLogin(); // Utilise un formulaire de connexion par défaut
        return http.build();
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider(LdapAuthenticator authenticator) {
        return new LdapAuthenticationProvider(authenticator);
    }

    @Bean
    public BindAuthenticator authenticator(LdapContextSource contextSource) {
        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        // Spécifie où chercher les utilisateurs
        authenticator.setUserDnPatterns(new String[]{"uid={0},ou=people"});
        return authenticator;
    }
}
```

## Utilisation

Une fois la configuration en place, tous les endpoints de votre application seront sécurisés. Spring Security interceptera les requêtes, présentera un formulaire de connexion, et validera les identifiants fournis auprès du serveur LDAP configuré.
