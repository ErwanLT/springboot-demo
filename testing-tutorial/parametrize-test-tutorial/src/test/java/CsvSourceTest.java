import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvSourceTest {
    @ParameterizedTest
    @CsvSource({ "2, pair",
            "3, impair"})
    void checkCsvSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/csvSource.csv", numLinesToSkip = 1)
    void checkCsvFileSource(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/csvSource_attributes.csv",
            delimiterString = "|",
            lineSeparator = "||",
            numLinesToSkip = 1)
    void checkCsvFileSourceAttributes(int number, String expected) {
        assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
    }
}