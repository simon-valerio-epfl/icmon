package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Garry, a NPC that the player will fight
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
final public class Garry extends NPCActor implements PokemonOwner {
    final private static String SPRITE_NAME = "actors/garry";
    final private List<Pokemon> pokemons = new ArrayList<>();

    /**
     * Creates a new Garry in the specified area
     *
     * @param area the area where Garry shall spawn
     * @param orientation the orientation of Garry
     * @param spawnPosition the position where Garry shall spawn
     */
    public Garry (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME);

        // by default, Garry has a Nidoqueen
        addPokemon(PokemonName.NIDOQUEEN);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
