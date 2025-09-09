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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.pokedex.model.Type;
import fr.eletutour.pokedex.service.PokemonService;

import java.util.Set;

@Route(value = "types", layout = MainView.class)
@PageTitle(value = "Types")
public class TypesListView extends VerticalLayout {

    Grid<Type> grid = new Grid<>(Type.class);
    PokemonService pokemonService;

    public TypesListView(PokemonService pokemonService){
        this.pokemonService = pokemonService;
        setSizeFull();
        configureGrid();
        add(getContent());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {

        Set<Type> types = pokemonService.findAllTypes();

        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name");

        grid.addComponentColumn(type ->
        {
            Image img = new Image(type.getImage(), type.getName());
            img.setHeight(40, Unit.PIXELS);
            img.setWidth(40, Unit.PIXELS);
            return img;
        }).setHeader("Sprite")
                .setFooter(String.format("%s types", types.size()));;

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.setItems(types);
    }
}
