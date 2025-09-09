/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of virtualthread-tutorial.
 *
 * virtualthread-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * virtualthread-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with virtualthread-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
