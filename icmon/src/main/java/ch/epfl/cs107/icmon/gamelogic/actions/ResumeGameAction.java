package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.PauseMenu;

import java.util.ArrayList;

public class ResumeGameAction implements Action {

    final private ICMon.PauseMenuManager pauseMenuManager;
    public ResumeGameAction(ICMon.PauseMenuManager pauseMenuManager) {
        this.pauseMenuManager = pauseMenuManager;
    }

    public void perform() {
        this.pauseMenuManager.requestResume();
    }
}
