package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that completes an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class CompleteEventAction implements Action {
    final private ICMonEvent event;

    /**
     * Creates a new complete event action.
     *
     * @param event the event to complete
     */
    public CompleteEventAction(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void perform() {
        this.event.complete();
    }
}
