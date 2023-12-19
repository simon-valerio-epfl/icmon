package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.Pedro;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectGiftEvent extends CollectItemEvent {

    /**
     * Create a new event that will be completed when the player collects the given item
     * @param eventManager used to add/remove events from icmon
     * @param player the main player of the game
     * @param gift the item that the player has to collect
     */
    public CollectGiftEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICGift gift) {
        super(eventManager, player, gift);
    }

    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        if (getPlayer().wantsRealViewInteraction()) {
            Dialog dialog = new Dialog("collect_gift_fabrice");
            getPlayer().openDialog(dialog);
        }
    }

    @Override
    public void interactWith(Pedro pedro, boolean isCellInteraction) {
        if (getPlayer().wantsRealViewInteraction()) {
            Dialog dialog = new Dialog("collect_gift_pedro");
            getPlayer().openDialog(dialog);
        }
    }
}
