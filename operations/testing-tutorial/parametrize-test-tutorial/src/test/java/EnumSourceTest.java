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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test démontrant l'utilisation de @EnumSource pour les tests paramétrés.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @EnumSource avec une énumération complète</li>
 *     <li>L'utilisation de @EnumSource avec des noms spécifiques</li>
 *     <li>Les tests sur les valeurs d'énumération</li>
 * </ul>
 */
public class EnumSourceTest {
    /**
     * Test vérifiant que toutes les valeurs de l'énumération Month sont non nulles.
     * Utilise @EnumSource pour tester toutes les valeurs de l'énumération.
     *
     * @param month Le mois à tester
     */
    @ParameterizedTest
    @EnumSource(Month.class)
    void checkEnumSourceValue(Month month) {
        assertNotNull(month);
    }

    /**
     * Test vérifiant que les mois spécifiés commencent par "JU".
     * Utilise @EnumSource pour tester uniquement les mois JUIN et JUILLET.
     *
     * @param month Le mois à tester
     */
    @ParameterizedTest
    @EnumSource(names = {"JUIN", "JUILLET"})
    public void checkEnumSourceNames(Month month) {
        assertTrue(month.name().startsWith("JU"));
    }
}