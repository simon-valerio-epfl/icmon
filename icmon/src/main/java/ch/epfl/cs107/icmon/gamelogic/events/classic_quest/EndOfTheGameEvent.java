package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an event that is started when the game is ended...!
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class EndOfTheGameEvent extends ICMonEvent {

    /**
     * Creates a new event that will be completed when the player collects the given ball
     *
     * @param eventManager used to add/remove events from icmon
     * @param player that will collect the ball
     */
    public EndOfTheGameEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    /**
     * While this event is active,
     * there can be a final dialog between the player and the shop assistants
     * following a proximity interaction
     *
     * @param assistant the assistant that interacts with the player
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted() && getPlayer().wantsEntityViewInteraction()) {

            Dialog dialog = new Dialog("end_of_game_event_interaction_with_icshopassistant");
            getPlayer().openDialog(dialog);

            this.complete();
        }
    }

    @Override
    public void update(float deltaTime) {}
}
