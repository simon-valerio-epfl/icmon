package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

/**
 * ???
 */
public abstract class ICMonArea extends Area {

    /**
     * Should give a way to go back to the main area
     * and register the actors and the items spawning
     * on the area
     */
    protected abstract void createArea();

    /**
     *
     * @return some constant position where the player spawns when he arrives
     * on this area
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
     *
     * @return a constant about the drawing dimensions
     */
    @Override
    public final float getCameraScaleFactor() {
        return ICMon.CAMERA_SCALE_FACTOR;
    }

}
