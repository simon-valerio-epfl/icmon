package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;

public class LeaveAreaPokemonDeadAction implements Action {

    final private ICMonActor opponent;
    final private Pokemon pokemon;

    /**
     * associates the two pokemons to the action
     * @param opponent
     * @param pokemon
     */
    public LeaveAreaPokemonDeadAction(ICMonActor opponent, Pokemon pokemon) {
        this.opponent = opponent;
        this.pokemon = pokemon;
    }

    public void perform() {
        if (!this.pokemon.isAlive()) {
            this.opponent.leaveArea();
        }
    }
}
