package fr.eletutour.spel;

import fr.eletutour.spel.service.SpelExamples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpelExamplesTest {

    @Autowired
    private SpelExamples spelExamples;

    @Test
    void testLiteralExpressions() {
        assertThat(spelExamples.getStringLiteral()).isEqualTo("Hello World");
        assertThat(spelExamples.getDoubleLiteral()).isEqualTo(100.5);
        assertThat(spelExamples.isBooleanLiteral()).isTrue();
    }

    @Test
    void testBeanReference() {
        assertThat(spelExamples.getCarMake()).isEqualTo("GoodCar");
        assertThat(spelExamples.isCarNewer()).isTrue();
    }

    @Test
    void testPropertyAccess() {
        assertThat(spelExamples.getFromProperties()).isEqualTo("Hello from TEST properties");
        assertThat(spelExamples.getPropertyWithDefault()).isEqualTo("Hello from TEST properties");
    }

    @Test
    void testOperators() {
        assertThat(spelExamples.isCarFrom2021()).isTrue();
        assertThat(spelExamples.isGoodAndNew()).isTrue();
        assertThat(spelExamples.getCarAgeCategory()).isEqualTo("New");
    }

    @Test
    void testCollectionAccess() {
        assertThat(spelExamples.getNumberList()).containsExactly(1, 2, 3, 4);
        assertThat(spelExamples.getFirstNumber()).isEqualTo(1);
        assertThat(spelExamples.getValueMap()).containsEntry("key2", "value2");
        assertThat(spelExamples.getValueFromMap()).isEqualTo("value2");
    }

    @Test
    void testTypeOperatorAndSafeNavigation() {
        assertThat(spelExamples.getRandomUuid()).isNotNull();
        // The UUID is random, so we just check if it's a valid UUID string format
        assertThat(spelExamples.getRandomUuid()).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        // engineHorsepower should be null due to safe navigation on a non-existent property
        assertThat(spelExamples.getEngineHorsepower()).isNull();
    }

    @Test
    void testUtilityMethodCall() {
        assertThat(spelExamples.isIs100GreaterThan50()).isTrue();
    }
}
