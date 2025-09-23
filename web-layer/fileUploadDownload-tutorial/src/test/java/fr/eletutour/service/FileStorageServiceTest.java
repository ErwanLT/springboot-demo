package fr.eletutour.service;

import fr.eletutour.exception.FileStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        fileStorageService = new FileStorageService(tempDir);
    }

    @Test
    void storeFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        fileStorageService.storeFile(multipartFile);
        assertThat(Files.exists(tempDir.resolve("test.txt"))).isTrue();
    }

    @Test
    void storeFile_withInvalidPath() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "../test.txt", "text/plain", "Spring Framework".getBytes());
        assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(multipartFile));
    }

    @Test
    void loadFileAsResource() throws IOException {
        Files.write(tempDir.resolve("test.txt"), "Spring Framework".getBytes());
        Resource resource = fileStorageService.loadFileAsResource("test.txt");
        assertThat(resource.exists()).isTrue();
        assertThat(resource.isReadable()).isTrue();
    }

    @Test
    void loadFileAsResource_withNonExistentFile() {
        assertThrows(FileStorageException.class, () -> fileStorageService.loadFileAsResource("test.txt"));
    }

    @Test
    void deleteAll() throws IOException {
        // Given
        Path file = tempDir.resolve("test.txt");
        Files.write(file, "Spring Framework".getBytes());
        assertThat(Files.exists(file)).isTrue();

        // When
        fileStorageService.deleteAll();

        // Then
        assertThat(Files.exists(tempDir)).isFalse();
    }
}
