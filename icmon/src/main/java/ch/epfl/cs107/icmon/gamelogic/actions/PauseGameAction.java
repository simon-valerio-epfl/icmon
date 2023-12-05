package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.ICMonPauseControl;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.PauseMenu;

import java.util.ArrayList;

public class PauseGameAction implements Action {

    final private ICMonPauseControl pauseControl;
    final private PauseMenu pauseMenu;
    public PauseGameAction(ICMonPauseControl pauseControl, PauseMenu pauseMenu) {
        this.pauseControl = pauseControl;
        this.pauseMenu = pauseMenu;
    }

    public void perform() {
        this.pauseControl.requestPause();
        this.pauseControl.setPauseMenu(this.pauseMenu);
    }
}
