package ch.epfl.cs107.icmon.gamelogic.menu;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.actions.PokemonFightAction;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonPauseMenuGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

/**
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class GamePauseMenu extends PauseMenu {

    public enum PauseOption { FIRST, SECOND };
    public enum PauseMenuType { EXIT, RESUME };

    private ICMonPauseMenuGraphics menu;
    private boolean isSelected = false;
    private final PauseMenuType type;

    public GamePauseMenu(Keyboard keyboard, PauseMenuType type) {
        this.type = type;
        this.menu = new ICMonPauseMenuGraphics(CAMERA_SCALE_FACTOR, keyboard, type);
    }

    /**
     * @return true if the fight is still running
     */
    public boolean isRunning() {
        return !this.isSelected;
    }

    public PauseMenuType getType() {
        return this.type;
    }

    public PauseOption choice() {
        return this.menu.choice();
    }

    /**
     * Carries out the fight,
     * following the competitors' choices
     * and the evolution of the battle itself
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.menu.update(deltaTime);
        if (this.menu.choice() != null) {
            this.isSelected = true;
        }
    }

    @Override
    protected void drawMenu(Canvas c) {
        this.menu.draw(c);
    }
}
