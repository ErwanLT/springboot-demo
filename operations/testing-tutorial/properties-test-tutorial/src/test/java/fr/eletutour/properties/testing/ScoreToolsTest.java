package fr.eletutour.properties.testing;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.PropertyDefaults;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.CharRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.LongRange;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@PropertyDefaults(tries = 500)
class ScoreToolsTest {

    @Property
    void bonusShouldNeverReduceScore(
            @ForAll @LongRange(min = 0, max = Integer.MAX_VALUE)
            long score,
            @ForAll @IntRange(min = 0, max = 100)
            int bonusPct) {

        var result = ScoreTools.applyBonus(score, bonusPct);
        assertTrue(result >= score);
    }

    @Property
    void generatedValuesShouldBePositive(
            @ForAll
            @IntRange(min = 1, max = 1000)
            int value) {

        assertTrue(value > 0);
    }

    @Property
    void sortingShouldPreserveSize(
            @ForAll List<Integer> values) {

        List<Integer> sorted =
                values.stream()
                        .sorted()
                        .toList();

        assertEquals(values.size(),
                sorted.size());
    }

    /*@Property
    void upperCaseShouldNotChangeLength(
            @ForAll String value) {

        assertEquals(
                value.length(),
                value.toUpperCase().length());
    }*/

    @Property
    void upperCaseShouldNotChangeLengthAlpha(
            @ForAll @AlphaChars
            String value) {

        assertEquals(
                value.length(),
                value.toUpperCase().length());
    }

    @Property
    void upperCaseShouldNotChangeLengthRestricted(
            @ForAll @CharRange(from = 'a', to = 'z') String value) {
        assertEquals(
                value.length(),
                value.toUpperCase().length());
    }
}