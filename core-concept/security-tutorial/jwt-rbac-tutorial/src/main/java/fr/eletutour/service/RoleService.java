package fr.eletutour.service;

import fr.eletutour.dao.entities.Role;
import fr.eletutour.dao.entities.RoleEnum;
import fr.eletutour.dao.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Service de gestion des rôles utilisateurs.
 * Ce service gère :
 * <ul>
 *     <li>L'initialisation des rôles par défaut</li>
 *     <li>La recherche des rôles par nom</li>
 *     <li>La création automatique des rôles manquants</li>
 * </ul>
 */
@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    /**
     * Constructeur du service de gestion des rôles.
     *
     * @param roleRepository Le repository de gestion des rôles
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Initialise les rôles par défaut au démarrage de l'application.
     * Cette méthode :
     * <ul>
     *     <li>Définit les rôles par défaut (USER, ADMIN, SUPER_ADMIN)</li>
     *     <li>Vérifie l'existence de chaque rôle</li>
     *     <li>Crée les rôles manquants</li>
     *     <li>Journalise les opérations</li>
     * </ul>
     */
    @PostConstruct
    void init() {
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        roleDescriptionMap.forEach((roleName, description) ->
                roleRepository.findByName(roleName).ifPresentOrElse(
                        role -> logger.info("Role already exists: {}", role),
                        () -> {
                            Role roleToCreate = new Role()
                                    .setName(roleName)
                                    .setDescription(description);
                            roleRepository.save(roleToCreate);
                            logger.info("Created new role: {}", roleToCreate);
                        }
                )
        );
    }

    /**
     * Recherche un rôle par son nom.
     *
     * @param name Le nom du rôle à rechercher
     * @return Un Optional contenant le rôle trouvé ou vide
     */
    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
