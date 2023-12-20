package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;

// todo complete this

/**
 * Represents an action that is performed after a pokemon selection.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class AfterPokemonSelectionFightAction implements Action {

    PokemonSelectionMenu pokemonSelectionMenu;
    ICMonPlayer player;
    ICMon.ICMonEventManager eventManager;
    Pokemon opponentPokemon;
    ICMonActor actor;

    boolean hasRealOpponent;
    Action executeOnFightWin;
    Action executeOnFightLose;

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, PokemonSelectionMenu pokemonSelectionMenu, Pokemon opponentPokemon, Action executeOnFightWin, Action executeOnFightLose) {
        assert player != null;
        assert eventManager != null;
        assert pokemonSelectionMenu != null;
        assert opponentPokemon != null;
        this.player = player;
        this.eventManager = eventManager;
        this.pokemonSelectionMenu = pokemonSelectionMenu;
        this.opponentPokemon = opponentPokemon;
        this.executeOnFightWin = executeOnFightWin;
        this.executeOnFightLose = executeOnFightLose;
        hasRealOpponent = false;
    }

    public AfterPokemonSelectionFightAction(
            ICMonPlayer player,
            ICMon.ICMonEventManager eventManager,
            PokemonSelectionMenu pokemonSelectionMenu,
            Pokemon opponentPokemon,
            Action executeOnFightWin,
            Action executeOnFightLose,
            ICMonActor actor
    ) {
        this(player, eventManager, pokemonSelectionMenu, opponentPokemon, executeOnFightWin, executeOnFightLose);
        assert actor != null;
        this.actor = actor;
    }

    public void perform() {
        ICMonFight ourFight = new ICMonFight(pokemonSelectionMenu.getPokemon(), opponentPokemon);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, player, ourFight);
        player.suspendGameWithFightEvent(pokemonFightEvent);

        pokemonFightEvent.onComplete(
                new PerformOnFightResultAction(ourFight, executeOnFightWin, executeOnFightLose)
        );

        if (actor != null) {
            pokemonFightEvent.onComplete(
                    new PerformOnFightResultAction(ourFight, new LeaveAreaAction(actor), null)
            );
        }

        // if the pokemon was alone in the countryside, it maybe has to leave
        if (actor == null) {
            pokemonFightEvent.onComplete(
                    new PerformOnFightResultAction(ourFight, new LeaveAreaAction(player), null)
            );
        }
    }
}
