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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokemon {
    @JsonProperty("pokedex_id")
    private int pokedexId;
    @JsonProperty("generation")
    private int generation;
    private String category;
    private Name name;
    private Sprites sprites;
    private List<Type> types;
    private List<Talent> talents;
    private Stats stats;
    private List<Resistance> resistances;
    private Evolution evolution;
    private String height;
    private String weight;
    @JsonProperty("egg_groups")
    private List<String> eggGroups;
    @JsonProperty("sexe")
    private Sexe sexe;
    @JsonProperty("catch_rate")
    private int catchRate;
    @JsonProperty("level_100")
    private int level100;
    private List<Forme> formes;
}
