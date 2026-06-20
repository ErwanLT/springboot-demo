package fr.eletutour.model;

import java.time.LocalDate;

public record Loan(Long id, Long bookId, Long userId, LocalDate loanDate) {
}
