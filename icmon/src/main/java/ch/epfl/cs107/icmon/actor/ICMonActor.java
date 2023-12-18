package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.Collections;
import java.util.List;

public abstract class ICMonActor extends MovableAreaEntity {

    /**
     * Initialising an ICMonActor implies assigning him
     * a departing position.
     * He will spawn without moving
     *
     * @param area where he'll spawn
     * @param orientation the spawning direction at which he will look at
     * @param spawnPosition the coordinates, on the spawning area, where he will appear
     */
    public ICMonActor(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition);
        resetMotion();
    }

    /**
     * Some other actor can stay on the same cell as a ICMonActor
     * One should feel free to override this method while
     * extending this class
     * @return
     */
    @Override
    public boolean takeCellSpace() {

        return false;
    }

    /**
     *
     * @return the main cell where the current instance is
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * Should be called before entering a different area
     */
    public void leaveArea () {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * One should call leaveArea() before entering a new Area
     * The game will visually follow the travelling actor and
     * the latter will stop moving when he arrives
     *
     * @param area the landing one
     * @param spawnPosition belonging to the landing Area
     */
    public void enterArea (Area area, DiscreteCoordinates spawnPosition) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(spawnPosition.toVector());
        resetMotion();
    }

    /**
     * by default an ICMonActor accepts proximity interactions
     * @return
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * by default an ICMonActor does not accept distance interactions
     * @return
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }
}
