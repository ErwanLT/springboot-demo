/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of virtualthread-tutorial.
 *
 * virtualthread-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * virtualthread-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with virtualthread-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.virtualthread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;

@SpringBootApplication
public class VirtualthreadTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualthreadTutorialApplication.class, args);
    }

    @Bean
    @Primary
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

}
