/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of spel-tutorial.
 *
 * spel-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * spel-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with spel-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.spel;

import fr.eletutour.spel.service.SpelExamples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpelExamplesTest {

    @Autowired
    private SpelExamples spelExamples;

    @Test
    void testLiteralExpressions() {
        assertThat(spelExamples.getStringLiteral()).isEqualTo("Hello World");
        assertThat(spelExamples.getDoubleLiteral()).isEqualTo(100.5);
        assertThat(spelExamples.isBooleanLiteral()).isTrue();
    }

    @Test
    void testBeanReference() {
        assertThat(spelExamples.getCarMake()).isEqualTo("GoodCar");
        assertThat(spelExamples.isCarNewer()).isTrue();
    }

    @Test
    void testPropertyAccess() {
        assertThat(spelExamples.getFromProperties()).isEqualTo("Hello from TEST properties");
        assertThat(spelExamples.getPropertyWithDefault()).isEqualTo("Hello from TEST properties");
    }

    @Test
    void testOperators() {
        assertThat(spelExamples.isCarFrom2021()).isTrue();
        assertThat(spelExamples.isGoodAndNew()).isTrue();
        assertThat(spelExamples.getCarAgeCategory()).isEqualTo("New");
    }

    @Test
    void testCollectionAccess() {
        assertThat(spelExamples.getNumberList()).containsExactly(1, 2, 3, 4);
        assertThat(spelExamples.getFirstNumber()).isEqualTo(1);
        assertThat(spelExamples.getValueMap()).containsEntry("key2", "value2");
        assertThat(spelExamples.getValueFromMap()).isEqualTo("value2");
    }

    @Test
    void testTypeOperatorAndSafeNavigation() {
        assertThat(spelExamples.getRandomUuid()).isNotNull();
        // The UUID is random, so we just check if it's a valid UUID string format
        assertThat(spelExamples.getRandomUuid()).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        // engineHorsepower should be null due to safe navigation on a non-existent property
        assertThat(spelExamples.getEngineHorsepower()).isNull();
    }

    @Test
    void testUtilityMethodCall() {
        assertThat(spelExamples.isIs100GreaterThan50()).isTrue();
    }
}
