package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMonPauseControl;
import ch.epfl.cs107.play.engine.PauseMenu;

public class ResumeGameAction implements Action {

    final private PauseMenu.Pausable pauseControl;
    public ResumeGameAction(PauseMenu.Pausable pauseControl) {
        this.pauseControl = pauseControl;
    }

    public void perform() {
        this.pauseControl.requestResume();
    }
}
