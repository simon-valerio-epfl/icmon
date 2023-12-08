package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

import java.util.ArrayList;

public class UnRegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ICMon.ICMonEventManager eventManager;

    public UnRegisterEventAction(ICMonEvent event, ICMon.ICMonEventManager eventManager) {
        this.eventManager = eventManager;
        this.event = event;
    }

    public void perform() {
        eventManager.registerEvent(event);
    }
}
