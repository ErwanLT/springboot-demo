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
package fr.eletutour.jms.model;

import java.io.Serializable;

// Serializable est nécessaire pour envoyer un objet en JMS
public class ChatMessage implements Serializable {
    private String sender;
    private String content;

    // Constructeurs
    public ChatMessage() {}
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    // Getters / Setters
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatMessage{sender='" + sender + "', content='" + content + "'}";
    }
}
