# Tutoriel Basic Auth avec Spring Boot

Ce tutoriel montre comment implémenter l'authentification basique dans une application Spring Boot.

## À propos de l'authentification basique

L'authentification basique est une méthode simple de sécurisation des API REST. Elle utilise un en-tête HTTP standard pour transmettre les identifiants de l'utilisateur.

## Fonctionnalités implémentées

- Configuration de Spring Security
- Authentification basique avec utilisateurs en mémoire
- Sécurisation des endpoints REST
- Gestion des rôles utilisateur (USER et ADMIN)
- Interface de test avec React

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── configuration/  # Configuration de sécurité
│   │       ├── controller/     # Contrôleurs REST
│   │       └── BasicAuthTutorialApplication.java
│   └── resources/
│       └── application.yml    # Configuration
```

## Configuration de sécurité

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

## Contrôleur REST

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

## Installation

1. Clonez ce dépôt
2. Naviguez dans le répertoire `basic-auth-tutorial`
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`
5. Pour l'interface de test, exécutez :
   ```bash
   npm install
   npm start
   ```

## Test de l'API

### Avec cURL
```bash
# Accès public
curl http://localhost:8080/goodbye

# Accès authentifié (utilisateur)
curl -u user:password http://localhost:8080/hello

# Accès authentifié (admin)
curl -u admin:admin123 http://localhost:8080/hello
```

### Avec l'interface React
1. Ouvrez http://localhost:3000
2. Utilisez les identifiants :
   - Utilisateur normal :
     - Username: user
     - Password: password
   - Administrateur :
     - Username: admin
     - Password: admin123

## Bonnes pratiques

1. **Sécurité** :
   - Utiliser HTTPS en production
   - Changer les mots de passe par défaut
   - Implémenter la limitation des tentatives
   - Configurer les en-têtes de sécurité

2. **Configuration** :
   - Externaliser les identifiants
   - Utiliser des mots de passe forts
   - Gérer les rôles de manière flexible
   - Documenter les endpoints

3. **Tests** :
   - Tester les endpoints publics et privés
   - Vérifier la gestion des erreurs
   - Tester les différents rôles
   - Valider les réponses HTTP

## Ressources supplémentaires

- [Documentation Spring Security](https://docs.spring.io/spring-security/reference/)
- [Guide Basic Auth](https://developer.mozilla.org/fr/docs/Web/HTTP/Authentication)
- [OWASP Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/HTTP_Basic_Authentication_Cheat_Sheet.html)