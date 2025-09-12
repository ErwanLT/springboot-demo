package fr.eletutour.virtualthread.controller;

import fr.eletutour.virtualthread.service.SftpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FileUploadController {

    private final SftpService sftpService;

    public FileUploadController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @PostMapping("/upload")
    public void uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        sftpService.uploadFiles(files);
    }

    @PostMapping("/organize")
    public void organizeFiles() {
        sftpService.organizeFiles();
    }
}
