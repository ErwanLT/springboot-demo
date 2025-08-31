package fr.eletutour.pokedex.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.pokedex.model.Pokemon;
import fr.eletutour.pokedex.service.NavigationService;
import fr.eletutour.pokedex.service.PokemonService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Route(value = "gen", layout = MainView.class)
@PageTitle("Pokemons by generation")
public class GenView extends VerticalLayout {

    Grid<Pokemon> grid = new Grid<>(Pokemon.class);
    Select<String> select = new Select<>();
    TextField search = new TextField();

    PokemonService service;
    NavigationService navigationService;

    public GenView(PokemonService pokemonService, NavigationService  navigationService){
        this.service = pokemonService;
        this.navigationService = navigationService;
        setSizeFull();
        configureSelect();
        configureGrid();

        add(getSelect(), getSearch(), getContent());
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

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
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
        List<Pokemon> pokemons = service.getGenerations().get(gen);
        grid.getColumns().getFirst()
                        .setFooter(String.format("%s pokemons", pokemons.size()));
        grid.setItems(pokemons);
        search.clear();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("pokedexId");

        grid.addColumn(pokemon -> pokemon.getName().getFr()).setHeader("Name");
        grid.addComponentColumn(pokemon -> {
            Div div = new Div();
            pokemon.getTypes().forEach(t -> div.add(new Image(t.getImage(), t.getName())));
            return div;
        }).setHeader("Types");
        grid.addComponentColumn(pokemon ->
        {
            Image img = new Image(pokemon.getSprites().getRegular(), String.valueOf(pokemon.getPokedexId()));
            img.setHeight(150, Unit.PIXELS);
            img.setWidth(150, Unit.PIXELS);
            return img;
        }).setHeader("Sprite");
        grid.addComponentColumn(pokemon -> {
            Button button = new Button("Voir Détails");
            button.addClickListener(e -> {
                navigationService.setSelectedPokemon(pokemon);
                UI.getCurrent().navigate(PokemonDetailView.class);
            });
            return button;
        }).setHeader("Détails");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout getSelect() {
        select.addValueChangeListener(e -> updateListGen());

        var toolbar = new HorizontalLayout(select);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private Component getSearch() {
        search.setLabel("Nom");
        search.setPrefixComponent(VaadinIcon.SEARCH.create());
        search.addValueChangeListener(e -> updateListSearch(e.getValue()));
        search.setClearButtonVisible(true);

        return new HorizontalLayout(search);
    }

    private void updateListSearch(String value) {
        ListDataProvider<Pokemon> dataProvider = (ListDataProvider<Pokemon>) grid.getDataProvider();
        var pokemonFromGrid = dataProvider.getItems();
        if(!StringUtils.isEmpty(value)){
            var filteredPokemon = pokemonFromGrid.stream()
                    .filter(p -> p.getName().getFr().toLowerCase().contains(value.toLowerCase()))
                    .toList();
            grid.setItems(filteredPokemon);
            grid.getColumns().getFirst()
                    .setFooter(String.format("%s pokemons", filteredPokemon.size()));
        } else {
            updateListGen();
        }
    }
}
