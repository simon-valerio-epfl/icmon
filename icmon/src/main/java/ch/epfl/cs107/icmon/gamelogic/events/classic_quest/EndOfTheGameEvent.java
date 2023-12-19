package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class EndOfTheGameEvent extends ICMonEvent {


    public EndOfTheGameEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    /**
     * While this event is active,
     * there can be a final dialog between the player and the shop assistants
     * following a proximity interaction
     * @param assistant
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted() && getPlayer().wantsRealViewInteraction()) {
            System.out.println("I heard that you collected the ball");

            Dialog dialog = new Dialog("end_of_game_event_interaction_with_icshopassistant");
            getPlayer().openDialog(dialog);

            this.complete();
        }
    }

    @Override
    public void update(float deltaTime) {}
}
