package fr.eletutour.ia.ludo.controller;

import fr.eletutour.ia.ludo.dto.RuleAnswerResponse;
import fr.eletutour.ia.ludo.dto.RuleQuestionRequest;
import fr.eletutour.ia.ludo.service.RuleRagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LudoRuleController.class)
class LudoRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleRagService ruleRagService;

    @Test
    void shouldAnswerBoardgameRuleQuestion() throws Exception {
        when(ruleRagService.ask(any(RuleQuestionRequest.class))).thenReturn(
                new RuleAnswerResponse(
                        "Peut-on poser un partisan ici ?",
                        "Carcassonne",
                        "Oui, si aucune zone reliée n'est déjà occupée.",
                        2,
                        List.of()
                )
        );

        mockMvc.perform(post("/api/ludo/rules/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "question": "Peut-on poser un partisan ici ?",
                                  "game": "Carcassonne"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.game").value("Carcassonne"))
                .andExpect(jsonPath("$.retrievedDocuments").value(2));
    }
}
