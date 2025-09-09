/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of BookApplication.
 *
 * BookApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BookApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BookApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.mapper;

import fr.eletutour.dao.entity.BookEntity;
import fr.eletutour.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toBook(BookEntity bookEntity);
    BookEntity toBookEntity(Book book);
    List<Book> toBooks(List<BookEntity> bookEntities);
}
