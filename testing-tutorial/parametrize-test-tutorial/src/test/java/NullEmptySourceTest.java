import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NullEmptySourceTest {
    @ParameterizedTest
    @NullSource
    void checkNull(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    @ParameterizedTest
    @EmptySource
    void checkEmpty(String value) {
        assertTrue(StringTools.isBlank(value));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void checkNullAndEmpty(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   " })
    void checkNullEmptyAndBlank(String value) {
        assertTrue(StringTools.isBlank(value));
    }
}