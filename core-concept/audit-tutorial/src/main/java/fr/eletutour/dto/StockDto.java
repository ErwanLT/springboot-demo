package fr.eletutour.dto;

import lombok.Data;

@Data
public class StockDto {
    private Long id;
    private String productName;
    private int quantity;
}
