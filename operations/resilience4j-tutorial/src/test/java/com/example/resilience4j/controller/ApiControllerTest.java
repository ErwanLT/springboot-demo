package com.example.resilience4j.controller;

import com.example.resilience4j.service.BackendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BackendService backendService;

    // Reset the state of the service before each test
    @BeforeEach
    void setUp() {
        backendService.getCbCounter().set(0);
        backendService.getRetryCounter().set(0);
    }

    @Test
    void testCircuitBreaker() throws Exception {
        // First call fails
        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fallback response")));

        // Second call fails
        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fallback response")));

        // Third call succeeds
        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Data from backend")));
    }

    @Test
    void testRetry() throws Exception {
        // The single call will internally retry and eventually succeed
        mockMvc.perform(get("/api/retry-data"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Data from retryable backend")));
    }

    @Test
    void testRateLimiter() throws Exception {
        // First 2 calls should succeed
        mockMvc.perform(get("/api/ratelimit-data")).andExpect(status().isOk());
        mockMvc.perform(get("/api/ratelimit-data")).andExpect(status().isOk());

        // Third call should be rate limited and return fallback
        mockMvc.perform(get("/api/ratelimit-data"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Rate limit fallback response")));
    }

    @Test
    void testBulkhead() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // First call will occupy the only available slot in the bulkhead
        Future<String> future1 = executor.submit(() -> mockMvc.perform(get("/api/bulkhead-data"))
                .andReturn().getResponse().getContentAsString());

        // Give the first call a moment to enter the bulkhead
        Thread.sleep(100);

        // Second call should be rejected immediately
        Future<String> future2 = executor.submit(() -> mockMvc.perform(get("/api/bulkhead-data"))
                .andReturn().getResponse().getContentAsString());

        String response1 = future1.get(5, TimeUnit.SECONDS);
        String response2 = future2.get(1, TimeUnit.SECONDS);

        org.assertj.core.api.Assertions.assertThat(response1).contains("Data from bulkhead backend");
        org.assertj.core.api.Assertions.assertThat(response2).contains("Bulkhead fallback response");

        executor.shutdown();
    }

    @Test
    void testTimeLimiter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/timelimit-data"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Time limiter fallback response")));
    }
}
