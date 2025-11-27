# QR Code Tutorial

This module demonstrates how to generate and read QR codes within a Spring Boot application.

## Features

- Generate a QR code from a given text.
- Read the content of an uploaded QR code image.

## How to Run

1.  Run the `QrCodeTutorialApplication`.
2.  Use a tool like `curl` or Postman to interact with the API.

### Generate QR Code

```bash
curl -X POST http://localhost:8080/qrcode/generate?text=HelloWorld -o qrcode.png
```

### Read QR Code

```bash
curl -X POST -F "file=@/path/to/your/qrcode.png" http://localhost:8080/qrcode/read
```
