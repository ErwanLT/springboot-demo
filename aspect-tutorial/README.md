# Tutorial AOP

Ce module est un tutoriel sur la programmation orientée aspect (AOP) avec Spring Boot.

L'AOP est un paradigme de programmation qui permet de séparer les préoccupations transversales (comme la journalisation, la sécurité, la gestion des transactions, etc.) du code métier de l'application.

## Aspects implémentés

Ce module implémente les aspects suivants :

*   `fr.eletutour.tutorial.aop.aspect.LoggingAspect` : un aspect qui journalise l'exécution des méthodes du service `TutorialService`.

Voici un extrait du code de l'aspect :

```java
@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* fr.eletutour.tutorial.aop.service.TutorialService.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Avant la méthode : " + joinPoint.getSignature().toLongString());
    }

    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        LOGGER.info("Après la méthode : " + joinPoint.getSignature().toLongString());
    }
}
```

Cet aspect est appliqué au service `TutorialService` :

```java
@Service
public class TutorialService {

    private final Logger LOGGER = LoggerFactory.getLogger(TutorialService.class);

    public void helloMethode() {
        LOGGER.info("Hello");
    }
}
```

## Exécution des tests

Pour exécuter les tests, utilisez la commande suivante à la racine du projet :

```bash
./mvnw -f aaspect-tutorial/pom.xml verify
```
