package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * ???
 */
public final class Arena extends ICMonArea {

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(6, 2);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(20, 15), this, new DiscreteCoordinates(4, 1), new DiscreteCoordinates(5, 1));
        registerActor(doorToTown);

        Bulbizarre bulbizarre = new Bulbizarre(this, Orientation.DOWN, new DiscreteCoordinates(6, 6));
        registerActor(bulbizarre);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "arena";
    }

}