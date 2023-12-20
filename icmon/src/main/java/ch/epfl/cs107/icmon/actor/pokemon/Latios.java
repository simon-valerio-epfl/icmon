package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents a Latios, a pokemon that can be encountered in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class Latios extends Pokemon {

    private final static String POKEMON_NAME = "latios";
    private final static int MAX_HP = 12;
    private final static int DAMAGE = 1;

    /**
     * Creates a new Latios in the specified area
     *
     * @param area the area where the Latios shall spawn
     * @param orientation the orientation of the Latios
     * @param spawnPosition the position where the Latios shall spawn
     */
    public Latios(Area area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);

        // Latios can attack and run away
        setActions(
                new AttackFightAction(this),
                new RunAwayFightAction(this)
        );
    }

}
