package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * Represents a simple run-away action.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class RunAwayFightAction extends PokemonFightAction {

    /**
     * Creates a new run-away action
     *
     * @param pokemonDoer the pokemon that will do the action
     */
    public RunAwayFightAction (Pokemon pokemonDoer) {
        super("Run away", pokemonDoer);
    }

    /**
     * Basic action that ends the fight
     * @param target the pokemon that will be targeted by the action
     * @return whether the action had success
     */
    @Override
    public boolean doAction(Pokemon target) {
        return false;
    }
}
