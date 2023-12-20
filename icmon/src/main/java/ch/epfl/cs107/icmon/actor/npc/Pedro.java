package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Pedro, a NPC that you see when the quest starts... and that will steal you.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Pedro extends NPCActor implements PokemonOwner {
    final private static String SPRITE_NAME = "actors/pedro";
    final private List<Pokemon> pokemons = new ArrayList<>();

    /**
     * Creates a new Pedro in the specified area
     *
     * @param area the area where Pedro shall spawn
     * @param orientation the orientation of Pedro
     * @param spawnPosition the position where Pedro shall spawn
     */
    public Pedro (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 1.7f, 1.7f, 32, 32);

        // by default, Pedro has a Latios
        addPokemon(PokemonName.LATIOS);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
    /**
     * Gets the list containing this pedro's pokemons
     * @return the list of pokemons owned by the current instance of Pedro
     */
    @Override
    public List<Pokemon> getPokemons() {
        return pokemons;
    }

}
