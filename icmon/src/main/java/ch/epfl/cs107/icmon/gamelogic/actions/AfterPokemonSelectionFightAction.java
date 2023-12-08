package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

public class AfterPokemonSelectionFightAction implements Action {

    Pokemon pokemon;
    ICMonPlayer player;
    ICMon.ICMonEventManager eventManager;
    Pokemon opponentPokemon;
    ICMonActor actor;
    boolean hasRealOpponent;

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, Pokemon pokemon, Pokemon opponentPokemon) {
        this.player = player;
        this.eventManager = eventManager;
        this.pokemon = pokemon;
        this.opponentPokemon = opponentPokemon;
        hasRealOpponent = false;
    }

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, Pokemon pokemon, Pokemon opponentPokemon, ICMonActor actor) {
        this.player = player;
        this.eventManager = eventManager;
        this.pokemon = pokemon;
        this.opponentPokemon = opponentPokemon;
        hasRealOpponent = true;
    }

    public void perform() {
        ICMonFight ourFight = new ICMonFight(pokemon, opponentPokemon);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, player, ourFight);
        player.suspendGameWithFightEvent(pokemonFightEvent);

        pokemonFightEvent.onComplete(new LeaveAreaAction((ICMonActor) actor));
    }
}
