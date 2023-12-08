package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class AttackFightAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Attack";
    }

    @Override
    public boolean doAction(Pokemon target) {
        target.damage(2);
        return true;
    }
}
