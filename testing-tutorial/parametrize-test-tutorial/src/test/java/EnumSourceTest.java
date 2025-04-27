import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumSourceTest {
    @ParameterizedTest
    @EnumSource(Month.class)
    void checkEnumSourceValue(Month month) {
        assertNotNull(month);
    }

    @ParameterizedTest
    @EnumSource(names = {"JUIN", "JUILLET"})
    public void checkEnumSourceNames(Month month) {
        assertTrue(month.name().startsWith("JU"));
    }
}