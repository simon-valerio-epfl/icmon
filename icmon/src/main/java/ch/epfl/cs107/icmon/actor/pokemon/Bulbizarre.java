package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents a Bulbizarre, a pokemon that can be encountered in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class Bulbizarre extends Pokemon {

    private final static String POKEMON_NAME = "bulbizarre";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;

    /**
     * Create a new Bulbizarre in the specified area
     *
     * @param area the area where the Bulbizarre shall spawn
     * @param orientation the orientation of the Bulbizarre
     * @param spawnPosition the position where the Bulbizarre shall spawn
     */
    public Bulbizarre(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);

        // Bulbizarre can attack and run away
        setActions(
                new AttackFightAction(this),
                new RunAwayFightAction(this)
        );
    }

}
