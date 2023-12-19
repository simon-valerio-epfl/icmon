package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

abstract public class CollectItemEvent extends ICMonEvent {
    final private ICMonItem item;

    /**
     * This event is characterized by an item to collect
     * @param eventManager to add/remove events from icmon
     * @param player collecting the item
     * @param item to be collected
     */
    public CollectItemEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonItem item) {
        super(eventManager, player);
        this.item = item;
    }

    /**
     * The event is completed once someone has collected the item
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }

}
