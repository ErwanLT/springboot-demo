package com.example.apiversioningtutorial.controller;

import com.example.apiversioningtutorial.dto.UserV1;
import com.example.apiversioningtutorial.dto.UserV2;
import com.example.apiversioningtutorial.dto.UserV3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/v1/user")
    public UserV1 getUserV1() {
        return new UserV1("John Doe");
    }

    @GetMapping("/api/v2/user")
    public UserV2 getUserV2() {
        return new UserV2("John", "Doe");
    }

    @GetMapping("/api/v3/user")
    public UserV3 getUserV3() {
        return new UserV3("John", "Doe", 30);
    }

    // Query Param Versioning

    @GetMapping(path = "/api/user", params = "version=1")
    public UserV1 getUserQueryV1() {
        return new UserV1("John Doe");
    }

    @GetMapping(path = "/api/user", params = "version=2")
    public UserV2 getUserQueryV2() {
        return new UserV2("John", "Doe");
    }

    // Header Versioning

    @GetMapping(path = "/api/user/header", headers = "X-API-VERSION=1")
    public UserV1 getUserHeaderV1() {
        return new UserV1("John Doe");
    }

    @GetMapping(path = "/api/user/header", headers = "X-API-VERSION=2")
    public UserV2 getUserHeaderV2() {
        return new UserV2("John", "Doe");
    }
}
