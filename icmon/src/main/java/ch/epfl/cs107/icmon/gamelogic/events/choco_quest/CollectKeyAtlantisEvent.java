package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICKey;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectKeyAtlantisEvent extends CollectItemEvent {

    /**
     * Create a new event that will be completed when the player collects the given key
     * @param eventManager used to add/remove events from icmon
     * @param player that will collect the key
     * @param key that the player has to collect
     */
    public CollectKeyAtlantisEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICKey key) {
        super(eventManager, player, key);
    }

    // todo add this to chained event
    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        if (getPlayer().wantsRealViewInteraction()) {
            Dialog dialog = new Dialog("collect_key_fabrice.xml");
            getPlayer().openDialog(dialog);
        }
    }
}
