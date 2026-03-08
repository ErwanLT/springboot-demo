package fr.eletutour.ia.library.controller;

import fr.eletutour.ia.library.dto.AskRequest;
import fr.eletutour.ia.library.dto.AskResponse;
import fr.eletutour.ia.library.service.LibraryAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryAiService libraryAiService;

    public LibraryController(LibraryAiService libraryAiService) {
        this.libraryAiService = libraryAiService;
    }

    @GetMapping("/ask")
    public AskResponse ask(@RequestParam String question) {
        return new AskResponse(question, libraryAiService.ask(question));
    }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {
        return new AskResponse(request.question(), libraryAiService.ask(request.question()));
    }
}
