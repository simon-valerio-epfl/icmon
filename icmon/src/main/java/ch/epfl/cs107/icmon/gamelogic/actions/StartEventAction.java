package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that starts an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class StartEventAction implements Action{

    final private ICMonEvent event;

    /**
     * Creates a new start event action.
     *
     * @param event the event to start
     */
    public StartEventAction(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void perform() {
        this.event.start();
    }
}
