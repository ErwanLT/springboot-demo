package providers;

import java.util.stream.Stream;

public class ExternalMethodSourceProvider {
    static Stream<String> checkExternalMethodSourceArgs() {
        return Stream.of("a1",
                "b2");
    }
}