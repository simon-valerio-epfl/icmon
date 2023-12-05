package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Bulbizarre extends Pokemon {

    private final static String POKEMON_NAME = "bulbizarre";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;

    public Bulbizarre(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE);
    }
}
