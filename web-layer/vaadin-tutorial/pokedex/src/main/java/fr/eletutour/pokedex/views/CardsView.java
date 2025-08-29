package fr.eletutour.pokedex.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.pokedex.model.Pokemon;
import fr.eletutour.pokedex.model.Stats;
import fr.eletutour.pokedex.model.PokemonTypeColor;
import fr.eletutour.pokedex.service.PokemonService;

import java.util.ArrayList;
import java.util.List;

@Route(value = "cards", layout = MainView.class)
@PageTitle("Pokemons by generation")
@JsModule("./js/card-tilt.js")
public class CardsView extends VerticalLayout {

    Select<String> select = new Select<>();
    TextField search = new TextField();
    PokemonService service;
    List<Pokemon> pokemons = new ArrayList<>();
    private final VerticalLayout cardLayout = new VerticalLayout();

    public CardsView(PokemonService pokemonService) {
        this.service = pokemonService;
        setSizeFull();
        configureSelect();
        add(getSelect(), cardLayout);
        updateListGen();
    }

    private void configureSelect() {
        select.setLabel("Génération");
        select.setItems(List.of("Kanto",
                "Johto",
                "Hoenn",
                "Sinnoh",
                "Unys",
                "Kalos",
                "Alola",
                "Galar",
                "Paldéa"));

        select.setValue("Kanto");
    }


    private HorizontalLayout getSelect() {
        select.addValueChangeListener(e -> updateListGen());

        var toolbar = new HorizontalLayout(select);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private String getTypeColor1(List<String> typeNames) {
        return PokemonTypeColor.getColorForType(typeNames.get(0));
    }

    private String getTypeColor2(List<String> typeNames) {
        if (typeNames.size() > 1) {
            return PokemonTypeColor.getColorForType(typeNames.get(1));
        }
        return getTypeColor1(typeNames);
    }

    private Div createShine(List<String> typeNames) {
        Div shine = new Div();
        shine.addClassName("shine");
        shine.getElement().setAttribute("data-type-color1", getTypeColor1(typeNames));
        shine.getElement().setAttribute("data-type-color2", getTypeColor2(typeNames));
        return shine;
    }

    private Div createElements(Pokemon pokemon) {
        Div elements = new Div();
        pokemon.getTypes().forEach(typeObj -> {
            Image image = new Image(typeObj.getImage(), typeObj.getName());
            image.setHeight("30px");
            elements.add(image);
        });
        return elements;
    }

    private Div createImageContainer(Pokemon pokemon) {
        Image sprite = new Image(pokemon.getSprites().getRegular(), pokemon.getName().getFr());
        sprite.setHeight("150px");
        Div imageContainer = new Div(sprite);
        imageContainer.getStyle()
                .set("display", "flex")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("height", "170px");
        return imageContainer;
    }

    private HorizontalLayout createStatsLayout(Stats stats) {
        VerticalLayout col1 = new VerticalLayout();
        col1.setPadding(false);
        col1.setSpacing(false);
        col1.add(
                new Div(new Span("PV : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getHp()))),
                new Div(new Span("Attaque : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getAtk()))),
                new Div(new Span("Défense : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getDef())))
        );
        VerticalLayout col2 = new VerticalLayout();
        col2.setPadding(false);
        col2.setSpacing(false);
        col2.add(
                new Div(new Span("Atq. Spé. : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getSpe_atk()))),
                new Div(new Span("Def. Spé. : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getSpe_def()))),
                new Div(new Span("Vitesse : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(stats.getVit())))
        );
        return new HorizontalLayout(col1, col2);
    }

    private Div[] createFooter(Pokemon pokemon) {
        return new Div[] {
                new Div(new Span("Taille : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(pokemon.getHeight()))),
                new Div(new Span("Poids : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(pokemon.getWeight())))
        };
    }

    private Card createPokemonCard(Pokemon pokemon) {
        Card pokemonCard = new Card();
        pokemonCard.addClassName("pokemon-card");
        List<String> typeNames = pokemon.getTypes().stream().map(t -> t.getName().toLowerCase()).toList();
        Div shine = createShine(typeNames);
        pokemonCard.getElement().appendChild(shine.getElement());
        pokemonCard.setTitle(pokemon.getName().getFr());
        pokemonCard.setSubtitle(new Div(pokemon.getCategory()));
        var numFormat = String.format("%04d", pokemon.getPokedexId());
        pokemonCard.setHeaderPrefix(new Div("N°" + numFormat));
        pokemonCard.setHeaderSuffix(createElements(pokemon));
        pokemonCard.setMedia(createImageContainer(pokemon));
        pokemonCard.add(createStatsLayout(pokemon.getStats()));
        pokemonCard.addToFooter(createFooter(pokemon));
        return pokemonCard;
    }

    private void updateListGen() {
        var gen = switch(select.getValue()) {
            case "Kanto" -> 1;
            case "Johto" -> 2;
            case "Hoenn" -> 3;
            case "Sinnoh" -> 4;
            case "Unys" -> 5;
            case "Kalos" -> 6;
            case "Alola" -> 7;
            case "Galar" -> 8;
            case "Paldéa" ->9;
            default -> throw new IllegalStateException("Unexpected value: " + select.getValue());
        };
        pokemons = service.getGenerations().get(gen);
        search.clear();

        cardLayout.removeAll();
        HorizontalLayout row = new HorizontalLayout();
        int count = 0;
        for (Pokemon pokemon : pokemons) {
            Card pokemonCard = createPokemonCard(pokemon);
            row.add(pokemonCard);
            count++;
            if (count % 3 == 0) {
                cardLayout.add(row);
                row = new HorizontalLayout();
            }
        }
        if (row.getComponentCount() > 0) {
            cardLayout.add(row);
        }
        UI.getCurrent().getPage().executeJs("window.initCardTiltEffect()");
    }

}
