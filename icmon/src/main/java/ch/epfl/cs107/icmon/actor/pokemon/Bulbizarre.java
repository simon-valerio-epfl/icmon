package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Bulbizarre extends Pokemon {

    private final static String POKEMON_NAME = "bulbizarre";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;

    public Bulbizarre(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);
    }

}
