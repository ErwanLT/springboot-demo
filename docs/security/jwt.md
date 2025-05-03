---
layout: default
title: JWT
permalink: security/jwt
---

# Sécurisez vos API avec Spring Security : JWT
Précédemment, vous avez pu suivre le tutoriel suivant qui explique comment créer et exposer une [API REST](https://www.sfeir.dev/rest-definition/) rapidement grâce à [Spring Boot](https://www.sfeir.dev/back/back-spring-boot/):

[Comment créer son projet Spring Boot de zéro !](https://www.sfeir.dev/back/creer-son-projet-spring-boot-de-zero/)

Et avez ainsi créé une première API.

Cette fois, nous allons aborder un aspect essentiel de toute API : **sa sécurisation**. En effet, sans mesures de sécurité adéquates, vos données peuvent être exposées à des accès non autorisés ou des attaques malveillantes.

Dans cette série d'articles, nous verrons comment sécuriser une API avec **Spring Security** et différentes méthodes d’authentification.  
Ici, il s'agira de **JWT (JSON Web Token)**.

## Pourquoi sécuriser une API ?

Une API REST est souvent le point d’entrée des données sensibles de votre application. Si elle n’est pas protégée, elle devient vulnérable à :

- L’accès non autorisé.
- Les attaques comme le **man-in-the-middle**, **brute force** ou **injection**.
- La divulgation de données sensibles.

## Définition de JWT

Un JSON Web Token (**JWT**) est un standard ouvert ([RFC 7519](https://datatracker.ietf.org/doc/html/rfc7519?ref=sfeir.dev)) qui permet de transmettre des informations de manière sécurisée et compacte entre deux parties sous forme d'objet JSON

### Caractéristiques principales

**Structure** : Un JWT est composé de trois parties séparées par un point (.):

`eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzMzMTk5ODcyLCJleHAiOjE3MzMyMDM0NzJ9.VYCbnUPVvLGpsZr6WvE3bJ8ApvwYwYP1lP60ipYFTpA`

- **En-tête** : Contient le type de token et l'algorithme de signature
- **Payload** : Transporte les informations (claims) comme l'identité utilisateur
- **Signature** : Garantit l'authenticité et l'intégrité du token

**Avantages clés** :

- **Stateless** : Le serveur n'a pas besoin de stocker les informations de session
- **Autonome** : Peut être vérifié sans faire appel à une autorité externe
- **Sécurisé** : Signé numériquement pour garantir son authenticité

### Utilisation principale

Les JWT sont principalement utilisés pour **l'authentification** et **l'échange sécurisé d'informations** dans les applications web et mobiles, notamment dans les architectures de microservices et les plateformes cloud.

## Ajouter les dépendances

### Spring Security

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

Dépendances pour la sécurité

### jjwt

Nous avons également besoin d’une bibliothèque pour coder, décoder et valider le jeton JWT dans l’application.

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.12.6</version>
</dependency>
```

Dépendance pour la gestion du token

### Base de données

Contrairement à ce que nous avons pu faire [précédemment lors de la mise en place de l'authentification par Basic Auth](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-basic-auth/), ici nous stockerons nos utilisateurs en base de données.

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Dépendance pour la base de données

## Nos API

Nous allons ici mettre en place 3 API :

|Méthode|Route|Sécurisé|Description|
|---|---|---|---|
|POST|/auth/signup|❌|Création d'un nouvel utilisateur|
|POST|/auth/login|❌|Authentification d'un utilisateur|
|GET|/hello|✅|Bonjour utilisateur|

## La classe utilisateur

Nous utilisons l'authentification pour nous assurer que les utilisateurs qui veulent accéder à nos API soient connus de notre système. Ce qui implique qu'ils soient stockés en base de données.

Nous allons donc commencer par créer notre entité _User_ qui sera la représentation de la table _users_ en base de données.

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

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
```

Classe User

Vous noterez ici que nous implémentons `UserDetails`, cette classe nous permet de gérer les détails liés à l'authentification.

La méthode `getAuthorities()` retournera une liste vide, nous aborderons la gestion des roles lors d'un prochain article.  
La méthode `getUsername()` retournera l'email de l'utilisateur, ce dernier étant un champ unique en base.

💡 Assurez-vous que les méthodes `isAccountNonExpired()`, `isAccountNonLocked()`, `isCredentialsNonExpired()` et `isEnabled()` renvoient **true**; sinon, l’authentification échouera.

Il nous faudra également le moyen de retrouver nos utilisateurs, pour ce faire nous aurons besoin d'un repository.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
```

Repository user

## Service de gestion du token

Qui dit JSON Web Token, dit bien évidement un service pour générer, décoder et finalement valider ce dernier. Pour ce faire nous allons utiliser les méthodes présentes dans jjwt que nous avons importé précédemment à travers un service que nous nommerons simplement `JwtService`.

```java
@Service
public class JwtService {

    private final String secretKey;

    private final long jwtExpiration;

    public JwtService(@Value("${security.jwt.secret-key}") String secretKey,
                      @Value("${security.jwt.expiration-time}") long jwtExpiration) {
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

Le service JwtService

Pas de panique, nous allons décortiquer ensemble ce que fait ce service.

Mais tout d'abord, pour générer un token nous allons avoir besoin d'une clé secrète et de fixer la période d'expiration du token, pour ce faire, nous allons modifier notre fichier `application.properties`.

```properties
security.jwt.secret-key=edc0c2ea99387d88f8bea095099ba9a90ac194796414375885c77d59ca850c68
security.jwt.expiration-time=3600000
```

La clé secrète est essentielle pour l'algorithme **HMAC** (Hash-Based Message Authentication Code) car il s'agit d'une méthode de signature symétrique : la même clé est utilisée pour signer et vérifier le JWT.  
Cela garantit l'intégrité et l'authenticité du token, empêchant quiconque ne possédant pas la clé de falsifier ou de modifier son contenu.

### Génération du token

```java
private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
    return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}

private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
}
```

Méthode de génération de token

1. **Méthode `buildToken`** : Cette méthode crée un JWT (en [utilisant le design pattern builder](https://www.sfeir.dev/back/les-designs-patterns-de-creation-builder/)) en ajoutant des informations sur l'utilisateur et des revendications supplémentaires (roles / identifiant / organisation ...).
    1. **`extraClaims`** : Ajoute des données supplémentaires.
    2. **`subject`** : Définit le nom d'utilisateur comme sujet du token.
    3. **`issuedAt` et `expiration`** : Définit la date d'émission et la durée de validité du token.
    4. `**signWith**` : Signe le token avec HMAC-SHA256 en utilisant une clé secrète obtenue via `getSignInKey`.
    5. **`compact`** : Compacte en une chaîne finale composée de l'en-tête, du payload et de la signature.
2. **Méthode `getSignInKey`** : Cette méthode prépare la clé secrète pour la signature HMAC.
    1. **Décodage Base64** : La clé secrète est décodée depuis sa représentation Base64 en tableau d’octets.
    2. **Conversion en clé HMAC-SHA256** : Les octets sont transformés en une clé compatible avec l'algorithme HMAC-SHA256.

### Validation du token

```java
public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
}

public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
}  

private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}

private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
}

public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
}

private Claims extractAllClaims(String token) {
    return Jwts
            .parser()
            .setSigningKey(getSignInKey())
            .build().parseSignedClaims(token)
            .getPayload();
}
```

**Méthode `extractAllClaims`**

Toutes les méthodes pour valider le token sont liés à `extractAllClaims`, cette méthode extrait toutes les revendications contenues dans un token.

- Utilise le parser JWT de `JJWT` :
    1. Configure la clé de signature avec `getSignInKey` pour s'assurer que le token est signé correctement.
    2. Parse le token pour obtenir ses revendications.
- Renvoie un objet `Claims`, qui contient toutes les informations du payload du token.

Maintenant que nous avons nos méthode pour générer et valider nos tokens, il nous faut maintenant les mettre à profit, car pour l'instant la méthode d'authentification qui est en place, est celle par défaut de Spring security : [**Basic Authentification**](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-basic-auth/).  
Nous allons donc maintenant modifier notre configuration relative à la sécurité afin d'utiliser nos token à la place.

## Configurer la sécurité

Pour modifier la configuration par défaut de Spring Security, nous allons procéder en 3 étapes :

- Une couche de configuration
- Une couche de récupération / validation du token
- Une couche de sécurité

### Un peu de configuration globale

```java
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
```

Voyons un peu plus en détail ce que font les Bean définis dans cette classe :

- **`UserDetailService`** : il s'agit d'une interface de **Spring Security** utilisée pour récupérer les détails des utilisateurs nécessaires à l'authentification et à l'autorisation. Les informations retournées sont encapsulées dans un objet `UserDetails`, qui contient des détails sur l'utilisateur, comme :
    - Nom d'utilisateur
    - Mot de passe
    - Rôles/autorités
    - État actif ou inactif de l'utilisateur
- **`BCryptPasswordEncoder`** : Le **BCryptPasswordEncoder** applique l'algorithme de hachage BCrypt pour sécuriser les mots de passe, ce qui rend le mot de passe difficile à retrouver même en cas de compromission de la base de données.
- **`AuthenticationProvider`** : définit un **AuthenticationProvider** personnalisé pour Spring Security, spécifiquement un **DaoAuthenticationProvider**. Ce fournisseur est responsable de l'authentification des utilisateurs via un **UserDetailsService** (récupérant les informations de l'utilisateur à partir de la base de données ou d'une autre source) et un **PasswordEncoder** (pour vérifier les mots de passe de manière sécurisée).
    - **`authProvider.setUserDetailsService(userDetailsService())`** : Cette ligne associe le **UserDetailsService** à l'authentification, permettant au fournisseur de récupérer les informations de l'utilisateur (nom d'utilisateur, mot de passe, rôles, etc.).
    - **`authProvider.setPasswordEncoder(passwordEncoder())`** : Cela spécifie l'**encoder de mot de passe** (ici, `BCryptPasswordEncoder`), qui permettra de vérifier les mots de passe hachés en comparant le mot de passe saisi avec celui stocké.

A cette étape, le mot de passe qui apparaissait en clair dans les logs au démarrage de l'application n'est plus visible.

### Récupération et validation du token

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
```

C'est dans cette classe que nous allons **récupérer et valider** notre token, cette dernière étend `OncePerRequestFilter`, ce qui signifie qu'elle est exécutée une seule fois par requête HTTP.

- **Méthode `doFilterInternal`** : Cette méthode est exécutée pour chaque requête HTTP.
    - Le filtre commence par récupérer l'en-tête `Authorization` de la requête. Si cet en-tête est absent ou ne commence pas par "Bearer ", il passe simplement à la requête suivante.
    - **Extraction de l'email et vérification de l'authentification** :
        - Si un JWT est présent, le code extrait l'email de l'utilisateur via `jwtService.extractUsername(jwt)`.
        - Si l'email est trouvé et qu'aucune authentification n'est déjà présente (c'est-à-dire que l'utilisateur n'est pas encore authentifié), il charge les détails de l'utilisateur à partir de `userDetailsService.loadUserByUsername(userEmail)`.
    - **Validation du token** : Le token est validé avec `jwtService.isTokenValid(jwt, userDetails)`. Si le token est valide, un `**UsernamePasswordAuthenticationToken**` est créé pour authentifier l'utilisateur, et ce token d'authentification est placé dans le `SecurityContextHolder`, ce qui permet à Spring Security de gérer l'authentification de l'utilisateur.

## La sécurité

Notre méthode d'authentification est maintenant prête à être utilisée, il ne nous reste plus qu'à configurer dans quel cas nous voulons l'utiliser.

```java
@Configuration
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

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

Nous n'avons ici qu'une méthode securityFilterChain qui via un [builder](https://www.sfeir.dev/back/les-designs-patterns-de-creation-builder/) va nous permettre de définir les règles de sécurité pour gérer les requêtes HTTP.  
Voyons comment elle fonctionne :

- **Désactivation de CSRF :**

```java
.csrf(AbstractHttpConfigurer::disable)
```

Le mécanisme **CSRF (Cross-Site Request Forgery)** est désactivé ici. Ce mécanisme est utile pour les applications web avec sessions, mais inutile ici.

- **Règles d'autorisation des requêtes :**

```java
.authorizeHttpRequests(auth ->
      auth.requestMatchers("/auth/**").permitAll()
              .anyRequest().authenticated())
```

- Toutes les requêtes commençant par /auth/ ne nécessitent aucune authentification
- Toutes les autres requêtes nécessitent d'être authentifiées

- **Méthode d'authentification :**

```java
.authenticationProvider(authenticationProvider)
```

Ici l'on se sert du bean **`AuthenticationProvider`** défini dans notre classe de configuration globale.

- **Ajout d'un filtre pour le traitement des JWT :**

```java
.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
```

- Ajoute un filtre personnalisé `jwtAuthenticationFilter` avant le filtre standard `UsernamePasswordAuthenticationFilter`.
- Le `jwtAuthenticationFilter` :
    - Vérifie la présence et la validité d'un JWT dans chaque requête.
    - Si le JWT est valide, il configure les détails de l'utilisateur authentifié dans le contexte de sécurité.

## Gestion des erreurs

Je vous passe le laïus sur le fait qu'il est important de bien gérer ses erreurs dans spring boot, vous trouverez un article sur le sujet juste ici

[![](https://www.sfeir.dev/content/images/thumbnail/photo-1579373903781-fd5c0c30c4cd.jpeg)](https://www.sfeir.dev/back/comment-bien-gerer-ses-erreur-dans-springboot/)

Sachez cependant qu'en matière de sécurité, il ne faut pas non plus donner trop de détails.

Par exemple, l'erreur vient du mot de passe, vous ne devriez pas indiquer spécifiquement que c'est à cause de ce dernier, car cela peut être une faille de sécurité.  
Voici notre classe de gestion d'erreur :

```java
@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {

        return switch (exception) {
            case BadCredentialsException e -> createProblemDetail(401, e.getMessage(), "The username or password is incorrect");
            case AccountStatusException e -> createProblemDetail(403, e.getMessage(), "The account is locked");
            case AccessDeniedException e -> createProblemDetail(403, e.getMessage(), "You are not authorized to access this resource");
            case SignatureException e -> createProblemDetail(403, e.getMessage(), "The JWT signature is invalid");
            case ExpiredJwtException e -> createProblemDetail(403, e.getMessage(), "The JWT token has expired");
            default -> createProblemDetail(500, exception.getMessage(), "Unknown internal server error.");
        };
    }

    private ProblemDetail createProblemDetail(int status, String message, String description) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), message);
        detail.setProperty("description", description);
        return detail;
    }
}
```

J'ai utilisé ici [l'une des feature de java 21](https://www.sfeir.dev/back/quoi-de-neuf-dans-lapi-java-21/), le pattern matching pour gérer les différentes exceptions.

Il ne nous reste plus qu'à tester notre configuration via Postman ou n'importe quel autre outils de requête.

## Testing

J'ai préalablement créé un user via notre API `/signup`, puis l'ai fait se logger via `/login` afin de récupérer son token

[![](https://www.sfeir.dev/content/images/2024/12/image-13.png)](https://www.sfeir.dev/content/images/2024/12/image-13.png)

Génération du token

Voyons maintenant ce qu'il se passe quand je tente d'appeler `/hello` dans différente situation.

### Sans être authentifié

[![](https://www.sfeir.dev/content/images/2024/12/image-14.png)](https://www.sfeir.dev/content/images/2024/12/image-14.png)

### Avec un Token incomplet

[![](https://www.sfeir.dev/content/images/2024/12/image-15.png)](https://www.sfeir.dev/content/images/2024/12/image-15.png)

### Avec un token expiré

[![](https://www.sfeir.dev/content/images/2024/12/image-16.png)](https://www.sfeir.dev/content/images/2024/12/image-16.png)

### Avec un token valide

[![](https://www.sfeir.dev/content/images/2024/12/image-17.png)](https://www.sfeir.dev/content/images/2024/12/image-17.png)