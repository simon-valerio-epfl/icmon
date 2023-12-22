package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;

/**
 * Represents an action that is performed after a pokemon selection.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class AfterPokemonSelectionFightAction implements Action {

    private final PokemonSelectionMenu pokemonSelectionMenu;
    private final ICMonPlayer player;
    private final ICMon.ICMonEventManager eventManager;
    private final Pokemon opponentPokemon;
    private ICMonActor actor;

    private final Action performOnWin;
    private final Action performOnLoss;

    /**
     * Creates a new action that will be performed after a pokemon selection.
     *
     * @param player the player who selected the pokemon
     * @param eventManager the event manager to register the event
     * @param pokemonSelectionMenu the menu that was used to select the pokemon
     * @param opponentPokemon the opponent pokemon
     * @param performOnWin the action to perform if the player wins the fight
     * @param performOnLoss the action to perform if the player loses the fight
     */
    public AfterPokemonSelectionFightAction(ICMonPlayer player, ICMon.ICMonEventManager eventManager, PokemonSelectionMenu pokemonSelectionMenu, Pokemon opponentPokemon, Action performOnWin, Action performOnLoss) {
        assert player != null;
        assert eventManager != null;
        assert pokemonSelectionMenu != null;
        assert opponentPokemon != null;
        this.player = player;
        this.eventManager = eventManager;
        this.pokemonSelectionMenu = pokemonSelectionMenu;
        this.opponentPokemon = opponentPokemon;
        this.performOnWin = performOnWin;
        this.performOnLoss = performOnLoss;
    }

    /**
     * Creates a new action that will be performed after a pokemon selection.
     *
     * @param player the player who selected the pokemon
     * @param eventManager the event manager to register the event
     * @param pokemonSelectionMenu the menu that was used to select the pokemon
     * @param opponentPokemon the opponent pokemon
     * @param performOnWin the action to perform if the player wins the fight
     * @param performOnLoss the action to perform if the player loses the fight
     * @param actor the actor that will leave the area if the pokemon wins
     */
    public AfterPokemonSelectionFightAction(
            ICMonPlayer player,
            ICMon.ICMonEventManager eventManager,
            PokemonSelectionMenu pokemonSelectionMenu,
            Pokemon opponentPokemon,
            Action performOnWin,
            Action performOnLoss,
            ICMonActor actor
    ) {
        this(player, eventManager, pokemonSelectionMenu, opponentPokemon, performOnWin, performOnLoss);

        this.actor = actor;
    }

    /**
     * Creates a new fight based on the pokemon selection result.
     * Calls the actions to perform after the fight.
     */
    public void perform() {
        ICMonFight ourFight = new ICMonFight(pokemonSelectionMenu.getPokemon(), opponentPokemon);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, player, ourFight);
        player.suspendGameWithFightEvent(pokemonFightEvent);

        pokemonFightEvent.onComplete(
                new PerformOnFightResultAction(ourFight, performOnWin, performOnLoss)
        );

        if (actor != null) {
            pokemonFightEvent.onComplete(
                    new PerformOnFightResultAction(ourFight, new LeaveAreaAction(actor), null)
            );
        }

        // if the pokemon was alone in the countryside, it maybe has to leave
        if (actor == null) {
            pokemonFightEvent.onComplete(
                    new PerformOnFightResultAction(ourFight, new LeaveAreaAction(opponentPokemon), null)
            );
        }
    }
}
