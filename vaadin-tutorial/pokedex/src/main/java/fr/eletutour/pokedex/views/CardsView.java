package fr.eletutour.pokedex.views;

import com.vaadin.flow.component.card.Card;
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

            // Ajout du reflet
            Div shine = new Div();
            shine.addClassName("shine");
            // Couleurs des types
            List<String> typeNames = pokemon.getTypes().stream().map(t -> t.getName().toLowerCase()).toList();
            String color1 = switch (typeNames.get(0)) {
                case "plante" -> "#3DA224";
                case "poison" -> "#923FCC";
                case "spectre" -> "#703F70";
                case "sol" -> "#92501B";
                case "roche" -> "#B0AA82";
                case "normal" -> "#A0A2A0";
                case "électrik" -> "#FAC100";
                case "feu" -> "#E72324";
                case "vol" -> "#82BAEF";
                case "eau" -> "#2481EF";
                case "psy" -> "#EF3F7A";
                case "combat" -> "#FF8100";
                case "fée" -> "#EF70EF";
                case "insecte" -> "#92A212";
                case "glace" -> "#3DD9FF";
                case "dragon" -> "#4F60E2";
                case "acier" -> "#60A2B9";
                case "ténèbres" -> "#4F3F3D";
                default -> "#fff";
            };
            String color2 = color1;
            if (typeNames.size() > 1) {
                color2 = switch (typeNames.get(1)) {
                    case "plante" -> "#3DA224";
                    case "poison" -> "#923FCC";
                    case "spectre" -> "#703F70";
                    case "sol" -> "#92501B";
                    case "roche" -> "#B0AA82";
                    case "normal" -> "#A0A2A0";
                    case "électrik" -> "#FAC100";
                    case "feu" -> "#E72324";
                    case "vol" -> "#82BAEF";
                    case "eau" -> "#2481EF";
                    case "psy" -> "#EF3F7A";
                    case "combat" -> "#FF8100";
                    case "fée" -> "#EF70EF";
                    case "insecte" -> "#92A212";
                    case "glace" -> "#3DD9FF";
                    case "dragon" -> "#4F60E2";
                    case "acier" -> "#60A2B9";
                    case "ténèbres" -> "#4F3F3D";
                    default -> "#fff";
                };
            }
            shine.getElement().setAttribute("data-type-color1", color1);
            shine.getElement().setAttribute("data-type-color2", color2);
            pokemonCard.getElement().appendChild(shine.getElement());

            pokemonCard.setTitle(pokemon.getName().getFr());
            pokemonCard.setSubtitle(new Div(pokemon.getCategory()));

            var numFormat = String.format("%04d", pokemon.getPokedexId());
            pokemonCard.setHeaderPrefix(new Div("N°" + numFormat));

            Div elements = new Div();
            pokemon.getTypes().forEach(typeObj -> {
                Image image = new Image(typeObj.getImage(), typeObj.getName());
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

            HorizontalLayout statsLayout = new HorizontalLayout(col1, col2);
            pokemonCard.add(statsLayout);

            pokemonCard.addToFooter(
                    new Div(new Span("Taille : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(pokemon.getHeight()))),
                    new Div(new Span("Poids : ") {{ getStyle().set("font-weight", "bold"); }}, new Span(String.valueOf(pokemon.getWeight())))
            );

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
        function hexToRgb(hex) {
            hex = hex.replace('#', '');
            if (hex.length === 3) {
                hex = hex.split('').map(x => x + x).join('');
            }
            var bigint = parseInt(hex, 16);
            var r = (bigint >> 16) & 255;
            var g = (bigint >> 8) & 255;
            var b = bigint & 255;
            return r + ',' + g + ',' + b;
        }
        document.querySelectorAll('.pokemon-card').forEach(function(card) {
            var shine = card.querySelector('.shine');
            var colorHex1 = shine ? shine.getAttribute('data-type-color1') : '#fff';
            var colorHex2 = shine ? shine.getAttribute('data-type-color2') : '#fff';
            var color1 = hexToRgb(colorHex1);
            var color2 = hexToRgb(colorHex2);
            card.addEventListener('mousemove', function(e) {
                var rect = card.getBoundingClientRect();
                var x = e.clientX - rect.left;
                var y = e.clientY - rect.top;
                var centerX = rect.width / 2;
                var centerY = rect.height / 2;
                var rotateX = ((y - centerY) / centerY) * -20;
                var rotateY = ((x - centerX) / centerX) * 20;
                card.style.setProperty('--rX', rotateX + 'deg');
                card.style.setProperty('--rY', rotateY + 'deg');
                card.classList.add('active');
                if (shine) {
                    var angle = Math.atan2(y - centerY, x - centerX) * 180 / Math.PI - 90;
                    var pos = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    shine.style.background = 'linear-gradient(' + angle + 'deg, rgba(' + color1 + ',' + (0.7 + pos/rect.width*0.2) + ') 0%, rgba(' + color2 + ',0.15) 80%)';
                }
            });
            card.addEventListener('mouseleave', function() {
                card.style.setProperty('--rX', '0deg');
                card.style.setProperty('--rY', '0deg');
                card.classList.remove('active');
                if (shine) {
                    shine.style.background = '';
                }
            });
        });
    }, 100);
""";
        com.vaadin.flow.component.UI.getCurrent().getPage().executeJs(tiltScript);
    }

}
