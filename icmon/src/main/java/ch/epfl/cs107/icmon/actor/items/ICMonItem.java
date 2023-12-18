package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

import java.util.Collections;
import java.util.List;

public abstract class ICMonItem extends CollectableAreaEntity {
    private RPGSprite sprite;
    public ICMonItem(ICMonArea area, DiscreteCoordinates spawnPosition, String spriteName, double scaleFactor) {
        super(area, Orientation.DOWN, spawnPosition);
        this.sprite = new RPGSprite(spriteName, (float) (1f * scaleFactor), (float) (1f * scaleFactor), this);
    }

    public ICMonItem(ICMonArea area, DiscreteCoordinates spawnPosition, String spriteName) {
        this(area, spawnPosition, spriteName, 1);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}
