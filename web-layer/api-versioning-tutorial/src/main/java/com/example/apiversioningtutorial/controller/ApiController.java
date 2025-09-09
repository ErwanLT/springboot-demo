/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of api-versioning-tutorial.
 *
 * api-versioning-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * api-versioning-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with api-versioning-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.apiversioningtutorial.controller;

import com.example.apiversioningtutorial.dto.UserV1;
import com.example.apiversioningtutorial.dto.UserV2;
import com.example.apiversioningtutorial.dto.UserV3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "API for user management with versioning examples")
public class ApiController {

    // --- Versioning via URL ---
    @Operation(summary = "Get User V1 (URL versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV1.class)) })
    })
    @GetMapping("/v1")
    public UserV1 getUserV1() {
        return new UserV1("John Doe");
    }

    @Operation(summary = "Get User V2 (URL versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV2.class)) })
    })
    @GetMapping("/v2")
    public UserV2 getUserV2() {
        return new UserV2("John", "Doe");
    }

    @Operation(summary = "Get User V3 (URL versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV3.class)) })
    })
    @GetMapping("/v3")
    public UserV3 getUserV3() {
        return new UserV3("John", "Doe", 30);
    }

    // --- Versioning via Query Param ---
    @Operation(summary = "Get User V1 (Query Param versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV1.class)) })
    })
    @GetMapping(params = "version=1")
    public UserV1 getUserQueryV1() {
        return new UserV1("John Doe");
    }

    @Operation(summary = "Get User V2 (Query Param versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV2.class)) })
    })
    @GetMapping(params = "version=2")
    public UserV2 getUserQueryV2() {
        return new UserV2("John", "Doe");
    }

    // --- Versioning via Header ---
    @Operation(summary = "Get User V1 (Header versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV1.class)) })
    })
    @GetMapping(path = "/header", headers = "X-API-VERSION=1")
    public UserV1 getUserHeaderV1() {
        return new UserV1("John Doe");
    }

    @Operation(summary = "Get User V2 (Header versioning)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserV2.class)) })
    })
    @GetMapping(path = "/header", headers = "X-API-VERSION=2")
    public UserV2 getUserHeaderV2() {
        return new UserV2("John", "Doe");
    }
}