package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class CollectBallEvent extends CollectItemEvent {

    public CollectBallEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICBall ball) {
        super(eventManager, player, ball);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (getPlayer().wantsRealViewInteraction()) {
            System.out.println("This is an interaction between the player and ICShopAssistant based on events !");

            Dialog dialog = new Dialog("collect_item_event_interaction_with_icshopassistant");
            getPlayer().openDialog(dialog);
        }
    }
}
