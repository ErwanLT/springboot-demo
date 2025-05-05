---
layout: default
title: Prometheus
---

# Superviser votre application Spring Boot grâce à Prometheus et Grafana

Dans le développement d'une application, il ne suffit pas de la construire et de la sécuriser, il est également crucial de surveiller son état de santé en continu.  
La surveillance permet de réagir rapidement face à des anomalies ou à des problèmes de performance, assurant ainsi une meilleure stabilité et une réactivité accrue.  
Dans cet article, nous allons explorer comment intégrer deux outils essentiels : [**Prometheus**](https://prometheus.io/?ref=sfeir.dev) et [**Grafana**](https://grafana.com/?ref=sfeir.dev), afin de monitorer les métriques d’une API REST.

**Prometheus** nous permettra de collecter des métriques détaillées sur le comportement de notre API (comme les temps de réponse et la charge système), tandis que **Grafana** nous aidera à les visualiser à travers des tableaux de bord.

## Pré requis

Avoir lu et suivit les différentes étapes de l'article suivant

[Comment créer son projet Spring Boot de zéro !](https://www.sfeir.dev/back/creer-son-projet-spring-boot-de-zero/)

Dans cet exemple, nous utiliserons [Docker](https://www.sfeir.dev/tag/docker/) pour héberger **Prometheus** et **Grafana**.

### Installation de Docker

Nous utiliserons **Docker Desktop**, qui est disponible à la fois pour Windows et macOS. Docker Desktop permet de gérer facilement des conteneurs en local avec une interface graphique conviviale. Si vous ne l'avez pas encore installé, vous pouvez le télécharger directement depuis le site officiel [Docker Desktop](https://www.docker.com/products/docker-desktop/?ref=sfeir.dev).

Une fois Docker Desktop installé, vous pouvez lancer Docker en arrière-plan et vérifier son bon fonctionnement en exécutant une commande simple dans le terminal, telle que :

```bash
docker --version
```

Si Docker est correctement installé, vous verrez la version actuellement exécutée. Vous pourrez ensuite procéder à l'importation des images Docker nécessaires pour Prometheus et Grafana, comme expliqué dans la section suivante.

### Importer les images de Prometheus et Grafana

Pour ajouter **Prometheus** et **Grafana** à votre registre local Docker, rendez-vous sur **Docker Hub** et recherchez les images correspondantes :

- [Prometheus](https://hub.docker.com/r/prom/prometheus?ref=sfeir.dev)
- [Grafana](https://hub.docker.com/r/grafana/grafana?ref=sfeir.dev)

ℹ️ Vous pouvez directement chercher ces dernières depuis Docker Desktop.

Si tout c'est bien passé, vous devriez avoir quelque chose qui ressemble à ça :

[![](https://www.sfeir.dev/content/images/2024/10/image-4.png)](https://www.sfeir.dev/content/images/2024/10/image-4.png)

## Enrichissons notre pom.xml

Pour que notre API expose des métriques, nous devons ajouter certaines dépendances à notre fichier `pom.xml` :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Actuators et Endpoints

La première dépendance nous permet d'exposer des métriques intéressantes sur notre application via **Spring Boot Actuator**. Après avoir démarré votre application, rendez-vous sur `http://localhost:8080/actuator`.

vous devriez voir quelque chose comme ceci :

[![](https://www.sfeir.dev/content/images/2024/10/image-5-1.png)](https://www.sfeir.dev/content/images/2024/10/image-5-1.png)

Si jamais vous n'en avez pas autant, et surtout si vous ne voyez pas celle sur Prometheus, ne vous inquiétez pas, il suffit juste d'ajuster vos properties en rajoutant la ligne suivante.

```properties
management.endpoints.web.exposure.include=*
```

### Prometheus et Scraping des Métriques

Avec la deuxième dépendance (Micrometer pour Prometheus), un nouvel **endpoint Prometheus** est ajouté dans Actuator. Si vous accédez à l'URL `/actuator/prometheus`, vous verrez une liste de valeurs représentant les métriques de votre application, telles que :

- Informations sur la JVM
- Statut des requêtes HTTP sur vos endpoints
- Événements de logs

Nous devons maintenant configurer **Prometheus** pour collecter ces métriques.

## Configurer Prometheus

### Fichier de configuration

Nous allons créer un fichier `prometheus.yml` à la racine du projet pour configurer Prometheus afin qu'il collecte les données de notre API. Voici un exemple de configuration :

```yaml
global:
  scrape_interval:     15s 
  evaluation_interval: 15s 

rule_files:
# - "first_rules.yml"
# - "second_rules.yml"

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['127.0.0.1:9090']

  - job_name: 'spring-actuator'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s
    static_configs:
      - targets: ['XXXXXX:8080']
```

Remplacez `XXXXXX` par l'adresse IP de votre API. Comme Prometheus fonctionnera dans un conteneur Docker, il ne reconnaîtra pas `localhost`.

### Dockerfile pour Prometheus

Le dockerfile sert à construire l'image Docker qui contiendra Prometheus, ainsi que la configuration que nous venons de définir dans `prometheus.yml`. Voici les étapes pour créer ce fichier.

```dockerfile
# Utiliser l'image officielle de Prometheus comme base
FROM prom/prometheus
# Ajouter le fichier de configuration Prometheus à l'intérieur du conteneur
ADD prometheus.yml /etc/prometheus/prometheus.yml
```

### Construction de l'image Docker

Après avoir créé le fichier `prometheus.yml` et le `Dockerfile`, il faut maintenant construire l'image Docker pour Prometheus avec la nouvelle configuration.

Exécute cette commande dans le répertoire où se trouvent le `Dockerfile` et le fichier `prometheus.yml` :

```bash
docker build -t my-prometheus .
```

Cela créera une image Docker appelée `my-prometheus`, qui sera visible depuis Docker Desktop

[![](https://www.sfeir.dev/content/images/2024/10/image-6.png)](https://www.sfeir.dev/content/images/2024/10/image-6.png)

Nous pouvons maintenant lancé le conteneur Docker qui correspond à notre image

```bash
docker run -d --name prometheus-container -p 9090:9090 my-prometheus
```

et nous rendre à l'adresse suivante : [http://localhost:9090/](http://localhost:9090/?ref=sfeir.dev)

## Configurer Grafana

### Connexion

Maintenant, il nous reste à démarrer le conteneur pour Grafana et d'aller à l'adresse suivante afin d'accéder à la page de connexion : [http://localhost:3000/login](http://localhost:3000/login?ref=sfeir.dev)

[![](https://www.sfeir.dev/content/images/2024/10/image-7.png)](https://www.sfeir.dev/content/images/2024/10/image-7.png)

Et comme [la sécurité est une chose sérieuse](https://www.sfeir.dev/securite/le-palmares-2023-des-mots-de-passe-les-plus-courants/), les crédentiels utilisés pour se connecter font partis des plus complexe au monde... `admin/admin`.

### Configurer la source de données

Depuis le menu latéral gauche, cliquez sur Connection > Data sources et cherchez Prometheus.

Dans la partie connection, renseignez l'adresse IP du conteneur Docker ou tourne votre image de Prometheus.

[![](https://www.sfeir.dev/content/images/2024/10/image-8.png)](https://www.sfeir.dev/content/images/2024/10/image-8.png)

Il ne vous reste plus qu'a valider la configuration.

## Création de tableaux de bord

Une fois connecté à Grafana, vous pouvez créer des tableaux de bord pour visualiser les métriques de votre API. Par exemple, vous pouvez créer un tableau pour afficher le nombre de requêtes HTTP vers vos endpoints, triées par code de statut.

[![](https://www.sfeir.dev/content/images/2024/10/image-9.png)](https://www.sfeir.dev/content/images/2024/10/image-9.png)

Ici je n'affiche que le nombre de requête vers mon endpoint `hello/{name}` qui ont fini avec un statut 200.  
Mais l'on peut plus ou moins facilement créer d'autres représentations pour surveiller l'état de santé de notre application.

De plus, plusieurs dashboards déjà existants peuvent être importés dans votre Grafana local depuis [https://grafana.com/grafana/dashboards/](https://grafana.com/grafana/dashboards/?ref=sfeir.dev).

## En conclusion

Nous avons vu comment mettre en place un système de monitoring pour une API REST en utilisant Prometheus et Grafana.  
Grâce à **Prometheus**, nous pouvons collecter des métriques précises sur les performances de notre API, comme les temps de réponse ou le nombre de requêtes, tandis que **Grafana** permet de visualiser ces données sous forme de tableaux de bord intuitifs et dynamiques.

Ce processus de monitoring est crucial pour détecter et résoudre rapidement les problèmes de performance, permettant ainsi de garantir une expérience utilisateur optimale et une meilleure résilience des applications en production.  
Pour aller plus loin, nous pourrions envisager d'intégrer des alertes dans Prometheus et Grafana pour être notifié en temps réel des anomalies ou des seuils critiques, mais ceci fera l'objet d'un autre article.