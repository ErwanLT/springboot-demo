package fr.eletutour.notification.model;

import java.util.List;

public class Command {

    private Long id;
    private String customerName;
    private List<String> items;
    private CommandStatus status;

    public Command() {
    }

    public Command(Long id, String customerName, List<String> items, CommandStatus status) {
        this.id = id;
        this.customerName = customerName;
        this.items = items;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }
}
