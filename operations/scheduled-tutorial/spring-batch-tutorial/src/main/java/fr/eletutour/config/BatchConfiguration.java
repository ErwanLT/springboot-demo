package fr.eletutour.config;

import fr.eletutour.entities.User;
import fr.eletutour.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Configuration du traitement par lots Spring Batch.
 * Cette classe définit :
 * <ul>
 *     <li>La configuration du job de désactivation des utilisateurs inactifs</li>
 *     <li>Les étapes de traitement (steps)</li>
 *     <li>Les composants de lecture, traitement et écriture des données</li>
 * </ul>
 */
@Configuration
public class BatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    /**
     * Définit le job de désactivation des utilisateurs inactifs.
     * Ce job traite les utilisateurs qui ne se sont pas connectés depuis plus de 6 mois.
     *
     * @param jobRepository Le repository des jobs
     * @param step1 L'étape de traitement
     * @return Le job configuré
     */
    @Bean
    public Job inactivateUsersJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("inactivateUsersJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    /**
     * Définit l'étape de traitement du job.
     * Cette étape traite les utilisateurs par lots de 10.
     *
     * @param jobRepository Le repository des jobs
     * @param transactionManager Le gestionnaire de transactions
     * @param reader Le lecteur d'utilisateurs
     * @param processor Le processeur d'utilisateurs
     * @param writer L'écrivain d'utilisateurs
     * @return L'étape configurée
     */
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ItemReader<User> reader, ItemProcessor<User, User> processor,
                      ItemWriter<User> writer) {
        log.info("inactivateUsersJob - step1 ");
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /**
     * Définit le lecteur d'utilisateurs.
     * Lit les utilisateurs actifs depuis la base de données.
     *
     * @param userRepository Le repository des utilisateurs
     * @return Le lecteur configuré
     */
    @Bean
    public ItemReader<User> reader(UserRepository userRepository) {
        log.info("inactivateUsersJob - search users");
        return new ItemReader<User>() {
            List<User> activeUsers = userRepository.findByStatus("active");

            @Override
            public User read() {
                if (!activeUsers.isEmpty()) {
                    return activeUsers.removeFirst();
                }
                return null;
            }
        };
    }

    /**
     * Définit le processeur d'utilisateurs.
     * Vérifie si l'utilisateur n'a pas eu de connexion depuis 6 mois.
     *
     * @return Le processeur configuré
     */
    @Bean
    public ItemProcessor<User, User> processor() {
        return user -> {
            if (user.getLastLoginDate().isBefore(LocalDateTime.now().minusMonths(6))) {
                log.info("user {} is inactive because last login date is {}", user.getUsername(), user.getLastLoginDate());
                user.setStatus("inactive");
            }
            return user;
        };
    }

    /**
     * Définit l'écrivain d'utilisateurs.
     * Sauvegarde les utilisateurs modifiés dans la base de données.
     *
     * @param userRepository Le repository des utilisateurs
     * @return L'écrivain configuré
     */
    @Bean
    public ItemWriter<User> writer(UserRepository userRepository) {
        log.info("inactivateUsersJob - save users");
        return userRepository::saveAll;
    }
}