package fr.eletutour.dynamic.controller;

import fr.eletutour.dynamic.dto.Command;
import fr.eletutour.dynamic.dto.OnlineCommand;
import fr.eletutour.dynamic.dto.StoreCommand;
import fr.eletutour.dynamic.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new order, which can be an online order or a store order.",
            description = "The type of order is determined by the 'type' field in the request body. " +
                    "Use 'online' for online orders and 'store' for in-store orders.")
    @PostMapping
    public ResponseEntity<Long> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order details, which vary based on the order type.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {OnlineCommand.class, StoreCommand.class},
                                    discriminatorProperty = "type",
                                    discriminatorMapping = {
                                            @DiscriminatorMapping(value = "online", schema = OnlineCommand.class),
                                            @DiscriminatorMapping(value = "store", schema = StoreCommand.class)
                                    }
                            )
                    )
            )
            @RequestBody Command command) {
        Long id = service.createOrder(command);
        return ResponseEntity.ok(id);
    }
}
