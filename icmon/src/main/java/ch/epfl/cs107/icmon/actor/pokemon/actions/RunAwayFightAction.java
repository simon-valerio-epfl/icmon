package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class RunAwayFightAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Run away";
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
