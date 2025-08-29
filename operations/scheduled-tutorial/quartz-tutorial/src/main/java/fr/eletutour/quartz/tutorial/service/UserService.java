package fr.eletutour.quartz.tutorial.service;

import fr.eletutour.quartz.tutorial.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteInactiveUsers() {
        int deleted = userRepository.deleteByStatus("inactive");
        LOG.info("Suppression de {} utilisateurs inactifs.", deleted);
    }
}
