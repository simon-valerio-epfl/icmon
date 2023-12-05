package ch.epfl.cs107.icmon;

import ch.epfl.cs107.play.engine.PauseMenu;

public interface ICMonPauseControl {
    void requestPause();
    PauseMenu setPauseMenu(PauseMenu menu);
    void requestResume();
}

