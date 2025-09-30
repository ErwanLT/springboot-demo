# Tutoriel sur l'Internationalisation (i18n)

Ce tutoriel montre comment implémenter l'internationalisation (i18n) dans une application Spring Boot pour supporter plusieurs langues.

## Description

L'internationalisation est le processus de conception d'une application pour qu'elle puisse être adaptée à différentes langues et régions sans changements d'ingénierie. Ce projet démontre comment servir des messages traduits en fonction de la locale du client.

## Implémentation

L'implémentation repose sur les mécanismes i18n intégrés de Spring Boot :

1.  **Fichiers de Messages (`ResourceBundle`)**: Des fichiers de propriétés sont utilisés pour stocker les traductions.
    -   `messages.properties` (langue par défaut)
    -   `messages_en.properties` (Anglais)
    -   `messages_fr.properties` (Français)
    -   `messages_es.properties` (Espagnol)

2.  **`ResourceBundleMessageSource`**: Un bean est configuré pour indiquer à Spring où trouver les fichiers de messages (avec le nom de base `messages`).

3.  **`LocaleResolver`**: Le `AcceptHeaderLocaleResolver` est utilisé. Il détermine la langue à utiliser en se basant sur l'en-tête `Accept-Language` envoyé par le client dans la requête HTTP.

4.  **`MessageSource`**: Le service utilise l'injection de `MessageSource` pour récupérer le message approprié en fonction de la `Locale` courante.

## Comment Tester

1.  Lancez l'application Spring Boot.
2.  Utilisez un client REST comme `curl` ou Postman pour interroger l'API en spécifiant différentes langues via l'en-tête `Accept-Language`.

**Exemples avec `curl`**:

```bash
# Demander la ressource en Français
curl -H "Accept-Language: fr" http://localhost:8080/api/users/1

# Demander la ressource en Anglais
curl -H "Accept-Language: en" http://localhost:8080/api/users/1

# Demander la ressource en Espagnol
curl -H "Accept-Language: es" http://localhost:8080/api/users/1
```

Dans chaque cas, le message dans la réponse sera traduit dans la langue demandée, démontrant que le mécanisme d'internationalisation fonctionne correctement.