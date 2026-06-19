package fr.eletutour.properties.generator;


import fr.eletutour.properties.testing.generator.Email;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.providers.ArbitraryProvider;
import net.jqwik.api.providers.TypeUsage;

import java.util.Set;

public class EmailGenerator implements ArbitraryProvider {

    @Override
    public boolean canProvideFor(TypeUsage targetType) {
        return targetType.isOfType(String.class)
                && targetType.findAnnotation(Email.class).isPresent();
    }

    @Override
    public Set<Arbitrary<?>> provideFor(TypeUsage targetType, SubtypeProvider subtypeProvider) {
        Email annotation = targetType.findAnnotation(Email.class)
                .orElseThrow();

        Arbitrary<String> emailArbitrary = buildEmailArbitrary(annotation);
        return Set.of(emailArbitrary);
    }

    private Arbitrary<String> buildEmailArbitrary(Email annotation) {
        Arbitrary<String> localPart = Arbitraries.strings()
                .withChars("abcdefghijklmnopqrstuvwxyz0123456789._")
                .ofMinLength(1)
                .ofMaxLength(annotation.maxLocalLength())
                .filter(s -> !s.startsWith(".") && !s.startsWith("_")
                        && !s.endsWith(".")   && !s.endsWith("_"));

        Arbitrary<String> domain = buildDomainArbitrary(annotation);

        return Combinators.combine(localPart, domain)
                .as((local, dom) -> local + "@" + dom);
    }

    private Arbitrary<String> buildDomainArbitrary(Email annotation) {
        if (annotation.domains().length > 0) {
            return Arbitraries.of(annotation.domains());
        }

        Arbitrary<String> domainName = Arbitraries.strings()
                .withChars("abcdefghijklmnopqrstuvwxyz0123456789-")
                .ofMinLength(2)
                .ofMaxLength(15)
                .filter(s -> !s.startsWith("-") && !s.endsWith("-"));

        Arbitrary<String> tld = Arbitraries.of("com", "org", "net", "io", "fr", "de", "uk");

        return Combinators.combine(domainName, tld)
                .as((name, ext) -> name + "." + ext);
    }

    @Override
    public int priority() {
        return 10;
    }
}