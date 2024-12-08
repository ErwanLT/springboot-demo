package fr.eletutour.service;

import fr.eletutour.dao.entities.Role;
import fr.eletutour.dao.entities.RoleEnum;
import fr.eletutour.dao.entities.User;
import fr.eletutour.dao.repository.RoleRepository;
import fr.eletutour.dao.repository.UserRepository;
import fr.eletutour.model.RegisterUserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void init(){
        RegisterUserDto userDto = new RegisterUserDto("super.admin@email.com","123456","Super Admin");

        Optional<Role> optionalRole = roleService.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.email());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User()
                .setFullName(userDto.fullName())
                .setEmail(userDto.email())
                .setPassword(passwordEncoder.encode(userDto.password()))
                .setRole(optionalRole.get());

        userRepository.save(user);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleService.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User()
                .setFullName(input.fullName())
                .setEmail(input.email())
                .setPassword(passwordEncoder.encode(input.password()))
                .setRole(optionalRole.get());

        return userRepository.save(user);
    }
}
