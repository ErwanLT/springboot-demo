package fr.eletutour.pokedex.service;

import fr.eletutour.pokedex.model.Pokemon;
import org.springframework.stereotype.Service;

@Service
public class NavigationService {

    private Pokemon selectedPokemon;

    public Pokemon getSelectedPokemon() {
        return selectedPokemon;
    }

    public void setSelectedPokemon(Pokemon selectedPokemon) {
        this.selectedPokemon = selectedPokemon;
    }
}
