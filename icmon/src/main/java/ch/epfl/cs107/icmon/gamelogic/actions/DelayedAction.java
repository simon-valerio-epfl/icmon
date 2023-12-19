package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

import java.util.concurrent.CompletableFuture;

public class DelayedAction implements Action {

    private final Action action;
    private final int delay;

    public DelayedAction(Action action, int delay) {
        this.action = action;
        this.delay = delay;
    }

    public void perform() {
        CompletableFuture.delayedExecutor(delay, java.util.concurrent.TimeUnit.MILLISECONDS).execute(() -> {
            action.perform();
        });
    }
}
