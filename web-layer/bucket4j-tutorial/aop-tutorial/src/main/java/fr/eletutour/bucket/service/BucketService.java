package fr.eletutour.bucket.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Service de gestion des buckets pour le rate limiting avec Bucket4j.
 * <p>
 * Ce service permet d'attribuer dynamiquement un bucket à chaque clé API.
 * La politique de limitation dépend du type de clé :
 * <ul>
 *     <li>Clé "VIP-123" : 100 requêtes par heure</li>
 *     <li>Autres clés : 10 requêtes par heure</li>
 * </ul>
 * Les buckets sont conservés en mémoire dans une map.
 */
@Service
public class BucketService {

    private final Map<String, Bucket> buckets = new HashMap<>();

    /**
     * Résout un bucket pour une clé donnée, quelle que soit son origine (IP, clé API, etc.).
     */
    public Bucket resolveBucket(String key) {
        // Exemple : si la clé est "VIP-123", quota plus généreux
        if ("VIP-123".equals(key)) {
            return buckets.computeIfAbsent(key, k ->
                    Bucket.builder()
                            .addLimit(Bandwidth.builder()
                                    .capacity(100)
                                    .refillGreedy(100, Duration.ofHours(1))
                                    .build())
                            .build());
        } else {
            return buckets.computeIfAbsent(key, k ->
                    Bucket.builder()
                            .addLimit(Bandwidth.builder()
                                    .capacity(10)
                                    .refillGreedy(10, Duration.ofHours(1))
                                    .build())
                            .build());
        }
    }
}