package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents a shop assistant that helps the player.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICShopAssistant extends NPCActor {
    final private static String SPRITE_NAME = "actors/assistant";

    /**
     * Creates a new shop assistant in the specified area
     *
     * @param area the area where the shop assistant shall spawn
     * @param orientation the orientation of the shop assistant
     * @param spawnPosition the position where the shop assistant shall spawn
     */
    public ICShopAssistant (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}
