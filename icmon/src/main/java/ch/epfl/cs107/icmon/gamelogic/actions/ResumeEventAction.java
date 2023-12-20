package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that resumes an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ResumeEventAction implements Action {
    final private ICMonEvent event;
    /**
     * Creates a new resume event action.
     *
     * @param event the event to resume
     */
    public ResumeEventAction(ICMonEvent event) {
        assert event != null;
        this.event = event;
    }

    @Override
    public void perform() {
        this.event.resume();
    }
}
