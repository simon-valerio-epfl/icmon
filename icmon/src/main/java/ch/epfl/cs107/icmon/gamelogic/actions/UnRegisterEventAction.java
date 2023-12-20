package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

/**
 * Represents an action that unregisters an event.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class UnRegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ICMon.ICMonEventManager eventManager;

    /**
     * Creates a new unregister event action.
     *
     * @param event the event to unregister
     * @param eventManager the event manager to unregister the event
     */
    public UnRegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.eventManager = eventManager;
        this.event = event;
    }

    @Override
    public void perform() {
        eventManager.unRegisterEvent(event);
    }
}
