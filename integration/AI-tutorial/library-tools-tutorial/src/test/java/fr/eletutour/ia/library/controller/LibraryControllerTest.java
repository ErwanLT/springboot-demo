package fr.eletutour.ia.library.controller;

import fr.eletutour.ia.library.service.LibraryAiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryAiService libraryAiService;

    @Test
    void shouldAskQuestionThroughAssistant() throws Exception {
        String question = "Quels sont les livres de Martin Fowler ?";
        when(libraryAiService.ask(question)).thenReturn("Refactoring et Patterns of Enterprise Application Architecture");

        mockMvc.perform(get("/api/library/ask").param("question", question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value(question))
                .andExpect(jsonPath("$.answer").value("Refactoring et Patterns of Enterprise Application Architecture"));
    }
}
