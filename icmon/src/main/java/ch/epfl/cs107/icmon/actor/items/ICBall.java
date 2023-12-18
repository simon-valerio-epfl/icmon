package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class ICBall extends ICMonItem {
    final private static String SPRITE_NAME = "items/icball";
    final private static String SPAWNING_AREA = "town";

    /**
     * An istance of the class Ball is characterized by the Area
     * and the position where it appears
     * @param area where the ball shall spawn
     * @param spawnPosition
     */
    public ICBall(ICMonArea area, DiscreteCoordinates spawnPosition) {
        super(area, spawnPosition, SPRITE_NAME);
    }

    /**
     *
     * @return the name of the Area where this ball appeared
     */
    static public String getSpawningArea() {
        return SPAWNING_AREA;
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
