package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public class Nidoqueen extends Pokemon {

    private final static String POKEMON_NAME = "nidoqueen";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;

    public Nidoqueen(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE, new ArrayList<>());
    }

}
