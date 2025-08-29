# Tutoriel RuleBook

Ce tutoriel présente l'utilisation de RuleBook, un framework moderne de règles métier, dans une application Spring Boot.

## À propos de RuleBook

RuleBook est un framework de règles métier qui combine la puissance des règles métier avec la simplicité d'utilisation. Il offre :
- Un DSL (Domain Specific Language) intuitif pour définir les règles
- Une intégration native avec Spring Boot
- Un support pour les règles complexes et les chaînes de règles
- Une API fluide et moderne

## Fonctionnalités principales

- Définition de règles en utilisant un DSL Java
- Support des règles conditionnelles et des actions
- Gestion des chaînes de règles
- Intégration avec les annotations Spring
- Support des règles asynchrones

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── config/         # Configuration Spring et RuleBook
│   │       ├── model/          # Modèles de données
│   │       ├── rules/          # Définition des règles
│   │       └── service/        # Services métier
│   └── resources/
│       └── rules/             # Fichiers de règles (si utilisés)
```

## Exemple d'utilisation

```java
@Rule
public class ValidationRule extends RuleBook<Fact> {
    @Override
    public void define() {
        // Définition de la règle
        when(fact -> fact.getData() != null)
            .then(fact -> fact.setValid(true))
            .stop();
    }
}
```

## Installation

1. Ajoutez la dépendance RuleBook dans votre `pom.xml` :
```xml
<dependency>
    <groupId>com.deliveredtechnologies</groupId>
    <artifactId>rulebook-core</artifactId>
    <version>0.19</version>
</dependency>
```

2. Configurez RuleBook dans votre application Spring Boot :
```java
@Configuration
public class RuleBookConfig {
    @Bean
    public RuleBookRunner ruleBookRunner() {
        return new RuleBookRunner();
    }
}
```

## Bonnes pratiques

1. **Organisation des règles** :
   - Utilisez des packages pour organiser les règles par domaine
   - Nommez les règles de manière descriptive
   - Documentez les règles avec des commentaires JavaDoc

2. **Performance** :
   - Utilisez le cache pour les règles fréquemment utilisées
   - Optimisez les conditions des règles
   - Évitez les règles trop complexes

3. **Tests** :
   - Écrivez des tests unitaires pour chaque règle
   - Testez les chaînes de règles
   - Utilisez des données de test réalistes

## Exemples de règles

### Règle simple
```java
@Rule
public class SimpleRule extends RuleBook<Fact> {
    @Override
    public void define() {
        when(fact -> fact.getAmount() > 1000)
            .then(fact -> fact.setRequiresApproval(true));
    }
}
```

### Chaîne de règles
```java
@Rule
public class ChainRule extends RuleBook<Fact> {
    @Override
    public void define() {
        when(fact -> fact.getAmount() > 0)
            .then(fact -> fact.setValid(true))
            .and()
            .when(fact -> fact.getAmount() > 1000)
            .then(fact -> fact.setRequiresApproval(true));
    }
}
```

## Ressources supplémentaires

- [Documentation officielle RuleBook](https://github.com/deliveredtechnologies/rulebook)
- [Guide d'utilisation avec Spring Boot](https://github.com/deliveredtechnologies/rulebook/wiki/Spring-Boot-Integration)
- [Exemples de règles](https://github.com/deliveredtechnologies/rulebook/tree/master/rulebook-examples)