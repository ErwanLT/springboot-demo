/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of modulith-tutorial.
 *
 * modulith-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * modulith-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with modulith-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
