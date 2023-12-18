package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class AttackFightAction implements ICMonFightAction {
    @Override
    public String name() {
        return "Attack";
    }


    /**
     * a basic attack taking 2 hp from the enemy pokemon
     * @param target your opponent
     * @return whether the action had success
     */
    @Override
    public boolean doAction(Pokemon target) {
        target.damage(2);
        return true;
    }
}
