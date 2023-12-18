package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public class Latios extends Pokemon {

    private final static String POKEMON_NAME = "latios";
    private final static int MAX_HP = 12;
    private final static int DAMAGE = 1;

    /**
     * Every istance of this class has a default name, latios,
     * a default number of max health points, 12
     * and a default attack power, 1
     *
     * @param area
     * @param orientation
     * @param spawnPosition
     */
    public Latios(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);
    }

}
