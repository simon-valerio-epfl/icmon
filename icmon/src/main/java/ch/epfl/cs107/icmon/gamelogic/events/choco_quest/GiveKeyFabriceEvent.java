package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an event that gives the key to Fabrice.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class GiveKeyFabriceEvent extends ICMonEvent {

    /**
     * Creates a new give key to Fabrice event.
     *
     * @param eventManager the event manager to register the event
     * @param player the player that will give the key
     */
    public GiveKeyFabriceEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    @Override
    public void update(float deltaTime) {}

    /**
     * Completes the event when the player interacts with Fabrice
     * @param fabrice the fabrice that interacts with the player
     * @param isCellInteraction true if the interaction is a cell interaction
     */
    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            this.complete();
        }
    }
}
