package fr.eletutour.properties.generator;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

import java.time.LocalDate;

public class DateGenerators {
    public static Arbitrary<LocalDate> datesBetween(LocalDate start, LocalDate end) {
        return Arbitraries.longs()
                .between(start.toEpochDay(), end.toEpochDay())
                .map(LocalDate::ofEpochDay);
    }
}