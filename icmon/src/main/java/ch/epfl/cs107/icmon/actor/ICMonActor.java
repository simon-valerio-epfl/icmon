package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.area.ICMonArea;
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
     * Should be called before entering a different area
     */
    public void leaveArea () {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * Enters a new area.
     * the game will visually follow the travelling actor and
     * the latter will stop moving when he arrives
     * Note: one should call leaveArea() before entering a new Area
     *
     * @param area the landing area
     * @param spawnPosition the spawn position in the new area
     */
    public void enterArea (Area area, DiscreteCoordinates spawnPosition) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(spawnPosition.toVector());
        resetMotion();
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }
}
