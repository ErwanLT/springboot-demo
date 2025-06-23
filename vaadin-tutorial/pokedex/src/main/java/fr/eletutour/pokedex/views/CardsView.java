package fr.eletutour.pokedex.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.pokedex.model.Pokemon;
import fr.eletutour.pokedex.model.Stats;
import fr.eletutour.pokedex.service.PokemonService;

import java.util.ArrayList;
import java.util.List;

@Route(value = "cards", layout = MainView.class)
@PageTitle("Pokemons by generation")
public class CardsView extends VerticalLayout {

    Select<String> select = new Select<>();
    TextField search = new TextField();
    PokemonService service;
    List<Pokemon> pokemons = new ArrayList<>();
    private VerticalLayout cardLayout = new VerticalLayout();

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
            Card pokemonCard = new Card();
            pokemonCard.addClassName("pokemon-card");

            pokemonCard.setTitle(pokemon.getName().getFr());
            pokemonCard.setSubtitle(new Div(pokemon.getCategory()));

            var numFormat = String.format("%04d", pokemon.getPokedexId());
            pokemonCard.setHeaderPrefix(new Div("N°" + numFormat));

            Div elements = new Div();
            pokemon.getTypes().forEach(type -> {
                Image image = new Image(type.getImage(), type.getName());
                image.setHeight("30px");
                elements.add(image);
            });
            pokemonCard.setHeaderSuffix(elements);

            Image sprite = new Image(pokemon.getSprites().getRegular(), pokemon.getName().getFr());
            sprite.setHeight("150px");

            Div imageContainer = new Div(sprite);
            imageContainer.getStyle()
                    .set("display", "flex")
                    .set("justify-content", "center")
                    .set("align-items", "center")
                    .set("height", "170px");

            pokemonCard.setMedia(imageContainer);

            Stats stats = pokemon.getStats();

            VerticalLayout col1 = new VerticalLayout();
            col1.setPadding(false);
            col1.setSpacing(false);
            col1.add(
                    new Div(new Text("PV : " + stats.getHp())),
                    new Div(new Text("Attaque : " + stats.getAtk())),
                    new Div(new Text("Défense : " + stats.getDef()))
            );

            VerticalLayout col2 = new VerticalLayout();
            col2.setPadding(false);
            col2.setSpacing(false);
            col2.add(
                    new Div(new Text("Atq. Spé. : " + stats.getSpe_atk())),
                    new Div(new Text("Def. Spé. : " + stats.getSpe_def())),
                    new Div(new Text("Vitesse : " + stats.getVit()))
            );

            HorizontalLayout statsLayout = new HorizontalLayout(col1, col2);
            pokemonCard.add(statsLayout);

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

        // Injection du JS via text block
        String tiltScript = """
            setTimeout(function() {
                document.querySelectorAll('.pokemon-card').forEach(function(card) {
                    card.addEventListener('mousemove', function(e) {
                        var rect = card.getBoundingClientRect();
                        var x = e.clientX - rect.left;
                        var y = e.clientY - rect.top;
                        var centerX = rect.width / 2;
                        var centerY = rect.height / 2;
                        var rotateX = ((y - centerY) / centerY) * -20;
                        var rotateY = ((x - centerX) / centerX) * 20;
                        card.style.transform = 'scale(1.05) rotateX(' + rotateX + 'deg) rotateY(' + rotateY + 'deg)';
                    });
                    card.addEventListener('mouseleave', function() {
                        card.style.transform = '';
                    });
                    card.addEventListener('mouseenter', function() {
                        card.style.transition = 'transform 0.15s cubic-bezier(.25,.8,.25,1)';
                    });
                });
            }, 100);
        """;
        com.vaadin.flow.component.UI.getCurrent().getPage().executeJs(tiltScript);
    }

}
