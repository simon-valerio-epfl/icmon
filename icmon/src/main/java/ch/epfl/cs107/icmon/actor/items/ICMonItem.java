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

/**
 * Represents any item in the game that can be picked up by the player.
 */
public abstract class ICMonItem extends CollectableAreaEntity {
    private final RPGSprite sprite;

    /**
     * Creates a new collectable item in the specified area
     *
     * @param area where the item shall spawn
     * @param spawnPosition
     * @param spriteName
     * @param scaleFactor dimension of the item
     */
    public ICMonItem(ICMonArea area, DiscreteCoordinates spawnPosition, String spriteName, double scaleFactor) {
        super(area, Orientation.DOWN, spawnPosition);
        this.sprite = new RPGSprite(spriteName, (float) (1f * scaleFactor), (float) (1f * scaleFactor), this);
    }

    /**
     * Creates a new collectable item in the specified area with a default value for the scale factor
     *
     * @param area where the item shall spawn
     * @param spawnPosition where the item shall spawn
     * @param spriteName name of the sprite
     */
    public ICMonItem(ICMonArea area, DiscreteCoordinates spawnPosition, String spriteName) {
        this(area, spawnPosition, spriteName, 1);
    }

    /**
     * Other entities can not enter the cell where an item is
     * @return always true
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * An item accepts proximity interactions by default
     * @return always true, but this method could have been overridden by a concrete subclass of ICMonItem
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * An item does not accept distance interactions by default
     * @return always false, but this method could have been overridden by a concrete subclass of ICMonItem
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    /**
     * Gets the coordinates of the main cell where the item is
     * @return the main coordinates of an item
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}
