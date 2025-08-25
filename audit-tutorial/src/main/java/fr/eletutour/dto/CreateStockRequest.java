package fr.eletutour.dto;

import lombok.Data;

@Data
public class CreateStockRequest {
    private String productName;
    private int quantity;
}
