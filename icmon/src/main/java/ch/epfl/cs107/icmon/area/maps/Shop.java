package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Shop extends ICMonArea {
    final static DiscreteCoordinates SPAWNING_POSITION = new DiscreteCoordinates(3,1);


    /**
     * @return the name of this map
     */
    @Override
    public String getTitle() {
        return "shop";
    }

    /**
     *
     * @return some default spawning coordinates on this area, namely (3,1)
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return SPAWNING_POSITION;
    }

    /**
     * it adds a door taking back to the main Area, town
     * this area is created with a character, a shop assistant,
     * at some default conditions namely (4,6)
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(25,19), this, new DiscreteCoordinates(3,1), new DiscreteCoordinates(4, 1));
        registerActor(doorToTown);

        ICShopAssistant shopAssistant = new ICShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(4, 6));
        registerActor(shopAssistant);
    }

}
