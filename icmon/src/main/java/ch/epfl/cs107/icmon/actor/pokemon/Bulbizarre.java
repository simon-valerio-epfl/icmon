package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bulbizarre extends Pokemon {

    private final static String POKEMON_NAME = "bulbizarre";
    private final static int MAX_HP = 10;
    private final static int DAMAGE = 1;
    private final static ICMonFightAction ATTACK_ACTION = new AttackFightAction();
    private final static ICMonFightAction RUN_AWAY_ACTION = new RunAwayFightAction();
    private final static ICMonFightAction[] ACTIONS = {ATTACK_ACTION, RUN_AWAY_ACTION};

    public Bulbizarre(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, POKEMON_NAME, MAX_HP, DAMAGE, ACTIONS);
    }

}
