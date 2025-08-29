# Tutoriel RBAC avec JWT et Spring Boot

Ce tutoriel montre comment implémenter le contrôle d'accès basé sur les rôles (RBAC) avec JWT dans une application Spring Boot.

## À propos du RBAC

Le contrôle d'accès basé sur les rôles (RBAC) est un modèle de sécurité qui définit les permissions des utilisateurs en fonction de leurs rôles. Les avantages incluent :
- Gestion fine des permissions
- Séparation claire des responsabilités
- Maintenance simplifiée des droits d'accès
- Amélioration de la sécurité

## Fonctionnalités implémentées

- Implémentation RBAC avec Spring Security
- Intégration JWT
- Gestion des rôles et permissions
- Sécurisation des endpoints par rôle
- Gestion des utilisateurs et rôles

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── configuration/  # Configuration de sécurité
│   │       ├── controller/     # Contrôleurs REST
│   │       ├── dao/           # Entités et repositories
│   │       ├── model/         # DTOs et réponses
│   │       ├── service/       # Services métier
│   │       └── JwtRbacTutorialApplication.java
│   └── resources/
│       └── application.yml    # Configuration
```

## Configuration de sécurité

```java
@Configuration
@EnableMethodSecurity
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

## Modèle de données

### Entité Role
```java
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
```

### Entité User
```java
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    // Implémentation des méthodes UserDetails
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

## Installation

1. Clonez ce dépôt
2. Naviguez dans le répertoire `jwt-rbac-tutorial`
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Test de l'API

### Inscription d'un utilisateur
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

### Connexion
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Accès à un endpoint protégé
```bash
# Endpoint accessible aux utilisateurs
curl http://localhost:8080/api/user \
  -H "Authorization: Bearer <votre_token_jwt>"

# Endpoint accessible aux administrateurs
curl http://localhost:8080/api/admin \
  -H "Authorization: Bearer <votre_token_jwt>"
```

## Bonnes pratiques

1. **Sécurité** :
   - Définir des rôles avec des permissions minimales
   - Valider les tokens JWT avec les rôles
   - Implémenter la révocation des tokens
   - Logger les tentatives d'accès

2. **Architecture** :
   - Séparer les responsabilités (SRP)
   - Utiliser des DTOs pour les transferts
   - Implémenter des interfaces claires
   - Documenter les endpoints

3. **Maintenance** :
   - Gérer les migrations de base de données
   - Maintenir les logs d'audit
   - Mettre à jour les dépendances
   - Tester les scénarios de sécurité

## Ressources supplémentaires

- [Spring Security RBAC](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)
- [JWT avec RBAC](https://auth0.com/blog/role-based-access-control-rbac-and-react-apps/)
- [OWASP RBAC](https://cheatsheetseries.owasp.org/cheatsheets/Authorization_Cheat_Sheet.html)