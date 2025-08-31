# Tutoriel Easy Rules

Ce tutoriel présente l'utilisation d'Easy Rules, une bibliothèque Java simple et légère pour la gestion de règles métier, dans une application Spring Boot.

## À propos d'Easy Rules

Easy Rules est une bibliothèque qui permet de définir et d'exécuter des règles métier de manière déclarative. Elle est particulièrement adaptée pour :
- Les applications nécessitant une logique métier simple
- Les cas d'usage où la complexité de Drools n'est pas nécessaire
- Les projets où la simplicité et la maintenabilité sont prioritaires

## Fonctionnalités principales

- Définition de règles simples avec des conditions et des actions
- Support des règles composées
- Gestion des priorités des règles
- Intégration facile avec Spring Boot
- API fluide et intuitive

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── config/         # Configuration Spring et Easy Rules
│   │       ├── model/          # Modèles de données
│   │       ├── rules/          # Définition des règles
│   │       └── service/        # Services métier
│   └── resources/
│       └── rules/             # Fichiers de règles (si utilisés)
```

## Exemple d'utilisation

```java
@Rule(name = "Règle de validation", description = "Valide les données d'entrée")
public class ValidationRule implements Rule<Fact> {
    @Override
    public boolean evaluate(Fact fact) {
        return fact.getData() != null;
    }

    @Override
    public void execute(Fact fact) {
        fact.setValid(true);
    }
}
```

## Installation

1. Ajoutez la dépendance Easy Rules dans votre `pom.xml` :
```xml
<dependency>
    <groupId>org.jeasy</groupId>
    <artifactId>easy-rules-core</artifactId>
    <version>4.1.0</version>
</dependency>
```

2. Configurez le moteur de règles dans votre application Spring Boot :
```java
@Configuration
public class EasyRulesConfig {
    @Bean
    public RulesEngine rulesEngine() {
        return new DefaultRulesEngine();
    }
}
```

## Bonnes pratiques

1. **Organisation des règles** :
   - Regroupez les règles par domaine métier
   - Utilisez des noms explicites pour les règles
   - Documentez les règles avec des descriptions claires

2. **Performance** :
   - Évitez les règles trop complexes
   - Utilisez des priorités pour optimiser l'ordre d'exécution
   - Mettez en cache les règles fréquemment utilisées

3. **Tests** :
   - Testez chaque règle individuellement
   - Créez des tests d'intégration pour les scénarios complexes
   - Utilisez des données de test représentatives

## Ressources supplémentaires

- [Documentation officielle Easy Rules](https://github.com/j-easy/easy-rules)
- [Guide d'utilisation avec Spring Boot](https://github.com/j-easy/easy-rules/wiki/spring-boot-integration)
- [Exemples de règles](https://github.com/j-easy/easy-rules/tree/master/easy-rules-tutorials)