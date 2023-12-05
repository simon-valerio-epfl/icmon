package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Actor;

public class LeaveAreaAction implements Action {

    final private ICMonActor actor;

    public LeaveAreaAction(ICMonActor actor) {
        this.actor = actor;
    }

    public void perform() {
        actor.leaveArea();
    }
}
