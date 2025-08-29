package fr.eletutour.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Classe de test pour vérifier l'architecture de l'application en utilisant ArchUnit.
 * Cette classe contient des tests qui vérifient :
 * - Les dépendances entre les différentes couches de l'application
 * - La conformité avec les règles d'architecture définies
 * - L'isolation des composants
 * - La structure des packages
 */
public class ArchitectureTest {

    private static JavaClasses importedClasses;

    /**
     * Initialise les classes à tester en important toutes les classes du package de l'application.
     * Cette méthode est exécutée une seule fois avant tous les tests.
     */
    @BeforeAll
    public static void setup() {
        // Importer toutes les classes du package de l'application
        importedClasses = new ClassFileImporter()
                .importPackages("fr.eletutour.archunit");
    }

    /**
     * Vérifie que les contrôleurs ne dépendent que des services, des modèles et des exceptions.
     * Cette règle garantit que les contrôleurs respectent l'architecture en couches.
     */
    @Test
    public void controllersShouldOnlyDependOnServices() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(RestController.class)
            .or().resideInAPackage("..controller..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..service..", "..model..", "..exception..", "java..", "org.springframework..");

        rule.check(importedClasses);
    }

    /**
     * Vérifie que les services ne dépendent que des repositories, des modèles et des exceptions.
     * Cette règle garantit que la couche service est correctement isolée.
     */
    @Test
    public void servicesShouldOnlyDependOnRepositories() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(Service.class)
            .or().resideInAPackage("..service..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..repository..", "..model..", "..exception..", "java..", "org.springframework..");

        rule.check(importedClasses);
    }

    /**
     * Vérifie que les repositories ne dépendent pas des services ou des contrôleurs.
     * Cette règle garantit que la couche d'accès aux données reste indépendante.
     */
    @Test
    public void repositoriesShouldNotDependOnServicesOrControllers() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(Repository.class)
            .or().resideInAPackage("..repository..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("java..",  "..model..", "org.springframework..");

        rule.check(importedClasses);
    }

    /**
     * Vérifie qu'aucune classe des packages service ou repository ne dépend des contrôleurs.
     * Cette règle garantit que les couches inférieures ne dépendent pas des couches supérieures.
     */
    @Test
    public void noClassesShouldAccessControllers() {
        ArchRule rule = noClasses()
                .that()
                .resideInAnyPackage("..service..", "..repository..")
                .should()
                .dependOnClassesThat().resideInAPackage("..controller..");

        rule.check(importedClasses);
    }
}