/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of quartz-tutorial.
 *
 * quartz-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * quartz-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with quartz-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                .withDescription("Suppression des users inactifs")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger inactiveUserJobTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("inactiveUserTrigger")
                .withDescription("trigger du job de suppression des users inactifs")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 0))
                .build();
    }
}
