package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;

public class AfterPokemonSelectionFightAction implements Action {

    PokemonSelectionMenu pokemonSelectionMenu;
    ICMonPlayer player;
    ICMon.ICMonEventManager eventManager;
    Pokemon opponentPokemon;
    ICMonActor actor;

    boolean hasRealOpponent;
    ICMonEvent toCompleteOnWin;

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, PokemonSelectionMenu pokemonSelectionMenu, Pokemon opponentPokemon, ICMonEvent toCompleteOnWin) {
        this.player = player;
        this.eventManager = eventManager;
        this.pokemonSelectionMenu = pokemonSelectionMenu;
        this.opponentPokemon = opponentPokemon;
        this.toCompleteOnWin = toCompleteOnWin;
        hasRealOpponent = false;
    }

    public AfterPokemonSelectionFightAction(
            ICMonPlayer player,
            ICMon.ICMonEventManager eventManager,
            PokemonSelectionMenu pokemonSelectionMenu,
            Pokemon opponentPokemon,
            ICMonEvent toCompleteOnWin,
            ICMonActor actor
    ) {
        this(player, eventManager, pokemonSelectionMenu, opponentPokemon, toCompleteOnWin);
        this.actor = actor;
    }

    public void perform() {
        ICMonFight ourFight = new ICMonFight(pokemonSelectionMenu.getPokemon(), opponentPokemon);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, player, ourFight);
        player.suspendGameWithFightEvent(pokemonFightEvent);

        if (toCompleteOnWin != null) {
            pokemonFightEvent.onComplete(new CompleteEventFightAction(toCompleteOnWin, ourFight));
        }

        if (actor != null) {
            pokemonFightEvent.onComplete(new LeaveAreaFightAction(actor, ourFight));
        }

        // if the pokemon was alone in the countryside, it maybe has to leave
        if (actor == null) {
            pokemonFightEvent.onComplete(new LeaveAreaFightAction(opponentPokemon, ourFight));
        }
    }
}
