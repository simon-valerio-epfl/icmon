package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

import java.util.ArrayList;

public class RegisterEventAction implements Action {

    final private ICMonEvent event;
    final private ArrayList<ICMonEvent> events;

    public RegisterEventAction(ICMonEvent event, ArrayList<ICMonEvent> events) {
        this.events = events;
        this.event = event;
    }

    public void perform() {
        events.add(event);
    }
}
