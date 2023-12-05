package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.ICMonPauseControl;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.PauseMenu;

import java.util.ArrayList;

public class ResumeGameAction implements Action {

    final private ICMonPauseControl pauseControl;
    public ResumeGameAction(ICMonPauseControl pauseControl) {
        this.pauseControl = pauseControl;
    }

    public void perform() {
        this.pauseControl.requestResume();
    }
}
