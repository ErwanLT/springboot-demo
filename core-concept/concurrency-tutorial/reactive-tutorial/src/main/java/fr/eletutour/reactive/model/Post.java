/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of reactive-tutorial.
 *
 * reactive-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * reactive-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with reactive-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.reactive.model;

public record Post(Integer userId, Integer id, String title, String body) {
}
