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
