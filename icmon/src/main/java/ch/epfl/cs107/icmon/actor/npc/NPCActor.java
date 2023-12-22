package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

/**
 * Represents a NPC (Non-Playable Character) in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
abstract class NPCActor extends ICMonActor {
    final private Sprite sprite;

    /**
     * Creates a new NPC in the specified area
     *
     * @param area the area where the NPC shall spawn
     * @param orientation the orientation of the NPC
     * @param spawnPosition the position where the NPC shall spawn
     * @param spriteName the name of the sprite
     * @param scaleFactorWidth the width scale factor of the sprite
     * @param scaleFactorHeight the height scale factor of the sprite
     * @param customWidth the width of the sprite
     * @param customHeight the height of the sprite
     */
    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName, float scaleFactorWidth, float scaleFactorHeight, int customWidth, int customHeight) {
        super(area, orientation, spawnPosition);
        this.sprite = new RPGSprite(spriteName, scaleFactorWidth, scaleFactorHeight, this, new RegionOfInterest(0, 0, customWidth, customHeight));
    }

    /**
     * Creates a new NPC in the specified area with default values for the scale factor and the sprite dimensions
     *
     * @param area the area where the NPC shall spawn
     * @param orientation the orientation of the NPC
     * @param spawnPosition the position where the NPC shall spawn
     * @param spriteName the name of the sprite that will be used to draw the NPC
     */
    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName) {
        this(area, orientation, spawnPosition, spriteName, 1, 1.3215f, 16, 21);
    }

    /**
     * Updates the NPC sprite name
     */
    protected void setSpriteName(String spriteName) {
        this.sprite.setName(spriteName);
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    /**
     * An NPC prevents other entities from entering the cell where he is
     * @return always true
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * An NPC accepts distance interactions by default
     * @return always true
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    /**
     * An NPC does not accept contact interactions
     * @return always false
     */
    @Override
    public boolean isCellInteractable() {
        return false;
    }
}
