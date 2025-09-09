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
package fr.eletutour.elastic.repository;

import fr.eletutour.elastic.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {
    List<Book> findByTitleContainingOrContentContaining(String title, String content);

    List<Book> findByAuthor(String author);
}