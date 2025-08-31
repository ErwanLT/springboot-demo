# Tutoriel JWT avec Spring Boot

Ce tutoriel montre comment implémenter l'authentification JWT dans une application Spring Boot.

## À propos de JWT

JWT (JSON Web Token) est un standard ouvert pour la transmission sécurisée d'informations entre parties sous forme d'objet JSON. Il est particulièrement adapté pour :
- L'authentification stateless
- La sécurisation des API REST
- Le partage d'informations entre services

## Fonctionnalités implémentées

- Génération et validation de JWT
- Configuration de Spring Security
- Gestion des tokens d'accès
- Endpoints d'authentification (/auth/login et /auth/signup)
- Sécurisation des endpoints REST

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── configuration/  # Configuration de sécurité
│   │       ├── controller/     # Contrôleurs REST
│   │       ├── model/         # DTOs et réponses
│   │       ├── service/       # Services métier
│   │       └── JwtTutorialApplication.java
│   └── resources/
│       └── application.yml    # Configuration
```

## Configuration de sécurité

```java
@Configuration
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
```

## Contrôleur d'authentification

```java
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        var authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse()
            .setToken(jwtToken)
            .setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
```

## Modèle de réponse

```java
public class LoginResponse {
    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
```

## Installation

1. Clonez ce dépôt
2. Naviguez dans le répertoire `jwt-tutorial`
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Test de l'API

### Inscription d'un utilisateur
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "password",
    "email": "user@example.com"
  }'
```

### Connexion
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "password"
  }'
```

### Accès à un endpoint protégé
```bash
curl http://localhost:8080/api/protected \
  -H "Authorization: Bearer <votre_token_jwt>"
```

## Structure d'un JWT

Un JWT est composé de trois parties séparées par des points :
1. **Header** : Contient le type de token et l'algorithme de signature
2. **Payload** : Contient les claims (informations)
3. **Signature** : Vérifie l'authenticité du token

Exemple :
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

## Bonnes pratiques

1. **Sécurité** :
   - Utiliser des clés secrètes fortes
   - Définir une durée de vie appropriée pour les tokens
   - Stocker les tokens de manière sécurisée
   - Implémenter la révocation des tokens

2. **Configuration** :
   - Externaliser les paramètres de configuration
   - Utiliser des algorithmes de signature sécurisés
   - Configurer correctement les CORS
   - Gérer les erreurs d'authentification

3. **Performance** :
   - Optimiser la taille des tokens
   - Mettre en cache les tokens valides
   - Implémenter le refresh token
   - Surveiller l'utilisation des tokens

## Ressources supplémentaires

- [Documentation JWT](https://jwt.io/introduction)
- [Spring Security JWT](https://docs.spring.io/spring-security/reference/servlet/authentication/jwt.html)
- [OWASP JWT Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html)
