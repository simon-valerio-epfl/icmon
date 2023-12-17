package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.icmon.actor.pokemon.Nidoqueen;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.maps.MyPocket;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public interface PokemonOwner {

    // Note : tous les owners de pokémons auront la même poker
    // C'est le comportement prévu.
    MyPocket pocket = new MyPocket();

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

            }
        }
    }

    ArrayList<Pokemon> getPokemons();
}
