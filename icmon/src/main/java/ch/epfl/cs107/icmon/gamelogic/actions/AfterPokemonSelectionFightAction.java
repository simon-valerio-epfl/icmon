package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
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

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, PokemonSelectionMenu pokemonSelectionMenu, Pokemon opponentPokemon) {
        this.player = player;
        this.eventManager = eventManager;
        this.pokemonSelectionMenu = pokemonSelectionMenu;
        this.opponentPokemon = opponentPokemon;
        hasRealOpponent = false;
    }

    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, PokemonSelectionMenu pokemonSelectionMenu, Pokemon opponentPokemon, ICMonActor actor) {
        this(player, eventManager, pokemonSelectionMenu, opponentPokemon);
        this.actor = actor;
    }

    public void perform() {
        ICMonFight ourFight = new ICMonFight(pokemonSelectionMenu.getPokemon(), opponentPokemon);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, player, ourFight);
        player.suspendGameWithFightEvent(pokemonFightEvent);

        if (actor != null) {
            pokemonFightEvent.onComplete(new LeaveAreaAction(actor));
            // note: no need to make the pokemon leave if we fight a pokemon owner
            // it's stored in the pocket :)
        } else {
            pokemonFightEvent.onComplete(new LeaveAreaAction(opponentPokemon));
        }
    }
}
