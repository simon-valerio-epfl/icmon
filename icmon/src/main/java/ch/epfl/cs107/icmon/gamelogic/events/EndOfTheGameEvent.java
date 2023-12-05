package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class EndOfTheGameEvent extends ICMonEvent {

    public EndOfTheGameEvent(ICMonPlayer player) {
        super(player);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted()) {
            System.out.println("I heard that you collected the ball");

            Dialog dialog = new Dialog("end_of_game_event_interaction_with_icshopassistant");
            this.player.openDialog(dialog);

            this.complete();
        }
    }

    @Override
    public void update(float deltaTime) {
        // do nothing
    }
}
