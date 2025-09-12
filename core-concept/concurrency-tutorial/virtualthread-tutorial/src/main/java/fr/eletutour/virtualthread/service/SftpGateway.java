package fr.eletutour.virtualthread.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;

@MessagingGateway
public interface SftpGateway {

    @Gateway(requestChannel = "toSftpChannel")
    void upload(File file);

}
