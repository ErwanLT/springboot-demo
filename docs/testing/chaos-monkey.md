---
layout: default
title: Chaos Monkey
---

# Introduisez du chaos dans votre application Spring Boot
Mais pourquoi vouloir introduire du chaos **volontairement** dans mon application ?  
C'est sans doute la question qui vous a traversé l'esprit en lisant le titre de cet article.  
Et c'est ce que nous allons voir dans cet article en introduisant divers types de chaos dans une application [Spring Boot](https://www.sfeir.dev/back/back-spring-boot/).

## Le chaos engineering

Le **Chaos Engineering** est une discipline qui consiste à tester la résilience des systèmes en introduisant des perturbations contrôlées. L’objectif est d’identifier les failles avant qu’elles ne se manifestent en production et de s’assurer que l’application peut **tolérer les pannes** sans compromettre son bon fonctionnement.

### Pourquoi utiliser le chaos engineering ?

Les systèmes modernes sont **distribués, complexes et dynamiques** (microservices, cloud, conteneurs…). Les pannes sont inévitables :  
✅ **Tester la robustesse** face aux pannes de réseau, surcharges ou erreurs applicatives.  
✅ **Identifier les points faibles** avant qu’un incident ne survienne en production.  
✅ **Améliorer la résilience** en optimisant la tolérance aux erreurs et en renforçant l’architecture.

### Exemple d'outils de chaos engineering

Développé par Netflix, **Chaos Monkey** injecte des pannes aléatoires dans un environnement pour tester sa robustesse.

[

L’armée des singes du chaos : définition

Découvrez pourquoi introduire du chaos dans votre infrastructure, une pratique qui peut sembler contre-intuitive, peut finalement vous permettre d’être plus résilient.

![](https://www.sfeir.dev/content/images/icon/plume-point-154.png)sfeir.dev - Le média incontournable pour les passionnés de tech et d'intelligence artificielleErwan Le Tutour

![](https://www.sfeir.dev/content/images/thumbnail/simainArmy-1.png)

](https://www.sfeir.dev/armee-des-singes-du-chaos-definition/)

## Chaos Monkey pour Spring Boot

[

GitHub - codecentric/chaos-monkey-spring-boot: Chaos Monkey for Spring Boot

Chaos Monkey for Spring Boot. Contribute to codecentric/chaos-monkey-spring-boot development by creating an account on GitHub.

![](https://www.sfeir.dev/content/images/icon/pinned-octocat-093da3e6fa40-13.svg)GitHubcodecentric

![](https://www.sfeir.dev/content/images/thumbnail/chaos-monkey-spring-boot)

](https://github.com/codecentric/chaos-monkey-spring-boot?ref=sfeir.dev)

Chaos Monkey for Spring Boot est un projet [open source](https://www.sfeir.dev/open-source-et-au-dela/) inspiré de la Simian Army de [Netflix](https://www.sfeir.dev/tendances/netflix-fete-ses-10-ans-en-france/) mais centré sur ... Spring Boot.

Le but du projet ? Après avoir mis en place pléthore de [tests unitaires et d'intégration](https://www.sfeir.dev/product/pourquoi-tester-son-code/), avoir entre 70-80% de couverture de code, il reste encore une zone d'incertitude :

- Comment l'application fera-t-elle face à de la latence ?
- Et si un service ne répondait plus ?

Ce sont ces questions qui ont conduit à la création de ce projet.

### Installation dans Spring Boot

Pour ajouter Chaos Monkey dans votre application Spring Boot, il vous faut modifier votre fichier `pom.xml` afin d'y rajouter la dépendance suivante :

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>chaos-monkey-spring-boot</artifactId>
    <version>3.1.2</version>
</dependency>
```

dépendance chaos monkey

Et ensuite d'activer le profil adéquat dans votre fichier `application.properties`

```properties
spring.profiles.active=chaos-monkey
```

Au démarrage de votre application, en plus de la traditionnelle bannière Spring ([si vous n'avez pas personnalisé celle-ci](https://www.sfeir.dev/back/comment-avoir-une-banniere-spring-personnalisee/)) vous devriez voir dans vos logs ceci :

```log
     _____ _                       __  __             _
    / ____| |                     |  \/  |           | |
   | |    | |__   __ _  ___  ___  | \  / | ___  _ __ | | _____ _   _
   | |    | '_ \ / _` |/ _ \/ __| | |\/| |/ _ \| '_ \| |/ / _ | | | |
   | |____| | | | (_| | (_) \__ \ | |  | | (_) | | | |   |  __| |_| |
    \_____|_| |_|\__,_|\___/|___/ |_|  |_|\___/|_| |_|_|\_\___|\__, |
                                                                __/ |
    _ready to do evil!                                         |___/

:: Chaos Monkey for Spring Boot                                    ::
```

Nous sommes maintenant prêts à déchaîner le chaos.

[![](https://media.tenor.com/wO1dRf_xGhgAAAAC/laughing-butters-stotch.gif)](https://media.tenor.com/wO1dRf_xGhgAAAAC/laughing-butters-stotch.gif)

Enfin pas tout à fait.

### Activation

Chaos Monkey for Spring Boot est livré avec des APIs qui permettent de modifier la configuration de ce dernier au runtime de l'application.

Pour profiter de cette fonctionnalité, il faut au préalable avoir inclus les actuators dans vos dépendances, et pour les plus curieux qui veulent avoir les différents contrats des APIs l'on peut également ajouter celle de l'UI de [swagger](https://www.sfeir.dev/back/migrer-de-swagger-2-a-openapi-3/) :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.4</version>
</dependency>
```

dépendance des actuators

Et modifier encore une fois nos propriétés :

```properties
management.endpoint.chaosmonkey.enabled=true
management.endpoints.web.exposure.include=health,info,chaosmonkey

springdoc.show-actuator=true
chaos.monkey.apidoc.enabled=true
```

[![](https://www.sfeir.dev/content/images/2025/02/image.png)](https://www.sfeir.dev/content/images/2025/02/image.png)

Il nous reste alors plus qu'a appeler `/actuator/chaosmonkey/enable` pour activer Chaos Monkey.

## Déchainer le chaos

Pour ce cas pratique, j'ai créé 2 APIs avec une architecture REST tout ce qu'il y a de plus de classique :

- Controller
- Service
- Repository

[![](https://www.sfeir.dev/content/images/2025/02/image-1.png)](https://www.sfeir.dev/content/images/2025/02/image-1.png)

Chacune avec ses propres exceptions.

### Définir ou le chaos va survenir

Les watchers jouent le rôle de singes du chaos pour les différents types de composant Spring Boot.

- @Controller
- @RestController
- @Service
- @Repository
- @Component

Avec l'aide de [Spring AOP](https://docs.spring.io/spring-framework/reference/core/aop.html?ref=sfeir.dev), Chaos Monkey détecte l'exécution d'une méthode publique dans un bean surveillé et choisira soit de ne rien faire, soit de lancer une de ses attaques.  
Nous pouvons modifier ces configurations grâce à l'API `/actuator/chaosmonkey/watchers`.

```json
{
  "controller": false,
  "restController": true,
  "service": false,
  "repository": false,
  "component": false
}
```

exemple de requête de mise à jour de watcher

Ici nous indiquons que nous souhaitons attaquer nos composants annotés `@RestController` (nous pouvons très bien mettre **true** partout).

### Définir le type de chaos que nous souhaitons

Grâce à l'API `/actuator/chaosmonkey/assaults` nous pouvons décider quel type de chaos nous voulons provoquer dans notre application.

- Latence
- Exception
- Kill de l'application via cron
- Mémoire de la JVM

```json
{
  "level": 5,
  "deterministic": true,
  "latencyRangeStart": 5000,
  "latencyRangeEnd": 10000,
  "latencyActive": true,
  "watchedCustomServices": [
    "fr.eletutour.controller.ArticleController"
  ]
}
```

exemple de requête de mise à jour d'assault

Regardons de plus près ce que va faire cette requête :

- **level** : indique le nombre de requête à attaquer (1 = chaque requête, 5 toutes les 5 requêtes).
- **deterministic** : Si on veut que le niveau soit déterminé, attaqué X requête ou bien une chance sur X d'être attaqué.
- **latencyRangeStart et latencyRangeEnd** : le minimum et le maximum de latence à ajouter au requête en ms.
- **latencyActive** : activation des attaques de type latence.
- **watchedCustomServices** : limite le service sur lequel déclenché le chaos, si vide pas de limite, tout y passera en fonction des watchers préalablement choisit.

Voyons maintenant ce que peut donner un appel à mon API article avec et sans chaos.

[![](https://www.sfeir.dev/content/images/2025/02/image-6.png)](https://www.sfeir.dev/content/images/2025/02/image-6.png)

appel sans chaos

[![](https://www.sfeir.dev/content/images/2025/02/image-7.png)](https://www.sfeir.dev/content/images/2025/02/image-7.png)

appel avec chaos

Comme vous pouvez le constater, le temps de réponse de mon API a grandement augmenté, et **je ne cible que les RestController**, imaginez si j'avais décidé de cibler également les Services et les Repository.

## Conclusion

Je ne vous ai montré ici, qu'une infime partie du chaos que vous pouvez déclencher dans vos applications, il ne reste qu'à vous lancer pour découvrir toutes les possibilités de cet outil.  
Mais n'oubliez pas une chose, vous allez devoir apprendre à devenir sociable et très rapidement si vous ne l'êtes pas déjà, car si jamais vous décidez de vous lancer dans le chaos engineering sans prévenir vos collègues à l'avance, car vous allez sans doute avoir beaucoup à faire avec eux quand le chaos frappera.

[![](https://media.tenor.com/cK8cTETiPwAAAAAC/laughing-butters-stotch.gif)](https://media.tenor.com/cK8cTETiPwAAAAAC/laughing-butters-stotch.gif)