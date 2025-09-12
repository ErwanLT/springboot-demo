package fr.eletutour.bucket.controller;

import fr.eletutour.bucket.aspect.RateLimited;
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

@RestController
@RequestMapping("/hello")
public class HelloController {

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
    @RateLimited
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello world!");
    }

    @Operation(
            summary = "Dire bonjour avec limitation par IP",
            description = """
            Cet endpoint retourne 'Hello avec limitation par IP !' si le quota n'est pas dépassé pour l'adresse IP du client.
            
            Limite : 10 requêtes par heure et par IP.
            
            - 200 : Succès, message de bienvenue.
            - 429 : Trop de requêtes, quota dépassé pour cette IP.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès : message de bienvenue",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "429", description = "Trop de requêtes : quota dépassé pour cette IP",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/by-ip")
    @RateLimited(strategy = "ip")
    public ResponseEntity<String> helloByIp() {
        return ResponseEntity.ok("Hello avec limitation par IP !");
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
    @RateLimited(strategy = "api-key")
    public ResponseEntity<String> sayHelloApiKey(@RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        return ResponseEntity.ok()
                .body("Hello world! (API key: " + apiKey + ")");

    }
}
