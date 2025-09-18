package fr.eletutour.querydsl.controller;

import fr.eletutour.querydsl.model.Book;
import fr.eletutour.querydsl.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void searchBooks_shouldReturnBooksByAuthor() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setYear(1954);
        book.setPublicationDate(LocalDate.of(1954, 7, 29));
        book.setPrice(22.50);

        when(bookService.searchBooks(any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(book), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/books/search?author=J.R.R. Tolkien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.content[0].author").value("J.R.R. Tolkien"));
    }

    @Test
    void searchBooks_whenServiceThrowsIllegalArgument_shouldReturnBadRequest() throws Exception {
        when(bookService.searchBooks(any(), any(), any(), any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenThrow(new IllegalArgumentException("Invalid sort property"));

        mockMvc.perform(get("/books/search?sort=invalid,asc"))
                .andExpect(status().isBadRequest());
    }
}