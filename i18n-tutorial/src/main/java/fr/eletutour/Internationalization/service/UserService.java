package fr.eletutour.Internationalization.service;

import fr.eletutour.Internationalization.entities.User;
import fr.eletutour.Internationalization.exception.ResourceNotFoundException;
import fr.eletutour.Internationalization.exception.UserNotFoundException;
import fr.eletutour.Internationalization.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user.not.found", id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        user.setUsername(userDetails.getUsername());
        user.setStatus(userDetails.getStatus());
        user.setLastLoginDate(userDetails.getLastLoginDate());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
