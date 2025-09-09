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
package fr.eletutour.Internationalization.controller;

import fr.eletutour.Internationalization.dto.UserDto;
import fr.eletutour.Internationalization.entities.User;
import fr.eletutour.Internationalization.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(Locale locale) {
        return userService.getAllUsers(locale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, Locale locale) {
        return ResponseEntity.ok(userService.getUserById(id, locale));
    }

    @PostMapping
    public UserDto createUser(@RequestBody User user, Locale locale) {
        return userService.createUser(user, locale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User userDetails, Locale locale) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails, locale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
