package fr.eletutour.ia.ludo.dto;

import java.util.List;

public record RuleAnswerResponse(String question,
                                 String game,
                                 String answer,
                                 int retrievedDocuments,
                                 List<RuleSourceSnippet> sources) {
}
