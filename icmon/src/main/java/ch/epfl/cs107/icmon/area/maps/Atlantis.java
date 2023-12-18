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

public class Atlantis extends ICMonArea {

    @Override
    public String getTitle() {
        return "atlantis";
    }
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(3, 1);
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        //registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(3,1), this, new DiscreteCoordinates(12,0));
        registerActor(doorToTown);
    }

}
