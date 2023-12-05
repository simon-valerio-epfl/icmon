package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {

    private float counter = 5f;

    public ICMonFight() {

    }

    @Override
    protected void drawMenu(Canvas c) {

    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        counter -= deltaTime;
    }

    public boolean isRunning() {
        return counter > 0;
    }
}
