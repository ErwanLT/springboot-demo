package fr.eletutour.quartz.tutorial.configuration;

import fr.eletutour.quartz.tutorial.job.InactiveUserCleanupJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobSchedulerConfig {

    @Bean
    public JobDetail inactiveUserJobDetail() {
        return JobBuilder.newJob(InactiveUserCleanupJob.class)
                .withIdentity("inactiveUserJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger inactiveUserJobTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("inactiveUserTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 12))
                .build();
    }
}
