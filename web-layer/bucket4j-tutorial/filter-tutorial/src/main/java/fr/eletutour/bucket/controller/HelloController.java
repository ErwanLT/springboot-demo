/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of filter-tutorial.
 *
 * filter-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * filter-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with filter-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.bucket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello world!");
    }
}
