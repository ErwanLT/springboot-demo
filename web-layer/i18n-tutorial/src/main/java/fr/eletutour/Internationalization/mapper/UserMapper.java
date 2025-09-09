/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of i18n-tutorial.
 *
 * i18n-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * i18n-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with i18n-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
