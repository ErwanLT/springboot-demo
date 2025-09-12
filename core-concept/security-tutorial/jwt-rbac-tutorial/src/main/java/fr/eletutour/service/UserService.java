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

/**
 * Service de gestion des utilisateurs.
 * Ce service gère :
 * <ul>
 *     <li>L'initialisation du super administrateur</li>
 *     <li>La création des administrateurs</li>
 *     <li>La récupération de la liste des utilisateurs</li>
 *     <li>La gestion des rôles utilisateurs</li>
 * </ul>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur du service de gestion des utilisateurs.
     *
     * @param userRepository Le repository de gestion des utilisateurs
     * @param roleRepository Le repository de gestion des rôles
     * @param roleService Le service de gestion des rôles
     * @param passwordEncoder L'encodeur de mots de passe
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Initialise le super administrateur au démarrage de l'application.
     * Cette méthode :
     * <ul>
     *     <li>Vérifie l'existence du rôle SUPER_ADMIN</li>
     *     <li>Vérifie si le super administrateur existe déjà</li>
     *     <li>Crée le super administrateur si nécessaire</li>
     * </ul>
     */
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

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return La liste des utilisateurs
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Crée un nouvel administrateur.
     * Cette méthode :
     * <ul>
     *     <li>Vérifie l'existence du rôle ADMIN</li>
     *     <li>Crée un nouvel utilisateur avec le rôle ADMIN</li>
     *     <li>Encode le mot de passe</li>
     *     <li>Sauvegarde l'administrateur</li>
     * </ul>
     *
     * @param input Les informations de l'administrateur à créer
     * @return L'administrateur créé ou null si le rôle ADMIN n'existe pas
     */
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
