package fr.eletutour.dto;

import java.util.Date;

public class RevisionDto {
    private int revisionNumber;
    private Date revisionDate;
    private String revisionType;
    private String username;
    private StockDto stock;

    public RevisionDto(int revisionNumber, Date revisionDate, String revisionType, String username, StockDto stock) {
        this.revisionNumber = revisionNumber;
        this.revisionDate = revisionDate;
        this.revisionType = revisionType;
        this.username = username;
        this.stock = stock;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }
}
