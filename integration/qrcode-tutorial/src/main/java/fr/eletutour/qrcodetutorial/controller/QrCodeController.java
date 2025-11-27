package fr.eletutour.qrcodetutorial.controller;

import com.google.zxing.NotFoundException;
import fr.eletutour.qrcodetutorial.service.QrCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "QR Code", description = "QR Code management APIs")
@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Operation(
            summary = "Generate QR Code",
            description = "Generate a QR code from a given text",
            tags = { "QR Code" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code generated successfully",
                    content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE,
                            schema = @Schema(type = "string", format = "byte"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@Parameter(description = "Text to encode in the QR code", required = true) @RequestParam String text) {
        try {
            byte[] qrCode = qrCodeService.generateQrCode(text, 250, 250);
            return ResponseEntity.ok(qrCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Generate QR Code with custom color",
            description = "Generate a QR code from a given text with custom color",
            tags = { "QR Code" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code generated successfully",
                    content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE,
                            schema = @Schema(type = "string", format = "byte"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping(value = "/generate/custom", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCodeWithColor(@Parameter(description = "Text to encode in the QR code", required = true) @RequestParam String text,
                                                          @Parameter(description = "Color of the QR code in hex format (#000000)", required = false) @RequestParam(defaultValue = "#000000") String color,
                                                          @Parameter(description = "Background color of the QR code in hex format (#FFFFFF)", required = false) @RequestParam(defaultValue = "#FFFFFF") String backgroundColor) {
        try {
            byte[] qrCode = qrCodeService.generateQrCodeWithColor(text, 250, 250, color, backgroundColor);
            return ResponseEntity.ok(qrCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Read QR Code",
            description = "Read the content of a QR code from an image file",
            tags = { "QR Code" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code content read successfully",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Bad request, file is empty",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "QR Code not found in image",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/read")
    public ResponseEntity<String> readQrCode(@Parameter(description = "Image file containing the QR code", required = true) @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        try {
            String content = qrCodeService.readQrCode(file.getInputStream());
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file: " + e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body("QR Code not found in image");
        }
    }
}
