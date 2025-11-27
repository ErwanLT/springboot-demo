package fr.eletutour.dynamic.dto;

public record OnlineCommand(
        String email,
        String deliveryAddress
) implements Command {
}
