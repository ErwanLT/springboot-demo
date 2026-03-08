package fr.eletutour.ia.ludo.controller;

import fr.eletutour.ia.ludo.dto.RuleAnswerResponse;
import fr.eletutour.ia.ludo.dto.RuleQuestionRequest;
import fr.eletutour.ia.ludo.service.RuleRagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ludo/rules")
public class LudoRuleController {

    private final RuleRagService ruleRagService;

    public LudoRuleController(RuleRagService ruleRagService) {
        this.ruleRagService = ruleRagService;
    }

    @PostMapping("/ask")
    public RuleAnswerResponse ask(@RequestBody RuleQuestionRequest request) {
        return ruleRagService.ask(request);
    }
}
