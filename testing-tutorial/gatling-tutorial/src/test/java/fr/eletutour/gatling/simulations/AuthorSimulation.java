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

@Slf4j
public class AuthorSimulation extends Simulation {

    private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = setupProtocolForSimulation();
    private static final Iterator<Map<String, Object>> FEED_DATA = setupTestFeedData();
    private static final ScenarioBuilder POST_SCENARIO_BUILDER = buildPostScenario();

    public AuthorSimulation() {
        setUp(POST_SCENARIO_BUILDER.injectOpen(postEndpointInjectionProfile())
                .protocols(HTTP_PROTOCOL_BUILDER)).assertions(global().responseTime()
                .max()
                .lte(10000), global().successfulRequests()
                .percent()
                .gt(90d));
    }

    private static HttpProtocolBuilder setupProtocolForSimulation() {
        return http.baseUrl("http://localhost:8080")
                .acceptHeader("application/json")
                .maxConnectionsPerHost(10)
                .userAgentHeader("Gatling/Performance Test");
    }

    private RampRateOpenInjectionStep postEndpointInjectionProfile() {
        int totalDesiredUserCount = 50; // Réduit à 50 utilisateurs au total
        double userRampUpPerInterval = 10; // 10 utilisateurs ajoutés par intervalle
        double rampUpIntervalSeconds = 20; // Sur 20 secondes
        int totalRampUptimeSeconds = 20; // Montée en charge sur 20s
        int steadyStateDurationSeconds = 60; // État stable pendant 60s
        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
                .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    }

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

    private static ScenarioBuilder buildPostScenario() {
        return CoreDsl.scenario("Load Test Creating Author")
                .feed(FEED_DATA)
                .exec(session -> {
                    System.out.println("Sending: name=" + session.getString("name") + ", bio=" + session.getString("bio"));
                    // Stocker les valeurs dans la session pour les utiliser dans formatted()
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
