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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.pokedex.model.EvolutionStep;
import fr.eletutour.pokedex.model.Forme;
import fr.eletutour.pokedex.model.MegaEvolution;
import fr.eletutour.pokedex.model.Pokemon;
import fr.eletutour.pokedex.model.Talent;
import fr.eletutour.pokedex.service.NavigationService;
import fr.eletutour.pokedex.service.PokemonService;
import org.springframework.util.CollectionUtils;


@Route(value = "pokemon-detail")
@PageTitle("Détails du Pokémon")
public class PokemonDetailView extends VerticalLayout {

    private final PokemonService pokemonService;
    private final NavigationService navigationService;

    private Pokemon pokemon;

    private final HorizontalLayout numAndName = new HorizontalLayout();
    private final HorizontalLayout sprites = new HorizontalLayout();
    private final HorizontalLayout types = new HorizontalLayout();
    private final HorizontalLayout categorie = new HorizontalLayout();
    private final HorizontalLayout talentsLayout = new HorizontalLayout();
    private final HorizontalLayout tailleLayout = new HorizontalLayout();
    private final HorizontalLayout poidsLayout = new HorizontalLayout();
    private final HorizontalLayout eggsLayout = new HorizontalLayout();
    private final VerticalLayout evolutionsLayout = new VerticalLayout();
    private final VerticalLayout gigamaxLayout = new VerticalLayout();

    public PokemonDetailView(NavigationService navigationService, PokemonService pokemonService) {
        this.navigationService = navigationService;
        this.pokemonService = pokemonService;

        pokemon = navigationService.getSelectedPokemon();

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        configureSprites();
        configureType();
        configureCategorie();
        configureTailleLayout();
        configurePoidsLayout();
        configureTalents();
        configureOeufs();
        configureEvolutions();
        configureGigamax();

        Button backButton = new Button("Retour liste", e -> getUI().ifPresent(ui -> ui.getPage().executeJs("window.history.back()")));
        add(configureNumAndName(), sprites, types, categorie, tailleLayout, poidsLayout, talentsLayout, eggsLayout, evolutionsLayout, gigamaxLayout, backButton);
    }

    private HorizontalLayout configureNumAndName() {
        return PokemonDetailUtils.numAndNameLayout(pokemon);
    }

    private void configureSprites() {

        TabSheet spritesTabSheet = new TabSheet();
        TabSheet normalForm = tabSprite(pokemon);
        spritesTabSheet.add("Forme classique", normalForm);
        if(!CollectionUtils.isEmpty(pokemon.getFormes())){
            TabSheet regionalForm = new TabSheet();
            for (Forme forme : pokemon.getFormes()){
                Pokemon p = pokemonService.findByName(forme.getName());
                regionalForm.add(forme.getRegion(), tabSprite(p));
            }
            spritesTabSheet.add("Forme régionale", regionalForm);
        }

        sprites.add(spritesTabSheet);
    }

    private TabSheet tabSprite(Pokemon p) {
        TabSheet normalForm = new TabSheet();
        Image regular = new Image();
        regular.setSrc(p.getSprites().getRegular());
        regular.setAlt(p.getName().getFr());
        regular.setAriaLabel("Artworf of " + p.getName().getFr());
        normalForm.add("Regular", regular);

        Image shiny = new Image();
        shiny.setSrc(p.getSprites().getShiny());
        shiny.setAlt(p.getName().getFr());
        shiny.setAriaLabel("Artworf of shiny " + p.getName().getFr());
        normalForm.add("Shiny", shiny);
        return normalForm;
    }

    private void configureType() {
        pokemon.getTypes().forEach(t -> types.add(new Image(t.getImage(), t.getName())));
    }

    private void configureCategorie() {
        VerticalLayout label = PokemonDetailUtils.createLabelWithClassName("Catégorie", pokemon.getTypes().getFirst().getName().toLowerCase());
        VerticalLayout cat = new VerticalLayout(new NativeLabel(pokemon.getCategory()));
        categorie.add(label, cat);
        categorie.setWidthFull();
    }

    private void configurePoidsLayout() {
        VerticalLayout label = PokemonDetailUtils.createLabelWithClassName("Poids", pokemon.getTypes().getFirst().getName().toLowerCase());
        VerticalLayout poids = new VerticalLayout(new NativeLabel(pokemon.getWeight()));
        poidsLayout.add(label, poids);
        poidsLayout.setWidthFull();
    }

    private void configureTailleLayout() {
        VerticalLayout label = PokemonDetailUtils.createLabelWithClassName("Taille", pokemon.getTypes().getFirst().getName().toLowerCase());
        VerticalLayout taille = new VerticalLayout(new NativeLabel(pokemon.getHeight()));
        tailleLayout.add(label, taille);
        tailleLayout.setWidthFull();
    }

    private void configureTalents() {
        VerticalLayout label = PokemonDetailUtils.createLabelWithClassName("Talents", pokemon.getTypes().getFirst().getName().toLowerCase());
        OrderedList list = new OrderedList();
        for (Talent t : pokemon.getTalents()) {
            list.add(new ListItem(t.getName()));
        }
        talentsLayout.add(label, list);
        talentsLayout.setWidthFull();
    }

    private void configureOeufs() {
        VerticalLayout label = PokemonDetailUtils.createLabelWithClassName("Oeufs", pokemon.getTypes().getFirst().getName().toLowerCase());
        UnorderedList list = new UnorderedList();
        if (!CollectionUtils.isEmpty(pokemon.getEggGroups())) {
            for (String s : pokemon.getEggGroups()) {
                list.add(new ListItem(s));
            }
        } else {
            list.add(new ListItem("NA"));
        }
        eggsLayout.add(label, list);
        eggsLayout.setWidthFull();
    }

    private void configureEvolutions() {
        if (pokemon.getEvolution() != null) {
            HorizontalLayout label = PokemonDetailUtils.createEvolutionLayout("Evolutions", pokemon.getTypes().getFirst().getName().toLowerCase());
            evolutionsLayout.add(label);

            HorizontalLayout stepLayout = new HorizontalLayout();
            stepLayout.setWidthFull();

            configurePreEvolutions(stepLayout);
            configureNextEvolutions(stepLayout);
            configureMegaEvolutions(stepLayout);

            evolutionsLayout.add(stepLayout);
        }
    }

    private void configurePreEvolutions(HorizontalLayout stepLayout) {
        if (!CollectionUtils.isEmpty(pokemon.getEvolution().getPre())) {
            VerticalLayout preEvolutionLayout = new VerticalLayout();
            HorizontalLayout preEvolutionLabel = PokemonDetailUtils.createEvolutionLayout("Pré évolution", pokemon.getTypes().getFirst().getName().toLowerCase());
            preEvolutionLayout.add(preEvolutionLabel);

            EvolutionStep step = CollectionUtils.lastElement(pokemon.getEvolution().getPre());
            Pokemon pre = pokemonService.findById(step.getPokedexId());

            var numFormat = String.format("%04d", pre.getPokedexId());
            VerticalLayout numero = new VerticalLayout(new H3("N°" + numFormat));
            VerticalLayout nom = new VerticalLayout(new H3(pre.getName().getFr()));

            switch (pre.getTypes().size()){
                case 1 :
                    numero.addClassName(pre.getTypes().getFirst().getName().toLowerCase());
                    numero.addClassNames("explain","type","entete");
                    break;
                case 2 :
                    numero.addClassName(pre.getTypes().get(1).getName().toLowerCase());
                    numero.addClassNames("explain","type","entete");
                    break;
            }
            nom.addClassName(pre.getTypes().getFirst().getName().toLowerCase());
            nom.addClassName("type");

            PokemonDetailUtils.addEvolutionStep(preEvolutionLayout, pre, "", pokemon.getTypes().getFirst().getName().toLowerCase());

            stepLayout.add(preEvolutionLayout);
        }
    }

    private void configureNextEvolutions(HorizontalLayout stepLayout) {
        if (!CollectionUtils.isEmpty(pokemon.getEvolution().getNext())) {
            VerticalLayout nextEvolutionLayout = new VerticalLayout();
            HorizontalLayout nextEvolutionLabel = PokemonDetailUtils.createEvolutionLayout("Prochaine évolution", pokemon.getTypes().getFirst().getName().toLowerCase());
            nextEvolutionLayout.add(nextEvolutionLabel);

            for (EvolutionStep step : pokemon.getEvolution().getNext()) {
                Pokemon next = pokemonService.findById(step.getPokedexId());
                PokemonDetailUtils.addEvolutionStep(nextEvolutionLayout, next, step.getCondition(), pokemon.getTypes().getFirst().getName().toLowerCase());
            }

            stepLayout.add(nextEvolutionLayout);
        }
    }

    private void configureMegaEvolutions(HorizontalLayout stepLayout) {
        if (!CollectionUtils.isEmpty(pokemon.getEvolution().getMega())) {
            VerticalLayout megaEvolutionLayout = new VerticalLayout();
            HorizontalLayout megaEvolutionLabel = PokemonDetailUtils.createEvolutionLayout("Méga évolution", pokemon.getTypes().getFirst().getName().toLowerCase());
            megaEvolutionLayout.add(megaEvolutionLabel);

            for (MegaEvolution mega : pokemon.getEvolution().getMega()) {
                PokemonDetailUtils.addEvolutionStep(megaEvolutionLayout, "", mega.getSprites().getRegular(), mega.getOrbe(), pokemon.getTypes().getFirst().getName().toLowerCase(), null);
            }

            stepLayout.add(megaEvolutionLayout);
        }
    }

    private void configureGigamax() {
        if(pokemon.getSprites().getGmax() != null){
            HorizontalLayout label = PokemonDetailUtils.createEvolutionLayout("Gigamax", pokemon.getTypes().getFirst().getName().toLowerCase());
            gigamaxLayout.add(label);

            HorizontalLayout spriteLayout = new HorizontalLayout();
            Image sprite = new Image(pokemon.getSprites().getGmax().getRegular(), pokemon.getName().getFr() + "gigamax");
            spriteLayout.add(sprite);
            spriteLayout.setWidthFull();
            spriteLayout.setAlignItems(Alignment.CENTER);
            spriteLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            gigamaxLayout.add(spriteLayout);
        }
    }
}