package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class RunAwayFightAction extends PokemonFightAction {

    public RunAwayFightAction (Pokemon pokemonDoer) {
        super("Run away", pokemonDoer);
    }

    /**
     * basic action that ends the fight
     * @param target your opponent
     * @return
     */
    @Override
    public boolean doAction(Pokemon target) {
        return false;
    }
}
