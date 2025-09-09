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
package fr.eletutour.notification.service;

import fr.eletutour.notification.model.Command;
import fr.eletutour.notification.model.CommandStatus;
import fr.eletutour.notification.repository.CommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CommandService {

    private static final Logger log = LoggerFactory.getLogger(CommandService.class);

    private final CommandRepository commandRepository;
    private final NotificationService notificationService;

    public CommandService(CommandRepository commandRepository, NotificationService notificationService) {
        this.commandRepository = commandRepository;
        this.notificationService = notificationService;
    }

    public Command createCommand(Command command) {
        log.info("Creating new command for customer: {}", command.getCustomerName());
        command.setStatus(CommandStatus.PENDING);
        Command savedCommand = commandRepository.save(command);
        notificationService.sendNotification(savedCommand);
        log.info("Command {} created with status PENDING", savedCommand.getId());
        return savedCommand;
    }

    public Collection<Command> getAllCommands() {
        log.info("Fetching all commands");
        return commandRepository.findAll();
    }

    public Optional<Command> updateStatusCommand(Long id, CommandStatus status) {
        log.info("Updating command {} to status {}", id, status);
        Optional<Command> commandOpt = commandRepository.findById(id);

        if (commandOpt.isEmpty()) {
            log.warn("Command with id {} not found", id);
            return Optional.empty();
        }

        Command command = commandOpt.get();
        command.setStatus(status);
        Command updatedCommand = commandRepository.save(command);
        notificationService.sendNotification(updatedCommand);
        log.info("Successfully updated command {} to status {}", id, status);

        return Optional.of(updatedCommand);
    }
}
