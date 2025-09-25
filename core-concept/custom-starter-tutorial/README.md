# Custom Starter Tutorial

This tutorial demonstrates how to create a custom Spring Boot starter.

## Modules

This tutorial consists of two modules:

- `greeting-starter`: This is the actual Spring Boot starter. It provides a `GreetingService` that can be configured through properties.
- `greeting-app`: This is a simple Spring Boot application that uses the `greeting-starter` to demonstrate its functionality.

## How to Run

1. Build the entire project from the root directory using `mvn clean install`.
2. Run the `greeting-app` application. You can do this by navigating to the `greeting-app` directory and running `mvn spring-boot:run`.
3. You should see the message "Hello from custom starter!" printed to the console.

## Configuration

You can change the greeting message by modifying the `application.properties` file in the `greeting-app` module:

```properties
greeting.message=Your custom message here
```
