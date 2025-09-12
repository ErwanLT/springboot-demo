package fr.eletutour.actuator;

import fr.eletutour.service.BookService;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "libraryStat")
public class LibraryStat {

    private final BookService bookService;

    public LibraryStat(BookService bookService) {
        this.bookService = bookService;
    }

    @ReadOperation
    public Map<String, Object> libraryStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("booksTotal", bookService.getTotalBooks());
        stats.put("booksBorrowed", bookService.getBorrowedBooks());
        stats.put("activeMembers", 85);
        return stats;
    }

    @WriteOperation
    public String clearCache() {
        // logique de purge du cache
        return "Cache vidé avec succès";
    }
}
