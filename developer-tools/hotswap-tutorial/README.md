# Tutoriel sur le Hotswap avec Spring Boot DevTools

Ce tutoriel démontre comment utiliser la fonctionnalité de "hotswap" (rechargement à chaud) avec **Spring Boot DevTools**.

## Description

Le hotswap est une fonctionnalité qui permet de recharger automatiquement les modifications de code dans une application en cours d'exécution sans avoir à la redémarrer complètement. Cela accélère considérablement le cycle de développement.

Spring Boot DevTools est un module qui fournit un ensemble d'outils pour les développeurs, dont le rechargement automatique de l'application.

## Concepts Clés

-   **`spring-boot-devtools`**: La dépendance clé qui active le rechargement automatique. Elle est marquée comme `optional` et `runtime` pour ne pas être incluse dans le build de production.
-   **Déclenchement du redémarrage**: DevTools surveille les changements dans le classpath. Lorsque des fichiers `.class` sont modifiés, il déclenche un redémarrage rapide de l'application.
-   **IDE Configuration**: Pour que cela fonctionne, votre IDE doit être configuré pour compiler automatiquement les fichiers lors de leur sauvegarde.
    -   **IntelliJ IDEA**: Activez "Build project automatically" dans `Settings -> Build, Execution, Deployment -> Compiler` et activez `spring.devtools.restart.enabled` dans le `Registry`.
    -   **Eclipse**: L'option "Build automatically" est généralement activée par défaut.

## Comment l'utiliser

1.  **Lancez l'application** à la racine de ce module (`hotswap-tutorial`) :
    ```bash
    mvn spring-boot:run
    ```

2.  **Accédez à l'endpoint**: Ouvrez votre navigateur ou utilisez `curl` pour appeler l'endpoint `http://localhost:8080/hello`. Vous devriez voir "Hello, World!".

3.  **Modifiez le code**: Ouvrez le fichier `src/main/java/fr/eletutour/hotswap/controller/HelloController.java` et changez le message retourné par la méthode `getHello()`. Par exemple :
    ```java
    @GetMapping
    public String getHello() {
        return "Hello, Hotswap!";
    }
    ```

4.  **Sauvegardez le fichier**. Si votre IDE est configuré pour la compilation automatique, vous verrez dans la console que l'application redémarre.

5.  **Rafraîchissez la page du navigateur** ou rappelez l'endpoint. Vous devriez maintenant voir le nouveau message "Hello, Hotswap!" sans avoir eu à relancer manuellement la commande `mvn spring-boot:run`.
