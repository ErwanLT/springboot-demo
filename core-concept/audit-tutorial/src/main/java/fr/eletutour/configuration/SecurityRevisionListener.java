/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of audit-tutorial.
 *
 * audit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * audit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with audit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.configuration;

import fr.eletutour.entity.StockRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        StockRevisionEntity stockRevisionEntity = (StockRevisionEntity) revisionEntity;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            stockRevisionEntity.setUsername(((UserDetails) principal).getUsername());
        } else {
            stockRevisionEntity.setUsername(principal.toString());
        }
    }
}