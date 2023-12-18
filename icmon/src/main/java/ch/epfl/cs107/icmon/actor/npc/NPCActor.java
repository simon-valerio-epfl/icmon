package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

abstract class NPCActor extends ICMonActor {
    final private Sprite sprite;
    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName, int scaleFactor) {
        super(area, orientation, spawnPosition);
        this.sprite = new RPGSprite(spriteName, scaleFactor, 1.3215f * scaleFactor, this, new RegionOfInterest(0, 0, 16, 21));
    }

    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName) {
        this(area, orientation, spawnPosition, spriteName, 1);
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}
