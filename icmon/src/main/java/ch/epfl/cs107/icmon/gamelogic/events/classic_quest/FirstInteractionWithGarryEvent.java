package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an event that is completed when the player fights Garry (and win!)
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class FirstInteractionWithGarryEvent extends ICMonEvent {


    /**
     * Creates a new event that will be completed when the player fights Garry
     *
     * @param eventManager the event manager used to add/remove events from icmon
     * @param player the player that will fight Garry
     */
    public FirstInteractionWithGarryEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    /**
     * While this event is active,
     * there can be a fight between a pokemon belonging to the owner
     * and the first one belonging to a garry
     * following a view interaction
     *
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
