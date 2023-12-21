package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.maps.MyPocket;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a PokemonOwner, an actor that can own pokemons
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public interface PokemonOwner {

    // all the pokemons that can be owned by a PokemonOwner
    enum PokemonName { LATIOS, BULBIZARRE, NIDOQUEEN }

    // this area is designed to store the pokemons of all the PokemonOwner instances
    MyPocket pocket = new MyPocket();

    /**
     * Initialises a new Pokémon according to the given name and adds it to the pocket
     *
     * @param pokemonName the name of pokemon you want to add to the pocket. It can be: latios, bulbizarre or nidoqueen.
     */
    default void addPokemon(PokemonName pokemonName) {
        switch (pokemonName) {
            case LATIOS -> {
                getPokemons().add(new Latios(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case BULBIZARRE -> {
                getPokemons().add(new Bulbizarre(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case NIDOQUEEN -> {
                getPokemons().add(new Nidoqueen(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            default -> {
                System.out.println("addPokemon() : unknown pokemon name: " + pokemonName);
            }
        }
    }

    /**
     * Adds a random pokemon to the pocket
     * This can be useful when someone collects a Pokéball for instance
     */
    default void addRandomPokemon() {
        PokemonName[] availablePokemons = PokemonName.values();
        int randomIndex = (int) (Math.random() * availablePokemons.length);
        addPokemon(availablePokemons[randomIndex]);
    }

    /**
     * Gets the list of pokemons belonging to the current instance
     * @return the list of pokemons belonging to the current instance
     */
    List<Pokemon> getPokemons();
}
