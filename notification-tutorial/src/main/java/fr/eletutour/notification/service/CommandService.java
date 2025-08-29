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
