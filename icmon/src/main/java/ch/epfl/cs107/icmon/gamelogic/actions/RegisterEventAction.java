package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class RegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ICMon.ICMonEventManager eventManager;

    public RegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.eventManager = eventManager;
        this.event = event;
    }

    public void perform() {
        eventManager.registerEvent(event);
    }
}
