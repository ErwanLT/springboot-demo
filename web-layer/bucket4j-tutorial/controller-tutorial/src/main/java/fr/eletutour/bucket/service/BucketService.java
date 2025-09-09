/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of controller-tutorial.
 *
 * controller-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * controller-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with controller-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
     * Récupère ou crée un bucket associé à la clé API fournie.
     * <p>
     * Si la clé est "VIP-123", le bucket autorise 100 requêtes/heure.
     * Pour toute autre clé, la limite est de 10 requêtes/heure.
     *
     * @param apiKey la clé API du client
     * @return le bucket correspondant à la clé API
     */
    public Bucket resolveBucket(String apiKey) {
        // Exemple : deux types de rate limit selon la clé
        if ("VIP-123".equals(apiKey)) {
            return buckets.computeIfAbsent(apiKey, k -> Bucket.builder()
                    .addLimit(Bandwidth.builder().capacity(100).refillGreedy(100, Duration.ofHours(1)).build())
                    .build());
        } else {
            return buckets.computeIfAbsent(apiKey, k -> Bucket.builder()
                    .addLimit(Bandwidth.builder().capacity(10).refillGreedy(10, Duration.ofHours(1)).build())
                    .build());
        }
    }
}