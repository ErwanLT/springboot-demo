/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jms-tutorial.
 *
 * jms-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jms-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jms-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.jms.controller;

import fr.eletutour.jms.model.ChatMessage;
import fr.eletutour.jms.service.MessageProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducerService producerService;

    public MessageController(MessageProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@RequestBody ChatMessage message) {
        producerService.sendMessage(message);
        return "Message envoyé : " + message;
    }
}
