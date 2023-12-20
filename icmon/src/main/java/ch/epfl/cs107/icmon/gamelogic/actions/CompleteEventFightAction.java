package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

/**
 * Represents an action that completes an event if the fight is won.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class CompleteEventFightAction implements Action {

    final private ICMonEvent event;
    final private ICMonFight fight;

    /**
     * Creates a new complete event action.
     *
     * @param event the event to complete
     * @param fight the fight to check
     */
    public CompleteEventFightAction(ICMonEvent event, ICMonFight fight) {
        this.event = event;
        this.fight = fight;
    }

    @Override
    public void perform() {
        if (this.fight.isWin()) {
            this.event.complete();
        }
    }
}
