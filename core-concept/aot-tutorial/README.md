# AOT Compilation Tutorial

This module demonstrates how to use Spring Boot's Ahead-of-Time (AOT) compilation feature to create a native executable.

## How to run

### Without AOT

To run the application without AOT, you can use the following command:

```bash
./mvnw spring-boot:run
```

You will see the startup time in the logs.

### With AOT

To build a native image, run the following command:

```bash
./mvnw clean package -Pnative
```

This will create a native executable in the `target` directory.

To run the native executable, use the following command:

```bash
./target/aot-tutorial
```

You will see the startup time in the logs. You can compare the startup time with and without AOT to see the difference.

## How to test the application

Once the application is running, you can access the following URL:

```
http://localhost:8080/
```

You should see the following message: `Hello, AOT!`