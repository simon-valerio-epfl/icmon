package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class CompleteEventFightAction implements Action {

    final private ICMonEvent event;
    final private ICMonFight fight;
    public CompleteEventFightAction(ICMonEvent event, ICMonFight fight) {
        this.event = event;
        this.fight = fight;
    }

    public void perform() {
        if (this.fight.isWin()) {
            this.event.complete();
        }
    }
}
