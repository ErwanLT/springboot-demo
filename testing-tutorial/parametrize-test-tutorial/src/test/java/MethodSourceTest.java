import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodSourceTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    void checkExplicitMethodSource(String word) {
        assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n’est pas alphanumérique");
    }

    static Stream<String> testArgs() {
        return Stream.of("a1", "b2");
    }

    @ParameterizedTest
    @MethodSource
    void checkImplicitMethodSource(String word) {
        assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n’est pas alphanumérique");
    }

    static Stream<String> checkImplicitMethodSource() {
        return Stream.of("a1", "b2");
    }

    @ParameterizedTest
    @MethodSource
    void checkMultiArgumentsMethodSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, number % 2);
    }

    static Stream<Arguments> checkMultiArgumentsMethodSource() {
        return Stream.of(Arguments.of(2, "pair"),
                Arguments.of(3, "impair"));
    }

    @ParameterizedTest
    @MethodSource(
            "providers.ExternalMethodSourceProvider#checkExternalMethodSourceArgs")
    void checkExternalMethodSource(String word) {
        assertTrue(StringUtils.isAlphanumeric(word),
                "Le mot fourni n’est pas alphanumérique");
    }
}