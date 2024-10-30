package fr.eletutour.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job inactivateUsersJob;

    public BatchScheduler(JobLauncher jobLauncher, Job inactivateUsersJob) {
        this.jobLauncher = jobLauncher;
        this.inactivateUsersJob = inactivateUsersJob;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())  // Paramètre unique pour chaque exécution
                .toJobParameters();
        jobLauncher.run(inactivateUsersJob, jobParameters);
    }
}