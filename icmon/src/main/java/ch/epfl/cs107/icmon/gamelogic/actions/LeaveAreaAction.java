package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Actor;

/**
 * Represents an action that makes an actor leave its area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class LeaveAreaAction implements Action {

    final private ICMonActor actor;

    /**
     * Creates a new leave area action.
     *
     * @param actor the actor to make leave its area
     */
    public LeaveAreaAction(ICMonActor actor) {
        this.actor = actor;
    }

    @Override
    public void perform() {
        actor.leaveArea();
    }
}
