package fr.eletutour.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Test class for Spring Modulith.
 * This class contains tests to verify the modular structure and generate documentation.
 */
public class ModulithTest {
    /**
     * Creates and prints the application module model.
     * This test helps in understanding the modules detected by Spring Modulith.
     */
    @Test
    void createApplicationModuleModel() {
        ApplicationModules modules = ApplicationModules.of(Application.class);
        modules.forEach(System.out::println);
    }

    /**
     * Generates module canvases using the Documenter.
     * These canvases provide a high-level view of the modules.
     */
    @Test
    void createModuleCanvas() {
        ApplicationModules modules = ApplicationModules.of(Application.class);
        new Documenter(modules)
                .writeModuleCanvases();
    }


    /**
     * Verifies the modular structure of the application.
     * This test ensures that there are no cyclic dependencies or other violations.
     */
    @Test
    void verifiesModularStructure() {
        ApplicationModules modules = ApplicationModules.of(Application.class);
        modules.verify();
    }

    /**
     * Creates comprehensive documentation for the modules.
     * This includes PlantUML diagrams for individual modules.
     */
    @Test
    void createModuleDocumentation() {
        ApplicationModules modules = ApplicationModules.of(Application.class);
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }
}
