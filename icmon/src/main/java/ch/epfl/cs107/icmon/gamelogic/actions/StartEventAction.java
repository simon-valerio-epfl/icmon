package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class StartEventAction implements Action{

    final private ICMonEvent event;
    public StartEventAction(ICMonEvent event) {
        this.event = event;
    }

    public void perform() {
        this.event.start();
    }
}
