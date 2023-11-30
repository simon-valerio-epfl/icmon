package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

import java.util.ArrayList;

public class UnRegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ArrayList<ICMonEvent> events;

    public UnRegisterEventAction(ICMonEvent event, ArrayList<ICMonEvent> events) {
        this.events = events;
        this.event = event;
    }

    public void perform() {
        events.add(event);
    }
}
