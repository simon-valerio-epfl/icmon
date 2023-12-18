package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonFightEvent extends ICMonEvent {

    private ICMonFight fightPauseMenu;

    /**
     * This event pauses the game to host a fight
     * @param eventManager
     * @param player
     * @param fightPauseMenu
     */
    public PokemonFightEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonFight fightPauseMenu) {
        super(eventManager, player);
        this.fightPauseMenu = fightPauseMenu;
    }

    /**
     * This event completes when the fight ends
     */
    @Override
    public void update(float deltaTime) {
        if (!this.fightPauseMenu.isRunning()) {
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
        return this.fightPauseMenu;
    }
}
