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

import fr.eletutour.spel.model.Car;
import fr.eletutour.spel.service.DynamicSpelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DynamicSpelServiceTest {

    @Autowired
    private DynamicSpelService dynamicSpelService;

    @Test
    void testEvaluateIsGreaterThan() {
        // Test case where the expression should be true
        boolean result1 = dynamicSpelService.evaluateIsGreaterThan(100);
        assertThat(result1).isTrue();

        // Test case where the expression should be false
        boolean result2 = dynamicSpelService.evaluateIsGreaterThan(10);
        assertThat(result2).isFalse();
    }

    @Test
    void testEvaluateAgainstRootObject() {
        Car testCar = new Car(); // Car's year is 2021 by default

        // Expression: isNewerThan(2020) -> should be true
        boolean result1 = dynamicSpelService.evaluateCarNewerThan(testCar, 2020);
        assertThat(result1).isTrue();

        // Expression: isNewerThan(2022) -> should be false
        boolean result2 = dynamicSpelService.evaluateCarNewerThan(testCar, 2022);
        assertThat(result2).isFalse();
    }
}
