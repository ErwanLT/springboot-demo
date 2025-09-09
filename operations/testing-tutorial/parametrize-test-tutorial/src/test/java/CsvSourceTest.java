/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of parametrize-test-tutorial.
 *
 * parametrize-test-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * parametrize-test-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with parametrize-test-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test démontrant l'utilisation de @CsvSource et @CsvFileSource pour les tests paramétrés.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @CsvSource avec des valeurs en ligne</li>
 *     <li>L'utilisation de @CsvFileSource avec des fichiers CSV</li>
 *     <li>La configuration des attributs de @CsvFileSource</li>
 * </ul>
 */
public class CsvSourceTest {
    /**
     * Test vérifiant si les nombres sont pairs ou impairs en utilisant @CsvSource.
     * Les données de test sont fournies directement dans l'annotation.
     *
     * @param number Le nombre à tester
     * @param expected Le résultat attendu ("pair" ou "impair")
     */
    @ParameterizedTest
    @CsvSource({ "2, pair",
            "3, impair"})
    void checkCsvSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }

    /**
     * Test vérifiant si les nombres sont pairs ou impairs en utilisant @CsvFileSource.
     * Les données de test sont lues depuis un fichier CSV.
     *
     * @param number Le nombre à tester
     * @param expected Le résultat attendu ("pair" ou "impair")
     */
    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/csvSource.csv", numLinesToSkip = 1)
    void checkCsvFileSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }

    /**
     * Test vérifiant si les nombres sont pairs ou impairs avec une configuration personnalisée de @CsvFileSource.
     * Utilise des délimiteurs et séparateurs de ligne personnalisés.
     *
     * @param number Le nombre à tester
     * @param expected Le résultat attendu ("pair" ou "impair")
     */
    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/csvSource_attributes.csv",
            delimiterString = "|",
            lineSeparator = "||",
            numLinesToSkip = 1)
    void checkCsvFileSourceAttributes(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }
}