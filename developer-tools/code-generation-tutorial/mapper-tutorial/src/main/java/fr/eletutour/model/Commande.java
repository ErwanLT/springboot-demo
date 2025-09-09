/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of mapper-tutorial.
 *
 * mapper-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mapper-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mapper-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.model;

import java.util.Date;

public class Commande {
    private String id;
    private Date dateCreation;

    public Commande(String id, Date dateCreation) {
        this.id = id;
        this.dateCreation = dateCreation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}