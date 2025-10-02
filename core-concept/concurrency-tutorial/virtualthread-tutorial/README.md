# Tutoriel sur les Virtual Threads (Project Loom)

Ce tutoriel démontre comment utiliser les **Virtual Threads** dans une application Spring Boot pour améliorer les performances des tâches concurrentes liées à des entrées/sorties (I/O-bound). L'exemple concret est le traitement de fichiers (téléversement, organisation) sur un serveur SFTP.

## Concepts Clés

- **Virtual Threads (Project Loom)**: Introduits dans Java 21, les *virtual threads* sont des threads légers gérés par la JVM. Ils sont conçus pour augmenter considérablement le débit des applications concurrentes qui passent beaucoup de temps à attendre (comme les appels réseau ou les opérations sur les fichiers), sans la complexité de la programmation asynchrone.

- **Activation dans Spring Boot**: Pour utiliser les virtual threads, on peut :
    1.  Activer la propriété `spring.threads.virtual.enabled=true` dans `application.properties` pour que l'ensemble de l'application (y compris le serveur web embarqué) les utilise.
    2.  Définir un bean `AsyncTaskExecutor` personnalisé, comme dans ce projet, pour dédier les virtual threads à des tâches spécifiques soumises via cet exécuteur.
        ```java
        @Bean
        public AsyncTaskExecutor applicationTaskExecutor() {
            return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
        }
        ```

- **Spring Integration SFTP**: Module de Spring Integration utilisé pour simplifier les interactions avec un serveur SFTP.

## Fonctionnalités Démontrées

- **Configuration des Virtual Threads**: Mise en place d'un exécuteur de tâches basé sur les virtual threads.
- **Traitement Parallèle**: Le téléversement de plusieurs fichiers est traité en parallèle, chaque fichier étant géré par un virtual thread distinct.
- **Comparaison de Performance**: L'application expose deux endpoints pour organiser les fichiers sur le serveur SFTP :
    - Un endpoint (`/organize`) qui effectue l'opération de manière concurrente avec des virtual threads.
    - Un endpoint (`/organize-sequentially`) qui fait la même chose de manière séquentielle.
    Cela permet de comparer les temps d'exécution dans les logs et de constater le gain de performance.

## Comment l'exécuter et le tester

### 1. Prérequis

- Java 21
- Docker et Docker Compose

### 2. Démarrer le serveur SFTP

Un serveur SFTP est fourni via Docker. Pour le lancer, exécutez la commande suivante à la racine de ce module (`virtualthread-tutorial`) :

```bash
docker-compose up -d
```
Le serveur sera disponible sur `localhost:2222` (utilisateur: `foo`, mot de passe: `pass`).

### 3. Lancer l'application

Vous pouvez démarrer l'application Spring Boot via votre IDE ou avec Maven :

```bash
mvn spring-boot:run
```

### 4. Téléverser des fichiers

Utilisez `curl` pour envoyer une requête `POST` à l'endpoint `/upload` avec un ou plusieurs fichiers. L'application les traitera en parallèle.

```bash
# Assurez-vous de créer des fichiers de test au préalable
# Exemple : touch 20240115_1030_file1.txt 20240116_1100_file2.txt

curl -X POST -F 'files=@/chemin/vers/20240115_1030_file1.txt' -F 'files=@/chemin/vers/20240116_1100_file2.txt' http://localhost:8080/upload
```
Les fichiers seront téléversés dans le dossier `/upload` du serveur SFTP.

### 5. Organiser les fichiers et comparer les performances

Les fichiers seront déplacés dans une structure de dossiers `année/mois/jour` basée sur leur nom. Observez les logs de l'application pour voir la différence de temps d'exécution.

- **Organisation concurrente (avec Virtual Threads) :**
  ```bash
  curl -X POST http://localhost:8080/organize
  ```

- **Organisation séquentielle :**
  ```bash
  curl -X POST http://localhost:8080/organize-sequentially
  ```