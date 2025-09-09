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
package fr.eletutour.virtualthread.service;

import org.apache.sshd.sftp.client.SftpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
public class SftpServiceImpl implements SftpService {

    private static final Logger log = LoggerFactory.getLogger(SftpServiceImpl.class);
    private final SftpGateway sftpGateway;
    private final AsyncTaskExecutor taskExecutor;
    private final SessionFactory<SftpClient.DirEntry> sftpSessionFactory;

    @Value("${sftp.remote.dir}")
    private String sftpRemoteDir;

    public SftpServiceImpl(SftpGateway sftpGateway, AsyncTaskExecutor taskExecutor, SessionFactory<SftpClient.DirEntry> sftpSessionFactory) {
        this.sftpGateway = sftpGateway;
        this.taskExecutor = taskExecutor;
        this.sftpSessionFactory = sftpSessionFactory;
    }

    @Override
    public void uploadFiles(List<MultipartFile> files) {
        Path tempDir;
        try {
            tempDir = Files.createTempDirectory("upload-");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        files.forEach(file -> taskExecutor.submit(() -> {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if (filename.isEmpty()) {
                log.warn("Skipping file with empty name");
                return;
            }
            try {
                Path tempFile = tempDir.resolve(filename);
                file.transferTo(tempFile);
                log.info("Uploading file {} to SFTP server", tempFile.toFile().getName());
                sftpGateway.upload(tempFile.toFile());
                Files.delete(tempFile);
                log.info("file {} transferred with success", filename);
            } catch (Exception e) {
                log.error("Error processing file {}", filename, e);
            }
        }));
    }

    @Override
    public void organizeFiles() {
        try (var session = sftpSessionFactory.getSession()) {
            var files = session.list(sftpRemoteDir);
            log.info("Found {} entries in {}", files.length, sftpRemoteDir);

            for (var file : files) {
                String filename = file.getFilename();
                if (file.getAttributes().isDirectory() || filename.equals(".") || filename.equals("..")) {
                    continue;
                }

                taskExecutor.submit(() -> {
                    try {
                        if (filename.length() < 14 || filename.charAt(8) != '_') {
                            log.warn("File '{}' does not match expected format yyyyMMdd_HHmm_name, skipping.", filename);
                            return;
                        }

                        String year = filename.substring(0, 4);
                        String month = filename.substring(4, 6);
                        String day = filename.substring(6, 8);

                        String targetDir = sftpRemoteDir + "/" + year + "/" + month + "/" + day;

                        // Ensure target directory exists, handling concurrency
                        String[] dirs = targetDir.replace(sftpRemoteDir + "/", "").split("/");
                        String currentPath = sftpRemoteDir;
                        for (String dir : dirs) {
                            currentPath = currentPath + "/" + dir;
                            try {
                                session.mkdir(currentPath);
                                log.info("Created directory: {}", currentPath);
                            } catch (IOException e) {
                                // It's possible another thread created the directory.
                                // We check if it exists now. If it does, we can ignore the exception.
                                if (!session.exists(currentPath)) {
                                    // If it still doesn't exist, the error was real.
                                    throw e;
                                }
                            }
                        }

                        String fromPath = sftpRemoteDir + "/" + filename;
                        String toPath = targetDir + "/" + filename;

                        log.info("Moving file from {} to {}", fromPath, toPath);
                        session.rename(fromPath, toPath);
                        log.info("Successfully moved file {}", filename);
                    } catch (Exception e) {
                        log.error("Error processing file {}: {}", filename, e.getMessage(), e);
                    }
                });
            }
        } catch (IOException e) {
            log.error("Failed to list or move files on SFTP server", e);
            throw new RuntimeException(e);
        }
    }
}
