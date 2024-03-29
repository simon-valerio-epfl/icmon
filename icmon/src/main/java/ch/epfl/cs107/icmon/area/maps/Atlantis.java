package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Represents the shop area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Atlantis extends ICMonArea {

    /**
     * Gets the name of this area
     * @return the name of the area
     */
    @Override
    public String getTitle() {
        return "atlantis";
    }

    /**
     * gets the default spawning coordinates
     * @return some default spawning coordinates on this area
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(3, 1);
    }

    /**
     * it adds a door taking back to the main Area, town
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(9,7), this, new DiscreteCoordinates(12,0), new DiscreteCoordinates(13,0));
        registerActor(doorToTown);
    }

}
