# Virtual Thread Tutorial

This tutorial demonstrates how to use virtual threads in a Spring Boot application to upload files to an SFTP server.

## Prerequisites

- Java 21
- Docker and Docker Compose
- Maven

## 1. Start the SFTP Server

Navigate to the `core-concept/concurrency-tutorial/virtualthread-tutorial` directory and run the following command to start the SFTP server:

```bash
docker-compose up -d
```

The SFTP server will be available on `localhost:2222` with the following credentials:
- **User**: `foo`
- **Password**: `pass`

## 2. Build and Run the Application

Navigate to the `core-concept/concurrency-tutorial/virtualthread-tutorial` directory and run the following command to build the application:

```bash
mvn clean install
```

Then, run the application:

```bash
java -jar target/virtualthread-tutorial-1.0-SNAPSHOT.jar
```

The application will start and be ready to accept file uploads.

## 3. Upload Files

To test the application, you can send a POST request to the `/upload` endpoint with a list of files.

You can use a tool like `curl` to send the request. For example:

```bash
curl -X POST -F 'files=@/path/to/your/file1.txt' -F 'files=@/path/to/your/file2.txt' http://localhost:8080/upload
```

The application will receive the files, process them in parallel using virtual threads, and upload them to the SFTP server in the `/home/foo/upload` directory.
