package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class FirstInteractionWithGarryEvent extends ICMonEvent {

    final private ICMon.ICMonGameState gameState;

    public FirstInteractionWithGarryEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMon.ICMonGameState gameState) {
        super(eventManager, player);
        this.gameState = gameState;
    }

    /**
     * While this event is active,
     * there can be a fight between a pokemon belonging to the owner
     * and the first one belonging to a garry
     * following a proximity interaction
     * @param garry an opponent
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(Garry garry, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted() && getPlayer().wantsRealViewInteraction()) {
            Pokemon garryPokemon = garry.getPokemons().get(0);
            getPlayer().fight(garryPokemon, garry);
        }
    }

    @Override
    public void update(float deltaTime) {}
}
