package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Represents a gift that can be picked up by the player.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICGift extends ICMonItem {
    final private static String SPRITE_NAME = "items/gift";

    /**
     * Creates a new gift in the specified area
     *
     * @param area the area where the gift shall spawn
     * @param spawnPosition the position where the gift shall spawn
     */
    public ICGift(ICMonArea area, DiscreteCoordinates spawnPosition) {
        super(area, spawnPosition, SPRITE_NAME, 1.3);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
