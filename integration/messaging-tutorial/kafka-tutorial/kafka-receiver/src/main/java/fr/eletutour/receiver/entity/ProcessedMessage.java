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
