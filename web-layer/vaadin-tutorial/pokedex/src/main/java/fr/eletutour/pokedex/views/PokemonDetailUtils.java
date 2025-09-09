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
package fr.eletutour.pokedex.views;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.eletutour.pokedex.model.Pokemon;

public class PokemonDetailUtils {

    public static VerticalLayout createLabelWithClassName(String text, String className) {
        VerticalLayout layout = new VerticalLayout(new H4(text));
        layout.addClassNames("type", "entete", className);
        return layout;
    }

    public static HorizontalLayout createEvolutionLayout(String evolutionType, String className) {
        HorizontalLayout layout = new HorizontalLayout(new H4(evolutionType));
        layout.addClassNames("type", className);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setWidthFull();
        return layout;
    }

    public static HorizontalLayout numAndNameLayout(Pokemon pokemon){
        HorizontalLayout numAndName = new HorizontalLayout();

        var numFormat = String.format("%04d", pokemon.getPokedexId());
        VerticalLayout numero = new VerticalLayout(new H3("N°" + numFormat));
        VerticalLayout nom = new VerticalLayout(new H3(pokemon.getName().getFr()));

        switch (pokemon.getTypes().size()){
            case 1 :
                numero.addClassName(pokemon.getTypes().getFirst().getName().toLowerCase());
                numero.addClassNames("explain","type","entete");
                break;
            case 2 :
                numero.addClassName(pokemon.getTypes().get(1).getName().toLowerCase());
                numero.addClassNames("explain","type","entete");
                break;
        }
        nom.addClassName(pokemon.getTypes().getFirst().getName().toLowerCase());
        nom.addClassName("type");

        numAndName.add(numero, nom);
        numAndName.setWidthFull();

        return numAndName;
    }

    public static void addEvolutionStep(VerticalLayout layout, Pokemon pokemon, String condition, String className) {
        addEvolutionStep(layout, "N°" + String.format("%04d", pokemon.getPokedexId()) + " " + pokemon.getName().getFr(), pokemon.getSprites().getRegular(), condition, className, pokemon);
    }


    public static void addEvolutionStep(VerticalLayout evolutionLayout, String name, String spriteUrl, String condition, String className, Pokemon pokemon) {
        VerticalLayout evoStep = new VerticalLayout(FlexComponent.JustifyContentMode.CENTER);
        HorizontalLayout nameLayout;
        if(pokemon == null) {
            nameLayout= new HorizontalLayout(new H4(name));
            nameLayout.setWidthFull();
            nameLayout.addClassNames("type", className);
            nameLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            nameLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        } else {
            nameLayout = numAndNameLayout(pokemon);
        }

        HorizontalLayout spriteLayout = new HorizontalLayout();
        Image sprite = new Image(spriteUrl, name);
        spriteLayout.add(sprite);
        spriteLayout.setWidthFull();
        spriteLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        spriteLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout conditionLayout = new HorizontalLayout(new Span(condition));
        conditionLayout.setWidthFull();
        conditionLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        conditionLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        evoStep.add(nameLayout, spriteLayout, conditionLayout);
        evolutionLayout.add(evoStep);
    }
}
