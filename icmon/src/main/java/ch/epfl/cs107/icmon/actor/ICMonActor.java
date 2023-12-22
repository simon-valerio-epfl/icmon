package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.Collections;
import java.util.List;

/**
 * Represents an actor in the game
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public abstract class ICMonActor extends MovableAreaEntity {

    /**
     * Creates a new actor in the specified area
     * When spawning, the actor will not be moving
     *
     * @param area where the actor will spawn
     * @param orientation the spawning direction at which they will look at
     * @param spawnPosition the coordinates, on the spawning area, where they will appear
     */
    public ICMonActor(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition);
        resetMotion();
    }

    /**
     * Leaves the current area
     * Should be called before entering a different area
     */
    public void leaveArea () {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * Enters a new area
     * The game will visually follow the travelling actor and
     * the latter will stop moving when he arrives
     * Note: one should call leaveArea() to exit the old Area
     * before entering a new one
     *
     * @param area the new Area
     * @param spawnPosition the spawn position in the new Area
     */
    public void enterArea (Area area, DiscreteCoordinates spawnPosition) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(spawnPosition.toVector());
        resetMotion();
    }

    /**
     * An actor lets other entities enter the cell where he is
     * @return always false, but this method may have been overridden by a concrete subclass of ICMonActor
     */
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    /**
     * Gets the coordinates of the main cell where the actor is
     * @return the main coordinates of an actor
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * An actor accepts proximity interactions by default
     * @return always true, but this method could have been overridden by a concrete subclass of ICMonActor
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * An actor does not accept distance interactions by default
     * @return always false, but this method could have been overridden by a concrete subclass of ICMonActor
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }
}
