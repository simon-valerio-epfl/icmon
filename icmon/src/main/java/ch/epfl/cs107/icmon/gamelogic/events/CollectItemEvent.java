package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectItemEvent extends ICMonEvent {
    final private ICMonItem item;
    public CollectItemEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonItem item) {
        super(eventManager, player);
        this.item = item;
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }
}
