# gRPC Tutorial

This tutorial demonstrates how to create a simple gRPC service and client with Spring Boot.

## How to Run

1.  Navigate to the `integration/grpc-tutorial` directory.
2.  Run the application using the Maven wrapper:
    ```bash
    ../../mvnw spring-boot:run
    ```
3.  The gRPC server will start on port 9090.

## How to Test

You can test the service using the provided integration test `GreetingServiceImplTest`.

To test with a gRPC client like `grpcurl`:
```bash
# List services
grpcurl -plaintext localhost:9090 list

# Call the service
grpcurl -plaintext -d '{"name": "World"}' localhost:9090 grpc.GreetingService/sayHello
```

```