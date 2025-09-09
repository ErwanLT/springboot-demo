/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of pokedex.
 *
 * pokedex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pokedex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pokedex.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.pokedex.service;



import fr.eletutour.pokedex.model.Type;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class TypeComparator implements Comparator<Type> {
    private final Collator collator = Collator.getInstance(Locale.FRENCH);

    @Override
    public int compare(Type type1, Type type2) {
        return collator.compare(type1.getName(), type2.getName());
    }
}
