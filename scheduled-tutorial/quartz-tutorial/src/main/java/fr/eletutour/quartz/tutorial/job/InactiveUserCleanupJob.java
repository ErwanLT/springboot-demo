package fr.eletutour.quartz.tutorial.job;

import fr.eletutour.quartz.tutorial.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class InactiveUserCleanupJob implements Job {

    private final UserService userService;

    public InactiveUserCleanupJob(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        userService.deleteInactiveUsers();
    }
}
