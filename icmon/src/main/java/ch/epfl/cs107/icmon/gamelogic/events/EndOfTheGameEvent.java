package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;

public class EndOfTheGameEvent extends ICMonEvent {

    public EndOfTheGameEvent(ICMonPlayer player) {
        super(player);
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted()) {
            System.out.println("I heard that you collected the ball");
            this.complete();
        }
    }

    @Override
    public void update(float deltaTime) {
        // do nothing
    }
}
