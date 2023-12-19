package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public interface ICMonFightAction {
    /**
     *
     * @return the name of the action
     */
    String name();

    /**
     * @param target an opponent
     * @return whether the fight will continue
     */
    boolean doAction(Pokemon attacker, Pokemon target);

}
