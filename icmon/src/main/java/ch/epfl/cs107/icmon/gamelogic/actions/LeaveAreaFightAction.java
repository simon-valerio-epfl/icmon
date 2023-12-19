package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class LeaveAreaFightAction implements Action {

    final private ICMonActor actor;
    final private ICMonFight fight;
    public LeaveAreaFightAction(ICMonActor actor, ICMonFight fight) {
        this.actor = actor;
        this.fight = fight;
    }

    public void perform() {
        if (this.fight.isWin()) {
            this.actor.leaveArea();
        }
    }
}
