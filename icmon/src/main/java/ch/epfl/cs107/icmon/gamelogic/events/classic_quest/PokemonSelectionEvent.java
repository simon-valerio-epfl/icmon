package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonSelectionEvent extends ICMonEvent {

    private PokemonSelectionMenu pauseMenu;
    public PokemonSelectionEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player, PokemonSelectionMenu pauseMenu) {
        super(eventManager, player);
        this.pauseMenu = pauseMenu;
    }

    @Override
    public void update(float deltaTime) {
        if (!this.pauseMenu.isRunning()) {
            this.complete();
        }
    }

    @Override
    public boolean hasPauseMenu() {
        return true;
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.pauseMenu;
    }
}
