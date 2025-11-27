package fr.eletutour.dynamic.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OnlineCommand.class, name = "online"),
        @JsonSubTypes.Type(value = StoreCommand.class, name = "store")
})
public sealed interface Command
        permits OnlineCommand, StoreCommand {
}
