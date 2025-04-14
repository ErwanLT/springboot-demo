package fr.eletutour.aspect;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class DroolsAspect {
    private static final Logger logger = LoggerFactory.getLogger(DroolsAspect.class);
    private final KieContainer kieContainer;

    public DroolsAspect(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @Around("@annotation(fr.eletutour.annotation.DroolsRule)")
    public Object applyDroolsRules(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Interception de la méthode avec @DroolsRule: {}, arguments: {}",
                joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));

        // Récupérer les arguments de la méthode
        Object[] args = joinPoint.getArgs();
        Transaction transaction = null;
        Account account = null;

        // Identifier Transaction et Account dans les arguments
        for (Object arg : args) {
            if (arg instanceof Transaction) {
                transaction = (Transaction) arg;
            } else if (arg instanceof Account) {
                account = (Account) arg;
            }
        }

        // Vérifier que Transaction et Account sont présents
        if (transaction == null) {
            logger.error("Aucune Transaction trouvée dans les arguments");
            throw new IllegalArgumentException("Transaction requise pour appliquer les règles Drools");
        }
        if (account == null) {
            logger.error("Aucun Account trouvé dans les arguments");
            throw new IllegalArgumentException("Account requis pour appliquer les règles Drools");
        }
        logger.info("Faits trouvés: Transaction={}, Account={}", transaction, account);

        // Créer une session Drools
        logger.info("Création de la KieSession 'bankSession'");
        Logger logger = LoggerFactory.getLogger("RulesLogger");
        KieSession kieSession = kieContainer.newKieSession("bankSession");
        kieSession.setGlobal("logger", logger);
        if (kieSession == null) {
            logger.error("Échec de la création de la KieSession 'bankSession'");
            throw new IllegalStateException("KieSession 'bankSession' non trouvée");
        }

        try {
            // Insérer les faits
            kieSession.insert(transaction);
            kieSession.insert(account);
            logger.info("Faits insérés: Transaction={}, Account={}", transaction, account);

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