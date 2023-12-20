package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents a Nidoqueen, a pokemon that can be encountered in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class Nidoqueen extends Pokemon {

    private final static String POKEMON_NAME = "nidoqueen";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 2;

    /**
     * Creates a new Nidoqueen in the specified area
     *
     * @param area the area where the Nidoqueen shall spawn
     * @param orientation the orientation of the Nidoqueen
     * @param spawnPosition the position where the Nidoqueen shall spawn
     */
    public Nidoqueen(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);

        // Nidoqueen can attack and run away
        setActions(
                new AttackFightAction(this),
                new RunAwayFightAction(this)
        );
    }

}
