package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents ProfOak, a NPC that will guide the player.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
final public class ProfOak extends NPCActor {
    final private static String SPRITE_NAME = "actors/prof-oak";

    /**
     * Creates a new ProfOak in the specified area
     *
     * @param area the area where ProfOak shall spawn
     * @param orientation the orientation of ProfOak
     * @param spawnPosition the position where ProfOak shall spawn
     */
    public ProfOak (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}
