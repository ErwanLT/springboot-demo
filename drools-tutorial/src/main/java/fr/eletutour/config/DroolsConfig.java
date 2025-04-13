package fr.eletutour.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class DroolsConfig {

    private static final Logger logger = LoggerFactory.getLogger(DroolsConfig.class);
    private static final KieServices kieServices = KieServices.Factory.get();
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/bank_rules.drl";

    @Bean
    public KieContainer kieContainer() {
        try {
            logger.info("Initialisation de la configuration Drools");

            // Créer un KieFileSystem
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            logger.info("KieFileSystem créé");

            // Vérifier et charger le fichier de règles
            Resource ruleResource = ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL);
            if (ruleResource.getInputStream() == null) {
                logger.error("Fichier de règles introuvable : {}", RULES_CUSTOMER_RULES_DRL);
                throw new IllegalStateException("Fichier de règles introuvable : " + RULES_CUSTOMER_RULES_DRL);
            }
            kieFileSystem.write(ruleResource);
            logger.info("Fichier de règles chargé : {}", RULES_CUSTOMER_RULES_DRL);

            // Créer un kmodule.xml programmatique
            KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
            KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("bankRules");
            kieBaseModel.addPackage("rules");
            kieBaseModel.setDefault(true);
            KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel("bankSession");
            kieSessionModel.setDefault(true);
            logger.info("Configuration de kmodule : kbase=bankRules, ksession=bankSession");

            // Écrire kmodule.xml dans le KieFileSystem
            String kmoduleXml = kieModuleModel.toXML();
            kieFileSystem.writeKModuleXML(kmoduleXml);
            logger.info("kmodule.xml généré : {}", kmoduleXml);

            // Construire le KieModule
            logger.info("Démarrage de la compilation des règles");
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            logger.info("Compilation terminée");

            // Vérifier les erreurs de compilation
            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                logger.error("Erreurs lors de la compilation des règles :");
                for (Message message : kieBuilder.getResults().getMessages(Message.Level.ERROR)) {
                    logger.error("Erreur : {}", message.getText());
                }
                throw new IllegalStateException("Échec de la compilation des règles Drools : " + kieBuilder.getResults().getMessages());
            }
            if (kieBuilder.getResults().hasMessages(Message.Level.WARNING)) {
                logger.warn("Avertissements lors de la compilation des règles :");
                for (Message message : kieBuilder.getResults().getMessages(Message.Level.WARNING)) {
                    logger.warn("Avertissement : {}", message.getText());
                }
            }
            logger.info("Compilation des règles réussie");

            // Créer le KieContainer
            KieModule kieModule = kieBuilder.getKieModule();
            KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
            logger.info("KieContainer créé avec succès, ReleaseId : {}", kieModule.getReleaseId());

            return kieContainer;
        } catch (Exception e) {
            logger.error("Erreur lors de la création du KieContainer", e);
            throw new IllegalStateException("Impossible de créer le KieContainer", e);
        }
    }
}