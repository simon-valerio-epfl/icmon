package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.play.engine.PauseMenu;

/**
 * Represents an action that resumes the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ResumeGameAction implements Action {

    final private PauseMenu.Pausable pauseControl;

    /**
     * Creates a new resume game action.
     *
     * @param pauseControl the pause control to resume the game
     */
    public ResumeGameAction(PauseMenu.Pausable pauseControl) {
        assert pauseControl != null;
        this.pauseControl = pauseControl;
    }

    @Override
    public void perform() {
        this.pauseControl.requestResume();
    }
}
