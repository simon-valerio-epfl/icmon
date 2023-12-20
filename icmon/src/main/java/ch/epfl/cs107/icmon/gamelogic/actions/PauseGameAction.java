package ch.epfl.cs107.icmon.gamelogic.actions;
import ch.epfl.cs107.play.engine.PauseMenu;

/**
 * Represents an action that pauses the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class PauseGameAction implements Action {

    final private PauseMenu.Pausable pauseControl;

    /**
     * Creates a new pause game action.
     *
     * @param pauseControl the pause control to pause the game
     */
    public PauseGameAction(PauseMenu.Pausable pauseControl) {
        assert pauseControl != null;
        this.pauseControl = pauseControl;
    }

    @Override
    public void perform() {
        this.pauseControl.requestPause();
    }
}
