package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonSelectionEvent extends ICMonEvent {

    private PokemonSelectionMenu pauseMenu;

    /**
     * This event pauses the game to let the player choose a pokemon
     * @param eventManager
     * @param player
     * @param pauseMenu
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
     * @return
     */
    @Override
    public boolean hasPauseMenu() {
        return true;
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.pauseMenu;
    }
}
