package fr.eletutour.outbox.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount
) {
}
