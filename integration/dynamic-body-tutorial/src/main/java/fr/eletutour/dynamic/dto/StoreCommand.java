package fr.eletutour.dynamic.dto;

public record StoreCommand(
        String storeId,
        boolean express
) implements Command {
}
