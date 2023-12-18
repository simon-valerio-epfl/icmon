package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMonPauseControl;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PauseGameAction implements Action {

    final private PauseMenu.Pausable pauseControl;
    final private PauseMenu pauseMenu;
    public PauseGameAction(PauseMenu.Pausable pauseControl, PauseMenu pauseMenu) {
        this.pauseControl = pauseControl;
        this.pauseMenu = pauseMenu;
    }

    public void perform() {
        this.pauseControl.requestPause();
    }
}
