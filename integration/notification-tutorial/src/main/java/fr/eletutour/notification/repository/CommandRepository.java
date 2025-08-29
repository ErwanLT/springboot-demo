package fr.eletutour.notification.repository;

import fr.eletutour.notification.model.Command;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommandRepository {

    private final Map<Long, Command> commands = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public Command save(Command command) {
        if (command.getId() == null) {
            command.setId(counter.incrementAndGet());
        }
        commands.put(command.getId(), command);
        return command;
    }

    public Optional<Command> findById(Long id) {
        return Optional.ofNullable(commands.get(id));
    }

    public Collection<Command> findAll() {
        return commands.values();
    }
}
