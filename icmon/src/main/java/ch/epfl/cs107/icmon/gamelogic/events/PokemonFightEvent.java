package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;

/**
 * Represents an event that handles a Pokémon fight.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class PokemonFightEvent extends ICMonEvent {

    private final ICMonFight fightPauseMenu;

    /**
     * This event pauses the game to host a fight
     *
     * @param eventManager the event manager to register the event
     * @param player the player that will fight
     * @param fightPauseMenu the pause menu that will be used to host the fight
     */
    public PokemonFightEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonFight fightPauseMenu) {
        super(eventManager, player);
        assert fightPauseMenu != null;
        this.fightPauseMenu = fightPauseMenu;
    }

    /**
     * This event completes when the fight ends
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (!this.fightPauseMenu.isRunning()) {
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
     * Returns the pause menu used to host the fight
     * @return the pause menu used to host the fight
     */
    @Override
    public PauseMenu getPauseMenu() {
        return this.fightPauseMenu;
    }
}
