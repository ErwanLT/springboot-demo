/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of spring-batch-tutorial.
 *
 * spring-batch-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * spring-batch-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with spring-batch-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
