/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of kafka-receiver.
 *
 * kafka-receiver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kafka-receiver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with kafka-receiver.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.receiver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessedMessage {

    @Id
    private Integer messageId;

    public ProcessedMessage() {
    }

    public ProcessedMessage(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
