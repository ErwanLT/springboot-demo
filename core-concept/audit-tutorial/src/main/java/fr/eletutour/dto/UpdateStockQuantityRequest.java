package fr.eletutour.dto;

import lombok.Data;

@Data
public class UpdateStockQuantityRequest {
    private int newQuantity;
}
