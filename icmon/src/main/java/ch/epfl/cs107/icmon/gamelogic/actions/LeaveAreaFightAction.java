package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

/**
 * Represents an action that makes an actor leave the area if the fight is won.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class LeaveAreaFightAction implements Action {

    final private ICMonActor actor;
    final private ICMonFight fight;

    /**
     * Creates a new leave area action.
     * @param actor the actor to make leave its area
     * @param fight the fight to check
     */
    public LeaveAreaFightAction(ICMonActor actor, ICMonFight fight) {
        this.actor = actor;
        this.fight = fight;
    }

    @Override
    public void perform() {
        if (this.fight.isWin()) {
            this.actor.leaveArea();
        }
    }
}
