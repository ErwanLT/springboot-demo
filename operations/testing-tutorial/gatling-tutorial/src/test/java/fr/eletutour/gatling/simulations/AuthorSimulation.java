package fr.eletutour.gatling.simulations;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep.RampRate.RampRateOpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Simulation de test de performance pour les endpoints d'auteurs.
 * Cette simulation :
 * <ul>
 *     <li>Teste la création d'auteurs sous charge</li>
 *     <li>Simule une montée en charge progressive</li>
 *     <li>Vérifie les temps de réponse et le taux de succès</li>
 *     <li>Génère des données de test aléatoires</li>
 * </ul>
 */
@Slf4j
public class AuthorSimulation extends Simulation {

    private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = setupProtocolForSimulation();
    private static final Iterator<Map<String, Object>> FEED_DATA = setupTestFeedData();
    private static final ScenarioBuilder POST_SCENARIO_BUILDER = buildPostScenario();

    /**
     * Constructeur de la simulation.
     * Configure le scénario de test avec :
     * <ul>
     *     <li>Le profil d'injection de charge</li>
     *     <li>Les assertions sur les performances</li>
     *     <li>Le protocole HTTP</li>
     * </ul>
     */
    public AuthorSimulation() {
        setUp(POST_SCENARIO_BUILDER.injectOpen(postEndpointInjectionProfile())
                .protocols(HTTP_PROTOCOL_BUILDER)).assertions(global().responseTime()
                .max()
                .lte(10000), global().successfulRequests()
                .percent()
                .gt(90d));
    }

    /**
     * Configure le protocole HTTP pour la simulation.
     * Définit :
     * <ul>
     *     <li>L'URL de base</li>
     *     <li>Les en-têtes HTTP</li>
     *     <li>Les paramètres de connexion</li>
     * </ul>
     *
     * @return Le builder du protocole HTTP configuré
     */
    private static HttpProtocolBuilder setupProtocolForSimulation() {
        return http.baseUrl("http://localhost:8080")
                .acceptHeader("application/json")
                .maxConnectionsPerHost(10)
                .userAgentHeader("Gatling/Performance Test");
    }

    /**
     * Définit le profil d'injection de charge.
     * Configure :
     * <ul>
     *     <li>Le nombre total d'utilisateurs</li>
     *     <li>La vitesse de montée en charge</li>
     *     <li>La durée des phases de test</li>
     * </ul>
     *
     * @return Le profil d'injection de charge
     */
    private RampRateOpenInjectionStep postEndpointInjectionProfile() {
        int totalDesiredUserCount = 50;
        double userRampUpPerInterval = 10;
        double rampUpIntervalSeconds = 20;
        int totalRampUptimeSeconds = 20;
        int steadyStateDurationSeconds = 60;
        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
                .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    }

    /**
     * Configure les données de test.
     * Génère :
     * <ul>
     *     <li>Des noms d'auteurs aléatoires</li>
     *     <li>Des biographies aléatoires</li>
     * </ul>
     *
     * @return Un itérateur de données de test
     */
    private static Iterator<Map<String, Object>> setupTestFeedData() {
        Faker faker = new Faker();
        Iterator<Map<String, Object>> iterator;
        iterator = Stream.generate(() -> {
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("name", faker.name()
                            .fullName());
                    stringObjectMap.put("bio", faker.lorem().sentence(15));
                    return stringObjectMap;
                })
                .iterator();
        return iterator;
    }

    /**
     * Construit le scénario de test.
     * Définit :
     * <ul>
     *     <li>Les étapes du scénario</li>
     *     <li>Les vérifications de réponse</li>
     *     <li>Le format des données envoyées</li>
     * </ul>
     *
     * @return Le builder du scénario
     */
    private static ScenarioBuilder buildPostScenario() {
        return CoreDsl.scenario("Load Test Creating Author")
                .feed(FEED_DATA)
                .exec(session -> {
                    System.out.println("Sending: name=" + session.getString("name") + ", bio=" + session.getString("bio"));
                    String name = session.getString("name");
                    String bio = session.getString("bio");
                    String jsonBody = """
                                        {
                                            "name": "%s",
                                            "bio": "%s"
                                        }
                                        """.formatted(name, bio);
                    return session.set("jsonBody", jsonBody);
                })
                .exec(http("create-author-request").post("/authors")
                        .header("Content-Type", "application/json")
                        .body(StringBody(session -> session.getString("jsonBody")))
                        .check(status().is(200)));
    }
}
