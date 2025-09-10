/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of resilience4j-tutorial.
 *
 * resilience4j-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * resilience4j-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with resilience4j-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.resilience4j.controller;

import com.example.resilience4j.service.BackendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final BackendService backendService;

    public ApiController(BackendService backendService) {
        this.backendService = backendService;
    }

    @GetMapping("/data")
    public String getData() {
        return backendService.fetchData();
    }

    @GetMapping("/retry-data")
    public String getRetryableData() {
        return backendService.retryableFetchData();
    }

    @GetMapping("/ratelimit-data")
    public String getRateLimitedData() {
        return backendService.rateLimitedFetchData();
    }

    @GetMapping("/bulkhead-data")
    public String getBulkheadData() throws InterruptedException {
        return backendService.bulkheadFetchData();
    }

    @GetMapping("/timelimit-data")
    public CompletableFuture<String> getTimeLimitedData() {
        return backendService.timeLimitedFetchData()
            .exceptionally(ex -> "Time limiter fallback response: " + ex.getMessage());
    }
}
