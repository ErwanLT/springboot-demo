# Tutoriel sur Spring Expression Language (SpEL)

Ce tutoriel explore les fonctionnalités du **Spring Expression Language (SpEL)**, un langage puissant pour interroger et manipuler un graphe d'objets à l'exécution.

## Concepts Clés

- **Expressions littérales**: Définition de chaînes de caractères, nombres et booléens directement dans une expression.
- **Référencement de Beans**: Accès aux propriétés et méthodes d'autres beans gérés par Spring.
- **Appel de méthodes de bean**: Invocation de méthodes sur d'autres beans Spring, y compris en passant des arguments.
- **Opérateurs**: Utilisation d'opérateurs logiques, relationnels et ternaires.
- **Accès aux collections**: Création et interrogation de listes et de maps.
- **Opérateur de type (`T()`)**: Appel de méthodes statiques et accès à des constantes de classe.
- **Navigation sécurisée (`?.`)**: Prévention des `NullPointerException` lors de l'accès à des propriétés potentiellement nulles.

## Exemples

Ce module montrera comment :
- Injecter des valeurs littérales via `@Value`.
- Lire des valeurs depuis `application.properties`.
- Référencer d'autres beans et appeler leurs méthodes.
- Appeler des méthodes sur d'autres beans en leur passant des arguments.
- Utiliser des opérateurs pour des évaluations conditionnelles.
- Manipuler des listes et des maps directement dans les expressions.
- Appeler des méthodes statiques comme `UUID.randomUUID()`.

## Évaluation d'Expressions par Programmation

En plus d'utiliser SpEL dans les annotations, il est possible d'évaluer des expressions dynamiquement dans votre code. Cela permet de traiter des expressions qui ne sont pas connues à l'avance ou qui dépendent d'entrées utilisateur.

L'API SpEL principale comprend :
- **`ExpressionParser`**: Pour parser une chaîne de caractères contenant une expression.
- **`Expression`**: L'objet représentant l'expression parsée.
- **`EvaluationContext`**: L'interface qui contient le contexte dans lequel l'expression est évaluée (variables, fonctions, etc.).

Ce module montre comment :
- Créer un `SpelExpressionParser`.
- Définir des variables (`#variableName`) et leur passer des valeurs lors de l'évaluation.
- Évaluer une expression par rapport à un objet racine (root object).