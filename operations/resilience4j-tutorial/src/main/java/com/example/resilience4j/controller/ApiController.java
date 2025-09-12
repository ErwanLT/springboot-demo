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
