/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of actuator-tutorial.
 *
 * actuator-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * actuator-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with actuator-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.model;

import java.util.Date;

public class Book {
    private String title;
    private String author;
    private Date publicationDate;
    private boolean borrowed;

    public Book(String title, String author, Date publicationDate) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.borrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
