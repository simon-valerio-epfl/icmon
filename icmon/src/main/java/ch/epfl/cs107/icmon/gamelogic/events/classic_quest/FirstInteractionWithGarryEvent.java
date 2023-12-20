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
     * following a view interaction
     * @param garry an opponent
     * @param isCellInteraction whether it's a cell interaction(true) or not
     */
    @Override
    public void interactWith(Garry garry, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            Pokemon garryPokemon = garry.getPokemons().get(0);
            getPlayer().fight(garryPokemon, garry, this);
            this.complete();
        }
    }

    @Override
    public void update(float deltaTime) {}
}
