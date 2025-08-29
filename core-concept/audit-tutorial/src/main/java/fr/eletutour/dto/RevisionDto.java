package fr.eletutour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RevisionDto {
    private int revisionNumber;
    private Date revisionDate;
    private String revisionType;
    private String username;
    private StockDto stock;
}
