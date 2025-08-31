import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test démontrant l'utilisation de @MethodSource pour les tests paramétrés.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @MethodSource avec une méthode explicite</li>
 *     <li>L'utilisation de @MethodSource avec une méthode implicite</li>
 *     <li>L'utilisation de @MethodSource avec plusieurs arguments</li>
 *     <li>L'utilisation de @MethodSource avec une source externe</li>
 * </ul>
 */
public class MethodSourceTest {
    /**
     * Test vérifiant si les mots sont alphanumériques en utilisant une méthode source explicite.
     * La méthode source est spécifiée explicitement dans l'annotation.
     *
     * @param word Le mot à tester
     */
    @ParameterizedTest
    @MethodSource("testArgs")
    void checkExplicitMethodSource(String word) {
        assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n'est pas alphanumérique");
    }

    /**
     * Fournit les arguments de test pour checkExplicitMethodSource.
     *
     * @return Un flux de chaînes de caractères à tester
     */
    static Stream<String> testArgs() {
        return Stream.of("a1", "b2");
    }

    /**
     * Test vérifiant si les mots sont alphanumériques en utilisant une méthode source implicite.
     * La méthode source est déduite du nom de la méthode de test.
     *
     * @param word Le mot à tester
     */
    @ParameterizedTest
    @MethodSource
    void checkImplicitMethodSource(String word) {
        assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n'est pas alphanumérique");
    }

    /**
     * Fournit les arguments de test pour checkImplicitMethodSource.
     *
     * @return Un flux de chaînes de caractères à tester
     */
    static Stream<String> checkImplicitMethodSource() {
        return Stream.of("a1", "b2");
    }

    /**
     * Test vérifiant si les nombres sont pairs ou impairs en utilisant une méthode source avec plusieurs arguments.
     *
     * @param number Le nombre à tester
     * @param expected Le résultat attendu ("pair" ou "impair")
     */
    @ParameterizedTest
    @MethodSource
    void checkMultiArgumentsMethodSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, number % 2);
    }

    /**
     * Fournit les arguments de test pour checkMultiArgumentsMethodSource.
     *
     * @return Un flux d'arguments contenant des nombres et leurs résultats attendus
     */
    static Stream<Arguments> checkMultiArgumentsMethodSource() {
        return Stream.of(Arguments.of(2, "pair"),
                Arguments.of(3, "impair"));
    }

    /**
     * Test vérifiant si les mots sont alphanumériques en utilisant une méthode source externe.
     * La méthode source est définie dans une classe externe.
     *
     * @param word Le mot à tester
     */
    @ParameterizedTest
    @MethodSource(
            "providers.ExternalMethodSourceProvider#checkExternalMethodSourceArgs")
    void checkExternalMethodSource(String word) {
        assertTrue(StringUtils.isAlphanumeric(word),
                "Le mot fourni n'est pas alphanumérique");
    }
}