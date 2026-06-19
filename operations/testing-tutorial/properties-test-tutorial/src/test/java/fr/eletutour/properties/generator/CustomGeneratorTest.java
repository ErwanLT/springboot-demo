package fr.eletutour.properties.generator;

import fr.eletutour.properties.testing.generator.Email;
import fr.eletutour.properties.testing.model.User;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.time.api.constraints.DateRange;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static fr.eletutour.properties.generator.DateGenerators.datesBetween;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomGeneratorTest {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$"
    );

    @Property
    void emailShouldBeValid(@ForAll @Email String email) {
        assertTrue(EMAIL_PATTERN.matcher(email).matches());
    }

    @Property
    void emailShouldUseCorporateDomain(@ForAll @Email(domains = {"company.com", "corp.io"}) String email) {
        assertTrue(email.endsWith(".com") || email.endsWith(".io"));
    }

    @Property
    void birthdayShouldBeInThePast(@ForAll("pastDates") LocalDate birthday) {
        assertTrue(birthday.isBefore(LocalDate.now()));
    }

    @Property
    void birthdayShouldBeInThePast2(@ForAll @DateRange(min = "1900-01-01", max = "2026-01-01") LocalDate birthday) {
        assertTrue(birthday.isBefore(LocalDate.now()));
    }


    @Provide
    Arbitrary<LocalDate> pastDates() {
        return datesBetween(LocalDate.of(1900, 1, 1), LocalDate.of(2026, 1, 1));
    }

    @Property
    void userAgeShouldBePositive(@ForAll("users") User user) {
        assertTrue(user.getAge() > 0);
    }

    @Provide
    Arbitrary<User> users() {
        return UserGenerator.users();
    }
}
