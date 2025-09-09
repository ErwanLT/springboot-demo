/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of elasticsearch-tutorial.
 *
 * elasticsearch-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * elasticsearch-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with elasticsearch-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class ElasticSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
