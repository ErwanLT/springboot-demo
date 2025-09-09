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
package fr.eletutour.pokedex.model;

public enum PokemonTypeColor {
    PLANTE("plante", "#3DA224"),
    POISON("poison", "#923FCC"),
    SPECTRE("spectre", "#703F70"),
    SOL("sol", "#92501B"),
    ROCHE("roche", "#B0AA82"),
    NORMAL("normal", "#A0A2A0"),
    ÉLECTRIK("électrik", "#FAC100"),
    FEU("feu", "#E72324"),
    VOL("vol", "#82BAEF"),
    EAU("eau", "#2481EF"),
    PSY("psy", "#EF3F7A"),
    COMBAT("combat", "#FF8100"),
    FÉE("fée", "#EF70EF"),
    INSECTE("insecte", "#92A212"),
    GLACE("glace", "#3DD9FF"),
    DRAGON("dragon", "#4F60E2"),
    ACIER("acier", "#60A2B9"),
    TÉNÈBRES("ténèbres", "#4F3F3D");

    private final String name;
    private final String color;

    PokemonTypeColor(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static String getColorForType(String typeName) {
        for (PokemonTypeColor type : values()) {
            if (type.name.equalsIgnoreCase(typeName)) {
                return type.color;
            }
        }
        return "#fff";
    }
} 