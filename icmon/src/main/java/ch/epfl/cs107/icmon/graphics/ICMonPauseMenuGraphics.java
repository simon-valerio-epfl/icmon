package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.menu.GamePauseMenu;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.GraphicsEntity;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


/**
 * Represents pause menus (both welcome/exit and resume menus).
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class ICMonPauseMenuGraphics implements Graphics {
    private GamePauseMenu.PauseOption choice;
    private GamePauseMenu.PauseOption currentState = GamePauseMenu.PauseOption.FIRST;
    private final ImageGraphics background;
    private final Keyboard keyboard;
    private final float scaleFactor;

    /**
     * Creates a new pause menu graphics.
     *
     * @param scaleFactor the scale factor used for centering graphics
     * @param keyboard the keyboard used for interaction
     * @param type the type of the pause menu (exit or resume)
     */
    public ICMonPauseMenuGraphics(float scaleFactor, Keyboard keyboard, GamePauseMenu.PauseMenuType type) {
        this.scaleFactor = scaleFactor;
        this.keyboard = keyboard;
        this.background = new ImageGraphics(ResourcePath.getBackground(type.equals(GamePauseMenu.PauseMenuType.EXIT) ? "menu_exit" : "menu_resume"), scaleFactor, scaleFactor);
    }

    /**
     * Gets the choice of the player
     * @return the chosen option (first or second)
     */
    public GamePauseMenu.PauseOption choice() {
        return this.choice;
    }

    /**
     * Draws the menu interface once the pause menu is activated
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    public void update(float deltaTime) {
        // HR : Keyboard management
        if (keyboard.get(Keyboard.DOWN).isPressed()){
            this.currentState = GamePauseMenu.PauseOption.SECOND;
        } else if (keyboard.get(Keyboard.UP).isPressed())
            this.currentState = GamePauseMenu.PauseOption.FIRST;
        else if (keyboard.get(Keyboard.ENTER).isPressed())
            this.choice = this.currentState;
    }

    @Override
    public void draw(Canvas canvas) {
        // HR : Draw the background
        this.background.draw(canvas);
        getFlake(scaleFactor).draw(canvas);
    }

    private GraphicsEntity getFlake (float scaleFactor) {
        Vector flakePosition = this.currentState.equals(GamePauseMenu.PauseOption.FIRST) ? getFirstFlakePosition(scaleFactor) : getSecondFlakePosition(scaleFactor);
        return new GraphicsEntity(flakePosition, new ImageGraphics(ResourcePath.getBackground("flake_selector"), 13, 13 ));
    }

    private Vector getFirstFlakePosition (float scaleFactor) {
        return new ch.epfl.cs107.play.math.Vector(scaleFactor / 2 - 6.5f, scaleFactor / 2 - 6.5f);
    }

    private Vector getSecondFlakePosition (float scaleFactor) {
        return  new ch.epfl.cs107.play.math.Vector(scaleFactor / 2 - 6.5f, scaleFactor / 2 - 8.4f);
    }

}
