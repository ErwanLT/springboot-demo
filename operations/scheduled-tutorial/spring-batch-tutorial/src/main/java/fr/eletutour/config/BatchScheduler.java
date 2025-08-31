package fr.eletutour.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Planificateur des jobs Spring Batch.
 * Cette classe gère :
 * <ul>
 *     <li>L'exécution planifiée des jobs</li>
 *     <li>La configuration des paramètres des jobs</li>
 *     <li>Le lancement des jobs via le JobLauncher</li>
 * </ul>
 */
@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job inactivateUsersJob;

    /**
     * Constructeur du planificateur.
     *
     * @param jobLauncher Le lanceur de jobs
     * @param inactivateUsersJob Le job de désactivation des utilisateurs
     */
    public BatchScheduler(JobLauncher jobLauncher, Job inactivateUsersJob) {
        this.jobLauncher = jobLauncher;
        this.inactivateUsersJob = inactivateUsersJob;
    }

    /**
     * Exécute le job de désactivation des utilisateurs toutes les 10 secondes.
     * Ajoute un paramètre temporel unique pour chaque exécution.
     *
     * @throws Exception Si une erreur survient lors de l'exécution du job
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())  // Paramètre unique pour chaque exécution
                .toJobParameters();
        jobLauncher.run(inactivateUsersJob, jobParameters);
    }
}
