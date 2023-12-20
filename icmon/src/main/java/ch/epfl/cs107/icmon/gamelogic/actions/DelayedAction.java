package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

import java.util.concurrent.CompletableFuture;

/**
 * Represents an action that executes itself after a delay.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class DelayedAction implements Action {
    private final Action action;
    private final int delay;

    /**
     * Creates a new delayed action.
     *
     * @param action the action to perform
     * @param delay the delay in milliseconds
     */
    public DelayedAction(Action action, int delay) {
        assert delay > 0;
        assert action != null;
        this.action = action;
        this.delay = delay;
    }

    @Override
    public void perform() {
        CompletableFuture.delayedExecutor(delay, java.util.concurrent.TimeUnit.MILLISECONDS).execute(action::perform);
    }
}
