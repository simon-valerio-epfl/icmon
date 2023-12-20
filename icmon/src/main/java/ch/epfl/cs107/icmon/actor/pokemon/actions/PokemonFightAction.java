package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * Represents any pokemon fight action
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
abstract public class PokemonFightAction {
    private final String name;
    private final Pokemon pokemonDoer;

    /**
     * Creates a new pokemon fight action
     *
     * @param name the name of the action
     * @param pokemonDoer the pokemon that will do the action
     */
    public PokemonFightAction(String name, Pokemon pokemonDoer) {
        this.name = name;
        this.pokemonDoer = pokemonDoer;
    }

    /**
     * Gets the name of the action
     * @return the name of the action
     */
    public String name() {
        return name;
    }

    /**
     * Gets the pokemon that will do the action
     * @return the pokemon that will do the action
     */
    public Pokemon getPokemonDoer() {
        return pokemonDoer;
    }

    /**
     * Does the action
     * @param target the pokemon that will be targeted by the action
     * @return whether the fight should continue after the action
     */
    abstract public boolean doAction(Pokemon target);
}
