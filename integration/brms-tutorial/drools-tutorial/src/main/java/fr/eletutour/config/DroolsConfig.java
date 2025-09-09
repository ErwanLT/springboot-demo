/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of drools-tutorial.
 *
 * drools-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drools-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drools-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Configuration du moteur Drools pour l'application.
 * Cette classe configure le moteur de règles Drools en :
 * - Chargant les fichiers de règles (.drl) depuis le classpath
 * - Configurant le KieContainer avec les règles compilées
 * - Gérant la compilation et la validation des règles
 * - Configurant les sessions Drools
 */
@Configuration
@EnableAspectJAutoProxy
public class DroolsConfig {

    private static final Logger logger = LoggerFactory.getLogger(DroolsConfig.class);
    private static final KieServices kieServices = KieServices.Factory.get();
    private static final String RULES_PATH = "rules/*.drl";

    /**
     * Crée et configure le KieContainer pour l'application.
     * Le KieContainer est le point d'entrée principal pour l'exécution des règles Drools.
     * Il charge et compile les règles depuis les fichiers .drl et configure
     * les sessions Drools nécessaires.
     *
     * @return Le KieContainer configuré
     * @throws IllegalStateException si la compilation des règles échoue ou si aucun fichier de règles n'est trouvé
     */
    @Bean
    public KieContainer kieContainer() {
        try {
            logger.info("Initialisation de la configuration Drools");

            // Créer un KieFileSystem
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            logger.info("KieFileSystem créé");

            // Charger tous les fichiers .drl du dossier rules
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] ruleResources = resolver.getResources("classpath:" + RULES_PATH);
            if (ruleResources.length == 0) {
                logger.error("Aucun fichier de règles trouvé dans {}", RULES_PATH);
                throw new IllegalStateException("Aucun fichier de règles trouvé dans " + RULES_PATH);
            }

            // Convertir les ressources Spring en ressources Drools
            for (Resource springResource : ruleResources) {
                // Utiliser le chemin relatif complet (rules/nom_du_fichier)
                String resourcePath = "rules/" + springResource.getFilename();
                logger.info("Tentative de chargement de la ressource : {}", resourcePath);

                // Créer une ressource Drools à partir du chemin relatif
                org.kie.api.io.Resource droolsResource = kieServices.getResources()
                        .newClassPathResource(resourcePath, "UTF-8");
                kieFileSystem.write(droolsResource);
                logger.info("Fichier de règles chargé : {}", resourcePath);
            }

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
        } catch (IOException e) {
            logger.error("Erreur lors du chargement des fichiers de règles", e);
            throw new IllegalStateException("Impossible de charger les fichiers de règles", e);
        } catch (Exception e) {
            logger.error("Erreur lors de la création du KieContainer", e);
            throw new IllegalStateException("Impossible de créer le KieContainer", e);
        }
    }
}