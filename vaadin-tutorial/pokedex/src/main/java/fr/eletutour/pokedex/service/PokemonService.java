package fr.eletutour.pokedex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eletutour.pokedex.model.Name;
import fr.eletutour.pokedex.model.Pokemon;
import fr.eletutour.pokedex.model.Type;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Service de gestion des Pokémon.
 * Ce service fournit des méthodes pour charger, rechercher et filtrer les Pokémon
 * à partir de fichiers JSON. Il gère également le regroupement des Pokémon par génération
 * et la récupération des types de Pokémon.
 */
@Service
public class PokemonService {

    private List<Pokemon> allPokemon = new ArrayList<>();

    private Map<Integer, List<Pokemon>> pokemonByGen = new HashMap<>();

    /**
     * Initialise le service en chargeant les données des Pokémon depuis les fichiers JSON.
     * Cette méthode est appelée automatiquement après la construction du service.
     */
    @PostConstruct
    private void init(){
        loadPokemonFromJson();
    }

    /**
     * Recherche tous les Pokémon correspondant au filtre de texte fourni.
     * Si le filtre est vide ou null, retourne tous les Pokémon.
     *
     * @param stringFilter Le texte de filtrage (recherche insensible à la casse dans le nom français)
     * @return La liste des Pokémon correspondant au filtre
     */
    public List<Pokemon> findAllPokemon(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return allPokemon;
        } else {
            return allPokemon.stream()
                    .filter(p -> StringUtils.containsIgnoreCase(p.getName().getFr(), stringFilter))
                    .toList();
        }
    }

    /**
     * Récupère la liste des Pokémon regroupés par génération.
     *
     * @return Une Map associant le numéro de génération à la liste des Pokémon correspondants
     */
    public Map<Integer, List<Pokemon>> getGenerations(){
        return pokemonByGen;
    }

    /**
     * Récupère l'ensemble des types de Pokémon disponibles.
     * Les types sont triés selon l'ordre défini dans TypeComparator.
     *
     * @return Un ensemble trié de tous les types de Pokémon
     */
    public Set<Type> findAllTypes() {
        return allPokemon.stream()
                .flatMap(pokemon -> pokemon.getTypes().stream())
                .sorted(new TypeComparator())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Charge les données des Pokémon depuis les fichiers JSON.
     * Charge les données pour les générations 1 à 9.
     * Les fichiers doivent être présents dans le dossier static/gen/.
     */
    public void loadPokemonFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for(int i = 1; i<10; i++){
                File file = new ClassPathResource("static/gen/gen"+i+".json").getFile();
                Pokemon[] pokemonsArray = objectMapper.readValue(file, Pokemon[].class);
                var pokemonFromGen = Arrays.asList(pokemonsArray);
                pokemonByGen.put(i, pokemonFromGen);
                this.allPokemon.addAll(pokemonFromGen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recherche un Pokémon par son numéro dans le Pokédex.
     *
     * @param pokedexId Le numéro du Pokémon dans le Pokédex
     * @return Le Pokémon correspondant au numéro fourni
     */
    public Pokemon findById(int pokedexId) {
        return allPokemon.stream()
                .filter(pokemon -> pokemon.getPokedexId() == pokedexId )
                .toList().getFirst();
    }

    /**
     * Recherche un Pokémon par son nom.
     *
     * @param name L'objet Name contenant le nom du Pokémon
     * @return Le Pokémon correspondant au nom fourni
     */
    public Pokemon findByName(Name name) {
        return allPokemon.stream()
                .filter(pokemon -> pokemon.getName().getFr().equals(name.getFr()))
                .toList().getFirst();
    }
}
