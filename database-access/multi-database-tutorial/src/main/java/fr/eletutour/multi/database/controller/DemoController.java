package fr.eletutour.multi.database.controller;

import fr.eletutour.multi.database.service.AppService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final AppService appService;

    public DemoController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/create-user-and-order")
    public String createUserAndOrder(@RequestBody CreateUserAndOrderRequest request) {
        return appService.createUserAndOrder(request);
    }
}
