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
     * @param scaleFactorWidth the width scale factor of the sprite (1 by default)
     * @param scaleFactorHeight the height scale factor of the sprite (1 by default)
     * @param customWidth the width of the sprite (16 by default)
     * @param customHeight the height of the sprite (21 by default)
     */
    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName, float scaleFactorWidth, float scaleFactorHeight, int customWidth, int customHeight) {
        super(area, orientation, spawnPosition);
        this.sprite = new RPGSprite(spriteName, scaleFactorWidth, scaleFactorHeight, this, new RegionOfInterest(0, 0, customWidth, customHeight));
    }

    /**
     * Creates a new NPC in the specified area with default values for the scale factor (1) and the sprite dimensions (16x21).
     *
     * @param area the area where the NPC shall spawn
     * @param orientation the orientation of the NPC
     * @param spawnPosition the position where the NPC shall spawn
     * @param spriteName the name of the sprite
     */
    public NPCActor (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String spriteName) {
        this(area, orientation, spawnPosition, spriteName, 1, 1.3215f, 16, 21);
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
}
