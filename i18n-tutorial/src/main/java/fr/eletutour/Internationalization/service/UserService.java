package fr.eletutour.Internationalization.service;

import fr.eletutour.Internationalization.dto.UserDto;
import fr.eletutour.Internationalization.entities.User;
import fr.eletutour.Internationalization.exception.UserNotFoundException;
import fr.eletutour.Internationalization.mapper.UserMapper;
import fr.eletutour.Internationalization.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers(Locale locale) {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toDto(user, locale))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id, Locale locale) {
        User user = findUserById(id);
        return userMapper.toDto(user, locale);
    }

    public UserDto createUser(User user, Locale locale) {
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser, locale);
    }

    public UserDto updateUser(Long id, User userDetails, Locale locale) {
        User user = findUserById(id);

        user.setUsername(userDetails.getUsername());
        user.setStatus(userDetails.getStatus());
        user.setLastLoginDate(userDetails.getLastLoginDate());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser, locale);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user.not.found", id));
    }
}
