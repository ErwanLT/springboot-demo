package com.letutour.demo.aot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AotController {

    @GetMapping("/")
    public String home() {
        return "Hello, AOT!";
    }
}
