package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Bulbizarre extends Pokemon {

    private final static String POKEMON_NAME = "bulbizarre";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;

    /**
     * Every istance of this class has a default name, bulbizarre,
     * a default number of max health points, 10
     * and a default attack power, 1
     *
     * @param area
     * @param orientation
     * @param spawnPosition
     */
    public Bulbizarre(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);
    }

}
