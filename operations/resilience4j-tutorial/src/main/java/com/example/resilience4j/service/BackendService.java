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
package com.example.resilience4j.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackendService.class);
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicInteger retryCounter = new AtomicInteger(0);

    public AtomicInteger getCbCounter() {
        return counter;
    }

    public AtomicInteger getRetryCounter() {
        return retryCounter;
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    public String fetchData() {
        LOGGER.info("Attempting to fetch data...");
        // Simulate a failing service
        if (counter.incrementAndGet() % 3 != 0) {
            LOGGER.warn("Backend service is unavailable, throwing exception.");
            throw new RuntimeException("Backend service is unavailable");
        }
        LOGGER.info("Successfully fetched data from backend.");
        return "Data from backend";
    }

    public String fallback(RuntimeException t) {
        LOGGER.error("Fallback response due to: {}", t.getMessage());
        return "Fallback response: " + t.getMessage();
    }

    @Retry(name = "backendB", fallbackMethod = "retryFallback")
    public String retryableFetchData() {
        LOGGER.info("Attempting to fetch data with retry... (attempt {})", retryCounter.get() + 1);
        // Simulate a service that fails twice then succeeds
        if (retryCounter.incrementAndGet() < 3) {
            LOGGER.warn("Retryable service is unavailable, throwing exception.");
            throw new RuntimeException("Retryable service is unavailable");
        }
        LOGGER.info("Successfully fetched data with retry.");
        retryCounter.set(0); // Reset for next independent call
        return "Data from retryable backend";
    }

    public String retryFallback(RuntimeException t) {
        LOGGER.error("Retry fallback response after all retries failed, due to: {}", t.getMessage());
        retryCounter.set(0); // Reset for next independent call
        return "Retry fallback response: " + t.getMessage();
    }

    @RateLimiter(name = "backendC", fallbackMethod = "rateLimiterFallback")
    public String rateLimitedFetchData() {
        LOGGER.info("Attempting to fetch data with rate limiter...");
        return "Data from rate-limited backend";
    }

    public String rateLimiterFallback(io.github.resilience4j.ratelimiter.RequestNotPermitted t) {
        LOGGER.error("Rate limit exceeded, fallback response due to: {}", t.getMessage());
        return "Rate limit fallback response: " + t.getMessage();
    }

    @Bulkhead(name = "backendD", fallbackMethod = "bulkheadFallback")
    public String bulkheadFetchData() throws InterruptedException {
        LOGGER.info("Attempting to fetch data with bulkhead...");
        Thread.sleep(3000); // Simulate a slow call
        LOGGER.info("Successfully fetched data with bulkhead.");
        return "Data from bulkhead backend";
    }

    public String bulkheadFallback(io.github.resilience4j.bulkhead.BulkheadFullException t) {
        LOGGER.error("Bulkhead is full, fallback response due to: {}", t.getMessage());
        return "Bulkhead fallback response: " + t.getMessage();
    }

    @TimeLimiter(name = "backendE")
    public CompletableFuture<String> timeLimitedFetchData() {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("Attempting to fetch data with time limiter...");
            try {
                Thread.sleep(3000); // Simulate a very slow call
            } catch (InterruptedException e) {
                // The future is cancelled, this exception is expected
                LOGGER.warn("Slow call was interrupted.");
                throw new RuntimeException(e);
            }
            LOGGER.info("This will not be logged due to the timeout.");
            return "Data from time-limited backend";
        });
    }
}
