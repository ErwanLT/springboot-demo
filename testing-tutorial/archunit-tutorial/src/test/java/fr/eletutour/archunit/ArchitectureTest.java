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

public class ArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    public static void setup() {
        // Importer toutes les classes du package de l'application
        importedClasses = new ClassFileImporter()
                .importPackages("fr.eletutour.archunit");
    }

    @Test
    public void controllersShouldOnlyDependOnServices() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(RestController.class)
            .or().resideInAPackage("..controller..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..service..", "..model..", "..exception..", "java..", "org.springframework..");

        rule.check(importedClasses);
    }

    @Test
    public void servicesShouldOnlyDependOnRepositories() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(Service.class)
            .or().resideInAPackage("..service..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..repository..", "..model..", "..exception..", "java..", "org.springframework..");

        rule.check(importedClasses);
    }

    @Test
    public void repositoriesShouldNotDependOnServicesOrControllers() {
        ArchRule rule = classes()
            .that().areAnnotatedWith(Repository.class)
            .or().resideInAPackage("..repository..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("java..",  "..model..", "org.springframework..");

        rule.check(importedClasses);
    }

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