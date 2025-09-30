# Tutoriel Prometheus et Grafana

Ce tutoriel montre comment intégrer une application Spring Boot avec Prometheus pour la collecte de métriques et Grafana pour leur visualisation.

[Article SFEIR associé](https://www.sfeir.dev/back/superviser-votre-application-spring-boot/)

## Description

L'application expose ses métriques internes (JVM, requêtes HTTP, etc.) via l'actuator de Spring Boot. Grâce à la dépendance `micrometer-registry-prometheus`, ces métriques sont disponibles dans un format que Prometheus peut "scraper" (collecter périodiquement).

Une fois collectées par Prometheus, les métriques peuvent être visualisées et analysées dans des tableaux de bord (dashboards) Grafana.

## Composants

1.  **Application Spring Boot**: L'application à monitorer. Elle expose un endpoint `/actuator/prometheus`.
2.  **Prometheus**: Un système de monitoring open-source qui collecte les métriques en interrogeant les endpoints exposés.
3.  **Grafana**: Une plateforme de visualisation qui se connecte à Prometheus pour créer des tableaux de bord interactifs.

## Configuration Clé

-   **`pom.xml`**: Contient la dépendance `micrometer-registry-prometheus` qui permet l'exposition des métriques au format Prometheus.
-   **`application.properties`**: La ligne `management.endpoints.web.exposure.include=*` est cruciale pour rendre le endpoint `/actuator/prometheus` accessible.

## Comment l'exécuter

Pour une expérience complète, vous aurez besoin de Docker pour lancer Prometheus et Grafana.

1.  **Créez un fichier `prometheus.yml`** pour configurer Prometheus afin qu'il scrape votre application :

    ```yaml
    global:
      scrape_interval: 15s

    scrape_configs:
      - job_name: 'spring-boot-app'
        metrics_path: '/actuator/prometheus'
        static_configs:
          - targets: ['host.docker.internal:8081'] # Remplacez par l'IP de votre machine si nécessaire
    ```

2.  **Créez un fichier `docker-compose.yml`** pour lancer les services :

    ```yaml
    version: '3.8'
    services:
      prometheus:
        image: prom/prometheus:latest
        container_name: prometheus
        ports:
          - "9090:9090"
        volumes:
          - ./prometheus.yml:/etc/prometheus/prometheus.yml

      grafana:
        image: grafana/grafana:latest
        container_name: grafana
        ports:
          - "3000:3000"
        depends_on:
          - prometheus
    ```

3.  **Lancez Docker Compose**:

    ```bash
    docker-compose up -d
    ```

4.  **Lancez l'application Spring Boot**:

    ```bash
    mvn spring-boot:run
    ```

## Visualisation

1.  **Prometheus**: Accédez à `http://localhost:9090`. Dans l'onglet "Status" -> "Targets", vous devriez voir votre application Spring Boot.
2.  **Grafana**: Accédez à `http://localhost:3000` (login/password: admin/admin).
    -   Ajoutez Prometheus comme source de données (Data Source).
    -   Créez un nouveau tableau de bord ou importez-en un existant (par exemple, le dashboard [Spring Boot 2.1 Statistics](https://grafana.com/grafana/dashboards/10280) est un bon point de départ) pour visualiser les métriques de votre application.
