package fr.eletutour.virtualthread.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SftpService {
    void uploadFiles(List<MultipartFile> files);

    void organizeFiles();
}
