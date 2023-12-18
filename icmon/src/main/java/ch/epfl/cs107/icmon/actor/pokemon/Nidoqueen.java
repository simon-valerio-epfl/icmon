package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public class Nidoqueen extends Pokemon {

    private final static String POKEMON_NAME = "nidoqueen";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 2;

    /**
     * Every istance of this class has a default name, nidoqueen,
     * a default number of max health points, 10
     * and a default attack power, 2
     *
     * @param area
     * @param orientation
     * @param spawnPosition
     */
    public Nidoqueen(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);
    }

}
