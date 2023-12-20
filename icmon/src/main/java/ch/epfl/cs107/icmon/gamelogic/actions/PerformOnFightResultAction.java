package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

/**
 * Represents an action that is performed only if the specified fight is a win.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class PerformOnFightResultAction implements Action {

    private final ICMonFight fight;
    private final Action performOnWin;
    private final Action performOnLose;

    /**
     * Creates a new action that will perform the specified actions depending on the fight result.
     *
     * @param fight the fight to check
     * @param performOnWin the action to perform if the fight is a win
     * @param performOnLose the action to perform if the fight is a lose
     */
    public PerformOnFightResultAction(ICMonFight fight, Action performOnWin, Action performOnLose) {
        this.fight = fight;
        this.performOnWin = performOnWin;
        this.performOnLose = performOnLose;
    }

    @Override
    public void perform() {
        if (fight.isWin()) {
            if (performOnWin != null)
                performOnWin.perform();
        } else {
            if (performOnLose != null)
                performOnLose.perform();
        }
    }
}
