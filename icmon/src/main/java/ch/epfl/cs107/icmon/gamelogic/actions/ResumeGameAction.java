package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMonPauseControl;

public class ResumeGameAction implements Action {

    final private ICMonPauseControl pauseControl;
    public ResumeGameAction(ICMonPauseControl pauseControl) {
        this.pauseControl = pauseControl;
    }

    public void perform() {
        this.pauseControl.requestResume();
    }
}
