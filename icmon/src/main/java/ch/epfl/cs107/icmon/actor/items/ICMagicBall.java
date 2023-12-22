package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Represents a magic ball item.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMagicBall extends ICMonItem {
    final private static String SPRITE_NAME = "items/icmagicball_big";

    /**
     * Creates a new magic ball
     *
     * @param area the area to which the magic ball belongs
     * @param spawnPosition the position where the magic ball will spawn
     */
    public ICMagicBall(ICMonArea area, DiscreteCoordinates spawnPosition) {
        super(area, spawnPosition, SPRITE_NAME);
    }

    /**
     * A magic ball accepts distance interactions
     * @return always true
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
