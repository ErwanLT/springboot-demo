package fr.eletutour.dto;

public class UpdateStockQuantityRequest {
    private int newQuantity;

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}
