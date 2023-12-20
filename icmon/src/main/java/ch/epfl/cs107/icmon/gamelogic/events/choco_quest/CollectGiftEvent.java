package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.actor.npc.Pedro;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an event that is completed when the player collects the gift in the lake.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class CollectGiftEvent extends CollectItemEvent {

    /**
     * Creates a new event that will be completed when the player collects the given gift
     *
     * @param eventManager the event manager used to add/remove events from icmon
     * @param player the player that will collect the gift
     * @param gift the gift that the player has to collect
     */
    public CollectGiftEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICGift gift) {
        super(eventManager, player, gift);
    }

    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            Dialog dialog = new Dialog("collect_gift_fabrice");
            getPlayer().openDialog(dialog);
        }
    }

    @Override
    public void interactWith(Pedro pedro, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            Dialog dialog = new Dialog("collect_gift_pedro");
            getPlayer().openDialog(dialog);
        }
    }
}
