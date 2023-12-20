package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public class Pedro extends NPCActor implements PokemonOwner {
    final private static String SPRITE_NAME = "actors/pedro";
    final private ArrayList<Pokemon> pokemons = new ArrayList<>();

    /**
     * This a special character that you see when the quest starts...
     * but will soon disappear
     * @param area
     * @param orientation
     * @param spawnPosition
     */
    public Pedro (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 1.7f, 1.7f, 32, 32);
        addPokemon("latios");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

}
