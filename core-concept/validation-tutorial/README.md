# Validation Tutorial

Ce tutoriel démontre comment utiliser la validation des données dans une application Spring Boot en utilisant les annotations de `jakarta.validation.constraints` et `org.hibernate.validator.constraints`.

## Fonctionnalités

-   **DTO avec contraintes multiples** : La classe `UserDto` utilise un large éventail d'annotations pour définir des règles de validation complexes :
    -   `@NotBlank`, `@Size` pour le nom d'utilisateur.
    -   `@Password` (une annotation personnalisée) pour le mot de passe.
    -   `@Email` pour les adresses e-mail.
    -   `@Min`, `@Max` pour les valeurs numériques (âge).
    -   `@Pattern` pour les numéros de téléphone.
    -   `@URL` pour les adresses web.
    -   `@PastOrPresent` pour les dates.
    -   `@CreditCardNumber` pour les numéros de carte de crédit.
-   **Annotation de validation personnalisée** :
    -   Création de l'annotation `@Password` pour encapsuler la logique de validation complexe d'un mot de passe.
    -   Implémentation du `PasswordValidator` qui contient la logique de validation réutilisable.
-   **Endpoint REST** : Le `UserController` expose un endpoint POST `/api/users` qui valide l'objet `UserDto` en entrée grâce à l'annotation `@Valid`.
-   **Tests unitaires complets** : La classe `UserControllerTest` utilise `MockMvc` pour tester le endpoint et vérifier que chaque règle de validation est bien appliquée, retournant un statut `200 OK` pour des données valides et `400 Bad Request` pour des données invalides.

## Comment l'exécuter

Vous pouvez lancer l'application en exécutant la méthode `main` de la classe `ValidationApplication`.

Pour tester la validation, vous pouvez envoyer des requêtes POST à `http://localhost:8080/api/users` avec un corps JSON.

**Exemple de requête valide :**
```json
{
  "username": "testuser",
  "password": "Password@123",
  "email": "test@example.com",
  "age": 20,
  "phoneNumber": "1234567890",
  "website": "https://example.com",
  "registrationDate": "2023-10-26",
  "creditCardNumber": "4111111111111111",
  "hobbies": ["reading", "coding"],
  "preferences": ["music", "movies"]
}
```

**Exemple de requête invalide :**
```json
{
  "username": "a",
  "password": "weak",
  "email": "invalid-email",
  "age": 17,
  "phoneNumber": "123",
  "website": "not-a-url",
  "registrationDate": "2099-01-01",
  "creditCardNumber": "1234",
  "hobbies": [],
  "preferences": null
}
```