package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents Fabrice, a NPC that requires the player help.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Fabrice extends NPCActor {
    final private static String SPRITE_NAME = "actors/fabrice";

    /**
     * Creates a new Fabrice in the specified area
     *
     * @param area the area where Fabrice shall spawn
     * @param orientation the orientation of Fabrice
     * @param spawnPosition the position where Fabrice shall spawn
     */
    public Fabrice (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 1.7f, 1.7f, 32, 32);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}
