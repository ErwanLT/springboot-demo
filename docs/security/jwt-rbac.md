---
layout: default
title: JWT RBAC
---

# Sécurisez vos API avec Spring Security : accès par rôle
Précédemment, vous avez pu suivre le tutoriel suivant qui explique comment créer et exposer une [API REST](https://www.sfeir.dev/rest-definition/) rapidement grâce à [Spring Boot](https://www.sfeir.dev/back/back-spring-boot/):

[Comment créer son projet Spring Boot de zéro !](https://www.sfeir.dev/back/creer-son-projet-spring-boot-de-zero/)

Et avez ainsi créé une première API.

Cette fois, nous allons aborder un aspect essentiel de toute API : **sa sécurisation**. En effet, sans mesures de sécurité adéquates, vos données peuvent être exposées à des accès non autorisés ou des attaques malveillantes.

Dans cette série d'articles, nous verrons comment sécuriser une API avec **Spring Security** et différentes méthodes d’authentification, ici il s'agira de **JWT (JSON Web Token) + RBAC (Role-Based Access Control)**.

## Prérequis

Cet article est une suite directe de l'article sur l'implémentation de la sécurité avec JWT dans Spring Boot :

[Sécurisez vos API avec Spring Security : JWT]({{ '/security/jwt/' | relative_url }})

Il est donc primordial d'avoir suivi ce premier tutoriel pour suivre celui-ci, car nous nous appuierons sur ce qui a déjà été mis en place.

## Pourquoi sécuriser une API ?

Une API REST est souvent le point d’entrée des données sensibles de votre application. Si elle n’est pas protégée, elle devient vulnérable à :

- L’accès non autorisé.
- Les attaques, comme le **man-in-the-middle**, **brute force** ou **injection**.
- La divulgation de données sensibles.

## RBAC définition

Le contrôle d'accès basé sur les rôles (RBAC, Role-Based Access Control) est un modèle de gestion des permissions qui permet de restreindre l'accès aux ressources d'un système en fonction des rôles des utilisateurs.

### Caractéristiques principales

**Structure**  
Un système RBAC repose sur trois concepts clés :

1. **Rôles** : Définitions abstraites qui regroupent un ensemble de permissions spécifiques. Par exemple : _Admin_, _Manager_, _Utilisateur_.
2. **Permissions** : Actions autorisées, telles que _Lire un fichier_, _Modifier un profil_, ou _Supprimer un enregistrement_.
3. **Attribution** : Les utilisateurs se voient attribuer des rôles qui leur confèrent des permissions.

**Mappage logique**

- **Utilisateur → Rôle → Permissions → Ressources**  
  Un utilisateur n'accède à une ressource que s'il a un rôle autorisé à effectuer l'action sur cette ressource.

### Avantages clés

- **Centralisé** : Gestion simplifiée des accès grâce à des rôles, au lieu de gérer les permissions directement pour chaque utilisateur.
- **Sécurisé** : Réduit le risque d'accès non autorisé en limitant les permissions aux besoins spécifiques des rôles.
- **Evolutif** : Les nouveaux utilisateurs ou permissions peuvent être intégrés rapidement en mettant à jour les rôles.
- **Transparence** : Offre une vue claire des relations entre utilisateurs, rôles et permissions.

### Utilisation principale

RBAC est principalement utilisé dans les systèmes nécessitant une gestion fine des accès, tels que :

- **Applications métiers** (ERP, CRM)
- **Systèmes de gestion de fichiers**
- **Plateformes de collaboration**
- **Environnements cloud et microservices**, où les autorisations doivent être facilement adaptables à différents groupes d'utilisateurs.

Prenons un exemple concret issu de la pop culture : **l'Ordre 66** dans Star Wars

[![](https://media.tenor.com/LBWyQg647MoAAAAC/execute-order66-order66.gif)](https://media.tenor.com/LBWyQg647MoAAAAC/execute-order66-order66.gif)

Ordre 66

> Dans le cas où des officiers Jedi agissent contre les intérêts de la République, et après avoir reçu des ordres spécifiques vérifiés comme venant directement du Commandant Suprême (Chancelier), les commandants retireront ces officiers par la force mortelle, et le commandement reviendra au Commandant suprême (Chancelier) jusqu’à ce qu’une nouvelle structure de commandement soit établie.
> - Définition de l'ordre 66

Ce qu'il faut noter ici, c'est que si Palpatine était resté le simple sénateur de Naboo, il n'aurait jamais pu faire exécuter l'ordre 66, car il n'aurait pas eu suffisamment d'autorité pour se faire.

## Nos API

Voici les API déjà existantes dans notre application

|Méthode|Route|Sécurisé|Description|
|---|---|---|---|
|POST|/auth/signup|❌|Création d'un nouvel utilisateur|
|POST|/auth/login|❌|Authentification d'un utilisateur|
|GET|/hello|✅|Bonjour utilisateur|

Et voici celle que nous allons maintenant rajouter

|Méthode|Route|Sécurisé|Role|Description|
|---|---|---|---|---|
|GET|/users/me|✅|SUPER ADMIN / ADMIN / USER|Récupération des informations de l'utilisateur connecté|
|GET|/users|✅|SUPER ADMIN / ADMIN|Récupération des informations de tous les utilisateurs|
|POST|/admins|✅|SUPER ADMIN|Création d'un user ADMIN|

Vous l'aurez compris grâce au tableau ci dessus, nous aurons maintenant 3 rôles dans notre application :

- **User** : peut accéder à ses informations
- **Admin** : peut faire tout ce que fait le user, mais peut également accéder à la liste complète des utilisateurs
- **Super Admin** : peut faire tout ce que fait l'admin, mais à également la possibilité de créer d'autre administrateur.

## Créons nos rôles

Qui dit accès par rôle, dit forcément rôles, nous allons donc enrichir le code existant pour ajouter ces derniers.

Pour ce faire nous allons avoir besoin d'une enum, et d'une classe qui sera la représentation de nos rôles en base de données.

### RoleEnum

```java
public enum RoleEnum {
    USER,
    ADMIN,
    SUPER_ADMIN
}
```

enum des rôles possible

Avec cette enum, nous nous assurons que ces valeurs soient constantes, et nous garantissons également que seules ces valeurs peuvent être utilisées pour désigner un rôle dans l'application.

### L'entité Role

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

    // Getter et Setter
}
```

Classe rôle

Elle contient un identifiant unique `id`, un champ `name` basé sur l'énumération `RoleEnum` pour assurer que seuls des rôles **valides** sont stockés, et une description textuelle du rôle.

### Le repository

```java
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
```

repository des rôle

Pour aller lire et sauvegarder les rôles en base de données, nous aurons besoin de ce repository.

### Le service de création de rôle

Pour l'instant, je ne compte pas mettre en place une API de création / modification de rôles, mais j'ai quand même besoin que les rôles que nous avons définis plus haut soient créés.  
Pour ce faire je vais créer un service qui sera dédié à la gestion des rôles et utiliser [une annotation](https://www.sfeir.dev/back/comprendre-les-annotations-dans-spring-boot/) **`@PostConstruct`** pour alimenter ma base avec nos 3 rôles.

```java
@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    void init() {
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        roleDescriptionMap.forEach((roleName, description) ->
                roleRepository.findByName(roleName).ifPresentOrElse(
                        role -> logger.info("Role already exists: {}", role),
                        () -> {
                            Role roleToCreate = new Role()
                                    .setName(roleName)
                                    .setDescription(description);
                            roleRepository.save(roleToCreate);
                            logger.info("Created new role: {}", roleToCreate);
                        }
                )
        );
    }

    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
```

Service de gestion des rôles

## Mise à jour de la classe User

Maintenant que nous avons les rôles, ils nous faut les associer à nos utilisateurs. Pour ce faire, nous devons modifier notre classe User afin de créer une relation vers la classe des rôles.

```java
@ManyToOne(cascade = CascadeType.REMOVE)
@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
private Role role;

public Role getRole() {
    return role;
}

public User setRole(Role role) {
    this.role = role;

    return this;
}
```

modification de la classe User

Les plus attentifs auront remarqué le `nullable = false`, qui rend le rôle obligatoire, il faut donc que nous modifions également notre service de création d'utilisateur.

```java
public User signup(RegisterUserDto input) {

    Optional<Role> optionalRole = roleService.findByName(RoleEnum.USER);

    if (optionalRole.isEmpty()) {
        return null;
    }

    var user = new User()
            .setFullName(input.fullName())
            .setEmail(input.email())
            .setPassword(passwordEncoder.encode(input.password()))
            .setRole(optionalRole.get());

    return userRepository.save(user);
}
```

méthode signup

Maintenant, quand nous créerons un nouvel utilisateur, ce dernier se verra attribuer le rôle USER.

## Les rôles dans le contexte d'authentification

Dans la classe entité utilisateur (`User.java`), la fonction `getAuthorities()` retourne toutes les autorisations associées à cet utilisateur. Elle était vide par défaut, mais nous devons maintenant la mettre à jour pour qu'elle produise une liste contenant le nom du rôle de l'utilisateur.

Remplacez la fonction `getAuthorities()` par le code ci-dessous :

```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
    return List.of(authority);
}
```

💡 Pour l'autorisation basée sur les rôles, Spring Security ajoute par défaut le préfixe ROLE_ à la valeur fournie. C'est pourquoi nous concaténons le nom du rôle avec "ROLE_".

## Mise à jour de la configuration de sécurité

Pour restreindre l'accès des utilisateurs en fonction de leurs rôles, nous devons activer cette fonctionnalité dans Spring Security, ce qui nous permet d'effectuer la vérification sans écrire de logique personnalisée.

Il faut ajouter l'annotation `@EnableMethodSecurity` dans notre classe de configuration de sécurité **`SecurityConfiguration.java`**.

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

}
```

## Protégeons nos API

Il ne nous reste plus maintenant qu'à sécuriser les endpoint de nos API.  
Pour ce faire, nous allons utiliser l'annotation `@PreAuthorize`,pour contrôler l'accès à une méthode en fonction d'une expression SpEL (**Spring Expression Language**).  
Elle permet de définir des règles d'autorisation avant que la méthode ne soit exécutée.

```java
@GetMapping("/me")
@PreAuthorize("isAuthenticated()")
public User authenticatedUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User) authentication.getPrincipal();
}

@GetMapping()
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public List<User> allUsers() {
    return userService.allUsers();
}

@PostMapping
@PreAuthorize("hasRole('SUPER_ADMIN')")
public User createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
    return userService.createAdministrator(registerUserDto);
}
```

Ici nous indiquons les règle de sécurité suivante :

- Informations du user connecté : tout le monde à partir du moment ou la personne à un token valide.
- Retourner la liste des utilisateurs: token valide + role ADMIN ou SUPER ADMIN
- Créer un nouvel ADMIN : token valide + role SUPER ADMIN.

Si jamais nous essayons d'appeler une de nos API avec un rôle qui n'a pas les droit, nous aurons une erreur :

[![](https://www.sfeir.dev/content/images/2024/12/image-20.png)](https://www.sfeir.dev/content/images/2024/12/image-20.png)

Et voilà, c'est comme ça que l'on empêche la purge jedi

[![](https://media.tenor.com/8_Mwd2dXry0AAAAC/mace-windu-revenge-of-the-sith.gif)](https://media.tenor.com/8_Mwd2dXry0AAAAC/mace-windu-revenge-of-the-sith.gif)