package fr.eletutour.aspect;

import fr.eletutour.model.DroolsFact;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Aspect pour appliquer les règles Drools aux méthodes annotées avec @DroolsRule.
 * Cette classe intercepte l'exécution des méthodes marquées avec @DroolsRule
 * et applique les règles métier définies dans les fichiers .drl.
 * Elle gère le cycle de vie des sessions Drools et l'insertion des faits.
 */
@Aspect
@Component
public class DroolsAspect {
    private static final Logger logger = LoggerFactory.getLogger(DroolsAspect.class);
    private final KieContainer kieContainer;

    /**
     * Constructeur de l'aspect Drools.
     *
     * @param kieContainer Le conteneur Drools injecté pour créer les sessions
     */
    public DroolsAspect(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    /**
     * Point de coupe autour des méthodes annotées avec @DroolsRule.
     * Cette méthode :
     * - Récupère les faits Drools parmi les arguments
     * - Crée une session Drools
     * - Insère les faits dans la session
     * - Exécute les règles
     * - Poursuit l'exécution de la méthode originale
     *
     * @param joinPoint Le point de jonction représentant la méthode interceptée
     * @return Le résultat de l'exécution de la méthode
     * @throws Throwable Si une erreur survient pendant l'exécution
     */
    @Around("@annotation(fr.eletutour.annotation.DroolsRule)")
    public Object applyDroolsRules(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Interception de la méthode avec @DroolsRule: {}, arguments: {}",
                joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));

        // Récupérer les faits Drools parmi les arguments
        List<DroolsFact> facts = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof DroolsFact) {
                facts.add((DroolsFact) arg);
            }
        }

        // Vérifier qu'il y a des faits
        if (facts.isEmpty()) {
            logger.error("Aucun fait Drools trouvé dans les arguments");
            throw new IllegalArgumentException("Au moins un fait Drools est requis pour appliquer les règles");
        }
        logger.info("Faits trouvés: {}", facts);

        // Créer une session Drools
        logger.info("Création de la KieSession 'bankSession'");
        KieSession kieSession = kieContainer.newKieSession("bankSession");
        if (kieSession == null) {
            logger.error("Échec de la création de la KieSession 'bankSession'");
            throw new IllegalStateException("KieSession 'bankSession' non trouvée");
        }

        // Configurer le logger comme global
        Logger rulesLogger = LoggerFactory.getLogger("RulesLogger");
        kieSession.setGlobal("logger", rulesLogger);

        try {
            // Insérer les faits
            for (DroolsFact fact : facts) {
                kieSession.insert(fact);
            }
            logger.info("Faits insérés: {}", facts);

            // Exécuter les règles
            logger.info("Exécution des règles Drools");
            int rulesFired = kieSession.fireAllRules();
            logger.info("Nombre de règles exécutées: {}", rulesFired);

            // Continuer l'exécution de la méthode originale
            logger.info("Poursuite de l'exécution de la méthode originale");
            return joinPoint.proceed();
        } finally {
            // Libérer la session
            logger.info("Libération de la KieSession");
            kieSession.dispose();
        }
    }
}