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
