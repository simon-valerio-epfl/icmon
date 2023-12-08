package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

import java.util.ArrayList;

public class SuspendEventAction implements Action {

    final private ICMonEvent event;
    public SuspendEventAction(ICMonEvent event) {
        this.event = event;
    }

    public void perform() {
        this.event.suspend();
    }
}