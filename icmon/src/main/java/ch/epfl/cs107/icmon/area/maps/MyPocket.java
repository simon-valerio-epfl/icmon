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
 *This is a fictional area where owned pokemons live
 * It does nothing special
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class MyPocket extends ICMonArea {

    @Override
    public String getTitle() {
        return null;
    }
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }
    @Override
    protected void createArea() {}

}
