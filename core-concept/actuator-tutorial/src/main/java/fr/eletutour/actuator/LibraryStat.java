/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of actuator-tutorial.
 *
 * actuator-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * actuator-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with actuator-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
