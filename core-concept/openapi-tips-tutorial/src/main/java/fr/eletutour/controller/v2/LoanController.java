package fr.eletutour.controller.v2;

import fr.eletutour.model.Loan;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Emprunts", description = "Tout ce qui touche les emprunts de livres")
@RestController
@RequestMapping("/api/v2/loans")
public class LoanController {

    @PostMapping
    public Loan create(Long bookId, Long userId) {
        return new Loan(1L, bookId, userId, LocalDate.now());
    }

    @GetMapping("/{userId}")
    public List<Loan> findByUser(@PathVariable Long userId) {
        return List.of(new Loan(1L, 1L, userId, LocalDate.now().minusDays(3)));
    }
}
