package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that suspends an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class SuspendEventAction implements Action {

    final private ICMonEvent event;

    /**
     * Creates a new suspend event action.
     *
     * @param event the event to suspend
     */
    public SuspendEventAction(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void perform() {
        this.event.suspend();
    }
}
