package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.PauseMenu;

import java.util.ArrayList;

public class PauseGameAction implements Action {

    final private ICMon.PauseMenuManager pauseMenuManager;
    final private PauseMenu pauseMenu;
    public PauseGameAction(ICMon.PauseMenuManager pauseMenuManager, PauseMenu pauseMenu) {
        this.pauseMenuManager = pauseMenuManager;
        this.pauseMenu = pauseMenu;
    }

    public void perform() {
        this.pauseMenuManager.requestPause();
        this.pauseMenuManager.setPauseMenu(this.pauseMenu);
    }
}
