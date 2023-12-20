package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

/**
 * Represents an area in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public abstract class ICMonArea extends Area {

    /**
     * This method shall create a door to go back to the main area
     * and register the actors and items
     * that will spawn in the area
     */
    protected abstract void createArea();

    /**
     * Gets the player's spawn position
     * @return some constant position where the player spawns when they arrive in this area
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /**
     * (re)starts the game
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return super.begin()
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICMonBehavior(window, getTitle()));
            createArea();
            return true;
        }
        return false;
    }

    /**
     * Gets the camera scale factor, a constant about the drawing dimensions
     * @return the camera scale factor
     */
    @Override
    public final float getCameraScaleFactor() {
        return ICMon.CAMERA_SCALE_FACTOR;
    }

}
