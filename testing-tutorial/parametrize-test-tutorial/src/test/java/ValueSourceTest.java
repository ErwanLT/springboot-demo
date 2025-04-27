import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueSourceTest {
    @ParameterizedTest
    @ValueSource(ints = { 2, 4 })
    void checkPairNumber(int number) {
        assertEquals(0, MathTools.isEven(number),
                "Le nombre fourni n’est pas un nombre pair");
    }

    @ParameterizedTest
    @ValueSource(strings = { "a1", "b2" })
    void checkAlphanumeric(String word) {
        assertTrue(StringTools.isAlphanumeric(word),
                "Le mot fourni n’est pas alphanumérique");
    }
}