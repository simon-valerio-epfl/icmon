package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;

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
        assert actor != null;
        this.actor = actor;
    }

    @Override
    public void perform() {
        actor.leaveArea();
    }
}
