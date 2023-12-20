package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.maps.MyPocket;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public interface PokemonOwner {
    //TODO
    // Note : every pokemon owner has the same pocket
    // We chose so because ..hmmmmmm

    MyPocket pocket = new MyPocket();

    /**
     * Initialises a new Pokemon according to the given name and adds it to the pocket
     * Currently, there are three available Pokemon types, namely:
     * latios, bulbizarre and nidoqueen
     *
     * @param pokemonName the type of pokemon you want to add to the pocket
     */
    default void addPokemon(String pokemonName) {
        switch (pokemonName) {
            case "latios" -> {
                getPokemons().add(new Latios(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case "bulbizarre" -> {
                getPokemons().add(new Bulbizarre(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case "nidoqueen" -> {
                getPokemons().add(new Nidoqueen(pocket, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            default -> {
                System.out.println("addPokemon() : unknown pokemon name: " + pokemonName);
            }
        }
    }

    /**
     *
     * @return the list of pokemons belonging to the current instance
     */
    ArrayList<Pokemon> getPokemons();
}
