package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectKeyAtlantisEvent extends CollectItemEvent {
    final private ICMonItem item;
    public CollectKeyAtlantisEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonItem item) {
        super(eventManager, player, item);
        this.item = item;
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            this.complete();
        }
    }

    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        Dialog dialog = new Dialog("collect_key_fabrice.xml");
        getPlayer().openDialog(dialog);
    }
}