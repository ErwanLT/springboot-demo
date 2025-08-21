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
