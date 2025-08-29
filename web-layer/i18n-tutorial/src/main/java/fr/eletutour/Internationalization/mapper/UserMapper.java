package fr.eletutour.Internationalization.mapper;

import fr.eletutour.Internationalization.dto.UserDto;
import fr.eletutour.Internationalization.entities.User;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class UserMapper {

    private final MessageSource messageSource;

    public UserMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public UserDto toDto(User user, Locale locale) {
        if (user == null) {
            return null;
        }

        String pattern = messageSource.getMessage("date.format", null, "dd/MM/yyyy HH:mm:ss", locale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = user.getLastLoginDate() != null ? user.getLastLoginDate().format(formatter) : "";

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getStatus(),
                formattedDate
        );
    }
}
