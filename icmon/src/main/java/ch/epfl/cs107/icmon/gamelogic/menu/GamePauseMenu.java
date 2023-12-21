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
 * Represents game pause menus (both welcome/exit and resume menus).
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class GamePauseMenu extends PauseMenu {

    public enum PauseOption { FIRST, SECOND };
    public enum PauseMenuType { EXIT, RESUME };

    // menu details
    private final ICMonPauseMenuGraphics menu;
    private final PauseMenuType type;

    // state management
    private boolean isSelected = false;

    public GamePauseMenu(Keyboard keyboard, PauseMenuType type) {
        this.type = type;
        this.menu = new ICMonPauseMenuGraphics(CAMERA_SCALE_FACTOR, keyboard, type);
    }

    /**
     * Gets the status of the pause menu
     * @return true if the user is still choosing
     */
    public boolean isRunning() {
        return !this.isSelected;
    }

    /**
     * Gets the type of the pause menu
     * @return the type of the pause menu (exit or resume)
     */
    public PauseMenuType getType() {
        return this.type;
    }

    /**
     * Gets the choice of the player
     * @return the chosen option (first or second)
     */
    public PauseOption choice() {
        return this.menu.choice();
    }

    /**
     * Carries out the choice of the player
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
