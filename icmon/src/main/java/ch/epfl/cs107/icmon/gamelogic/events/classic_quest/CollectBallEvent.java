package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an event that is completed when the player collects the ball in the lake.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class CollectBallEvent extends CollectItemEvent {

    /**
     * Creates a new event that will be completed when the player collects the given ball
     * @param eventManager used to add/remove events from icmon
     * @param player that will collect the ball
     * @param ball that the player has to collect
     */
    public CollectBallEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICBall ball) {
        super(eventManager, player, ball);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            System.out.println("This is an interaction between the player and ICShopAssistant based on events !");

            Dialog dialog = new Dialog("collect_item_event_interaction_with_icshopassistant");
            getPlayer().openDialog(dialog);
        }
    }
}
