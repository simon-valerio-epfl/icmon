package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

abstract public class PokemonFightAction {

    private final String name;
    private final Pokemon pokemonDoer;

    public PokemonFightAction(String name, Pokemon pokemonDoer) {
        this.name = name;
        this.pokemonDoer = pokemonDoer;
    }

    public String name() {
        return name;
    }

    public Pokemon getPokemonDoer() {
        return pokemonDoer;
    }

    abstract public boolean doAction(Pokemon target);
}
