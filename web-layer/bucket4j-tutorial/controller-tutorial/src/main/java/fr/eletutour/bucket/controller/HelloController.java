package fr.eletutour.bucket.controller;

import fr.eletutour.bucket.service.BucketService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.http.HttpStatus;

import java.time.Duration;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final Bucket bucket;

    private final BucketService bucketService;

    public HelloController(BucketService bucketService) {
        this.bucketService = bucketService;
        // refillGreedy : recharge le bucket d'un coup à intervalle régulier (ici, 20 tokens toutes les heures).
        Bandwidth limit = Bandwidth
                .builder()
                .capacity(20)
                .refillGreedy(20, Duration.ofHours(1))
                .build();
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Operation(
        summary = "Dire bonjour avec limitation de débit",
        description = """
        Cet endpoint retourne 'Hello world!' si le quota n'est pas dépassé.
        
        Limite : 20 requêtes par heure.
        
        - 200 : Succès, message de bienvenue.
        - 429 : Trop de requêtes, quota dépassé.
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succès : message de bienvenue",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "429", description = "Trop de requêtes : quota dépassé",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<String> sayHello() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("Hello world!");
        } else {
            return ResponseEntity.status(429).body("Trop de requêtes pour cette clé API, merci de réessayer plus tard.");
        }
    }

    @Operation(
        summary = "Dire bonjour avec API key et limitation dynamique",
        description = """
        Cet endpoint retourne 'Hello world!' si le quota n'est pas dépassé pour la clé API fournie dans le header 'X-API-KEY'.
        
        - Clé 'VIP-123' : 100 requêtes/heure
        - Autres clés : 10 requêtes/heure
        
        - 200 : Succès
        - 429 : Trop de requêtes
        - 400 : Clé manquante
        """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Succès : message de bienvenue",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "429", description = "Trop de requêtes : quota dépassé",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Clé API manquante",
            content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/apikey")
    public ResponseEntity<String> sayHelloApiKey(@RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return ResponseEntity.badRequest().body("Clé API manquante dans le header X-API-KEY");
        }
        Bucket bucket = bucketService.resolveBucket(apiKey);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return ResponseEntity.ok()
                .header("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                .body("Hello world! (API key: " + apiKey + ")");
        }
        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
            .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
            .body("Trop de requêtes pour cette clé API, merci de réessayer plus tard.");
    }
}
