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

/**
 * Represents the shop area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Shop extends ICMonArea {
    final static DiscreteCoordinates SPAWNING_POSITION = new DiscreteCoordinates(3,1);
    @Override
    public String getTitle() {
        return "shop";
    }
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return SPAWNING_POSITION;
    }
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
