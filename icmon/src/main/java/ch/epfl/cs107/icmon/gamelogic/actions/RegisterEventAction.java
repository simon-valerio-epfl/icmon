package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that registers an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class RegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ICMon.ICMonEventManager eventManager;

    /**
     * Creates a new register event action.
     *
     * @param event the event to register
     * @param eventManager the event manager to register the event
     */
    public RegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.eventManager = eventManager;
        this.event = event;
    }

    @Override
    public void perform() {
        eventManager.registerEvent(event);
    }
}
