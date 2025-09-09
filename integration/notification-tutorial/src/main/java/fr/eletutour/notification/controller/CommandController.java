/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of notification-tutorial.
 *
 * notification-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * notification-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with notification-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
