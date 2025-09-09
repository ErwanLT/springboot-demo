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
