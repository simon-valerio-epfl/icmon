package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class ProfOak extends NPCActor {
    final private static String SPRITE_NAME = "actors/prof-oak";


    /**
     * This actor has his own sprite
     * @param area
     * @param orientation
     * @param spawnPosition
     */
    public ProfOak (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}
