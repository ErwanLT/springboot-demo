package fr.eletutour.notification.controller;

import fr.eletutour.notification.model.Command;
import fr.eletutour.notification.model.CommandStatus;
import fr.eletutour.notification.service.CommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/commands")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.createCommand(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommand);
    }

    @GetMapping
    public Collection<Command> getAllCommands() {
        return commandService.getAllCommands();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Command> updateStatusCommand(@PathVariable Long id, @RequestParam CommandStatus status) {
        return commandService.updateStatusCommand(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
