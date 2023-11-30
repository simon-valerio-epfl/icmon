package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.items.ICMonItem;

public class CollectItemEvent extends ICMonEvent {
    final private ICMonItem item;
    public CollectItemEvent(ICMonItem item) {
        this.item = item;
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }
}
