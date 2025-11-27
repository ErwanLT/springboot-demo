package fr.eletutour.qrcodetutorial.service;

import com.google.zxing.NotFoundException;
import fr.eletutour.qrcodetutorial.QrCodeTutorialApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = QrCodeTutorialApplication.class)
class QrCodeServiceTest {

    @Autowired
    private QrCodeService qrCodeService;

    @Test
    void shouldGenerateAndReadQrCode() throws Exception {
        // Given
        String expectedContent = "Hello, Spring Boot QR Code!";

        // When
        byte[] qrCodeBytes = qrCodeService.generateQrCode(expectedContent, 250, 250);
        assertNotNull(qrCodeBytes);

        // Then
        InputStream inputStream = new ByteArrayInputStream(qrCodeBytes);
        String actualContent = qrCodeService.readQrCode(inputStream);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void readQrCode_whenNoQrCodeInImage_thenThrowsNotFoundException() {
        // Given
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);

        // When / Then
        assertThrows(NotFoundException.class, () -> {
            qrCodeService.readQrCode(inputStream);
        });
    }
}
