package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectItemEvent extends ICMonEvent {
    final private ICMonItem item;
    public CollectItemEvent(ICMonItem item, ICMonPlayer player) {
        super(player);
        this.item = item;
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("This is an interaction between the player and ICShopAssistant based on events !");

        Dialog dialog = new Dialog("collect_item_event_interaction_with_icshopassistant");
        getPlayer().openDialog(dialog);
    }
}
