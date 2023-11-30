package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.Collections;
import java.util.List;

public abstract class ICMonActor extends MovableAreaEntity {

    // todo demander à fabrice. créer constructeur classe abstraite ??
    public ICMonActor(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition);
        resetMotion();
    }

    @Override
    public boolean takeCellSpace() {
        // by default, an actor does not take the cell space
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public void leaveArea () {
        getOwnerArea().unregisterActor(this);
    }

    public void enterArea (ICMonArea area, DiscreteCoordinates spawnPosition) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(spawnPosition.toVector());
        resetMotion();
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
