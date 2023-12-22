package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Represents a ball that can be picked up by the player.
 * It usually contains a Pokémon, but can be anything.
 *
 * @see ch.epfl.cs107.icmon.actor.ICMonPlayer
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICBall extends ICMonItem {
    final private static String SPRITE_NAME = "items/icball";

    /**
     * Creates a new ICBall in the specified area
     *
     * @param area the area where the ball shall spawn
     * @param spawnPosition the position where the ball shall spawn
     */
    public ICBall(ICMonArea area, DiscreteCoordinates spawnPosition) {
        super(area, spawnPosition, SPRITE_NAME);
    }


    /**
     * A ball accepts distance interactions
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
