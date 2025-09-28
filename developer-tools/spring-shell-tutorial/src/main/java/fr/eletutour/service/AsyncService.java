package fr.eletutour.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncService {

    @Async
    public Future<String> longRunningTask() {
        try {
            // Simulate a long task that takes 5 seconds
            TimeUnit.SECONDS.sleep(5);
            return new AsyncResult<>("Inventory check complete!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new AsyncResult<>("Inventory check failed due to interruption.");
        }
    }
}
