package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents the house area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class House extends ICMonArea {
    final static DiscreteCoordinates SPAWNING_POSITION = new DiscreteCoordinates(6, 2);
    @Override
    public String getTitle() {
        return "house";
    }
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return SPAWNING_POSITION;
    }
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(7,26), this, new DiscreteCoordinates(3,1), new DiscreteCoordinates(4, 1));
        registerActor(doorToTown);

        Garry garry = new Garry(this, Orientation.DOWN, new DiscreteCoordinates(1, 3));
        registerActor(garry);
    }

 }
