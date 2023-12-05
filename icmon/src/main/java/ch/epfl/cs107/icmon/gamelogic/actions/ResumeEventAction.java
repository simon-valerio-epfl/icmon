package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class ResumeEventAction implements Action {
    final private ICMonEvent event;
    public ResumeEventAction(ICMonEvent event) {
        this.event = event;
    }

    public void perform() {
        this.event.resume();
    }
}
