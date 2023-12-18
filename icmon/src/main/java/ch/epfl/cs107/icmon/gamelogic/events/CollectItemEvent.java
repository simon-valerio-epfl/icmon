package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectItemEvent extends ICMonEvent {
    final private ICMonItem item;

    /**
     * This event is characterized by an item to collect
     * @param eventManager
     * @param player collecting the item
     * @param item
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

    /**
     * While this event is active,
     * there can be a certain dialog between the player and the shop assistants
     * following a proximity interaction
     * @param assistant
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("This is an interaction between the player and ICShopAssistant based on events !");

        Dialog dialog = new Dialog("collect_item_event_interaction_with_icshopassistant");
        getPlayer().openDialog(dialog);
    }
}
