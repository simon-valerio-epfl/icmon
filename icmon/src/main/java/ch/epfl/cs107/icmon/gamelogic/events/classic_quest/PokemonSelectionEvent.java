package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;
import ch.epfl.cs107.play.engine.PauseMenu;

/**
 * Represents an event that handles a Pokémon selection.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class PokemonSelectionEvent extends ICMonEvent {

    private final PokemonSelectionMenu pauseMenu;

    /**
     * This event pauses the game to let the player choose a pokemon
     * @param eventManager the event manager to register the event
     * @param player the player that will select the pokemon
     * @param pauseMenu the pause menu that will be used to host the selection
     */
    public PokemonSelectionEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player, PokemonSelectionMenu pauseMenu) {
        super(eventManager, player);
        this.pauseMenu = pauseMenu;
    }

    /**
     * The event is completed once the pokemon is chosen
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (!this.pauseMenu.isRunning()) {
            this.complete();
        }
    }

    /**
     * This event has a pause menu
     * @return true
     */
    @Override
    public boolean hasPauseMenu() {
        return true;
    }

    /**
     * Returns the pause menu used to host the selection
     * @return the pause menu used to host the selection
     */
    @Override
    public PauseMenu getPauseMenu() {
        return this.pauseMenu;
    }
}
