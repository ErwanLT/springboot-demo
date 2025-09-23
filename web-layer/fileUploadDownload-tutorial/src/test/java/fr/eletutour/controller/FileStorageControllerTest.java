package fr.eletutour.controller;

import fr.eletutour.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileStorageController.class)
class FileStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStorageService fileStorageService;

    @TempDir
    Path tempDir;

    @Test
    void uploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        doNothing().when(fileStorageService).storeFile(any());
        mockMvc.perform(multipart("/uploadFile").file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void uploadMultipleFiles() throws Exception {
        MockMultipartFile multipartFile1 = new MockMultipartFile("files", "test1.txt", "text/plain", "Spring Framework".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("files", "test2.txt", "text/plain", "Spring Framework".getBytes());
        doNothing().when(fileStorageService).storeFile(any());
        mockMvc.perform(multipart("/uploadMultipleFiles").file(multipartFile1).file(multipartFile2))
                .andExpect(status().isOk());
    }

    @Test
    void downloadFile() throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.write(file, "Spring Framework".getBytes());
        Resource resource = new UrlResource(file.toUri());
        given(fileStorageService.loadFileAsResource("test.txt")).willReturn(resource);

        mockMvc.perform(get("/downloadFile/test.txt"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.txt\""));
    }
}
