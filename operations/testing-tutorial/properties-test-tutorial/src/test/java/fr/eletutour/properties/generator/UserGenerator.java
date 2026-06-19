package fr.eletutour.properties.generator;

import fr.eletutour.properties.testing.model.User;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;

public class UserGenerator {
    public static Arbitrary<User> users() {
        return Combinators.combine(
                Arbitraries.strings().alpha().ofLength(5),  // nom
                Arbitraries.integers().between(18, 100)     // âge
        ).as((name, age) -> new User(name, age));
    }
}