package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;

/**
 * Represents an action that makes an actor leave its area if their pokemon is dead.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class LeaveAreaPokemonDeadAction implements Action {

    final private ICMonActor opponent;
    final private Pokemon pokemon;

    /**
     * Creates a new leave area action.
     *
     * @param opponent the opponent to make leave its area
     * @param pokemon the pokemon to check
     */
    public LeaveAreaPokemonDeadAction(ICMonActor opponent, Pokemon pokemon) {
        this.opponent = opponent;
        this.pokemon = pokemon;
    }

    public void perform() {
        if (this.pokemon.isDead()) {
            this.opponent.leaveArea();
        }
    }
}
