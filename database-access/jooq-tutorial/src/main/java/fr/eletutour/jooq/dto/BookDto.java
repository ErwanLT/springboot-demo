package fr.eletutour.jooq.dto;

import java.math.BigDecimal;

public record BookDto(
        String title,
        String author,
        int year,
        BigDecimal price
) {
}
