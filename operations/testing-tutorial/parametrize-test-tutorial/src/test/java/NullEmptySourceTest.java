import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test démontrant l'utilisation des annotations @NullSource, @EmptySource et @NullAndEmptySource.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @NullSource pour tester les valeurs nulles</li>
 *     <li>L'utilisation de @EmptySource pour tester les chaînes vides</li>
 *     <li>L'utilisation de @NullAndEmptySource pour tester les valeurs nulles et vides</li>
 *     <li>La combinaison de @NullAndEmptySource avec @ValueSource</li>
 * </ul>
 */
public class NullEmptySourceTest {
    /**
     * Test vérifiant le comportement avec une valeur nulle.
     * Utilise @NullSource pour fournir une valeur nulle.
     *
     * @param value La valeur à tester (sera null)
     */
    @ParameterizedTest
    @NullSource
    void checkNull(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    /**
     * Test vérifiant le comportement avec une chaîne vide.
     * Utilise @EmptySource pour fournir une chaîne vide.
     *
     * @param value La valeur à tester (sera une chaîne vide)
     */
    @ParameterizedTest
    @EmptySource
    void checkEmpty(String value) {
        assertTrue(StringTools.isBlank(value));
    }

    /**
     * Test vérifiant le comportement avec des valeurs nulles et vides.
     * Utilise @NullAndEmptySource pour fournir à la fois des valeurs nulles et des chaînes vides.
     *
     * @param value La valeur à tester (sera null ou une chaîne vide)
     */
    @ParameterizedTest
    @NullAndEmptySource
    void checkNullAndEmpty(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    /**
     * Test vérifiant le comportement avec des valeurs nulles, vides et des espaces.
     * Combine @NullAndEmptySource avec @ValueSource pour tester différents cas de chaînes vides.
     *
     * @param value La valeur à tester (sera null, une chaîne vide, ou une chaîne contenant uniquement des espaces)
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   " })
    void checkNullEmptyAndBlank(String value) {
        assertTrue(StringTools.isBlank(value));
    }
}