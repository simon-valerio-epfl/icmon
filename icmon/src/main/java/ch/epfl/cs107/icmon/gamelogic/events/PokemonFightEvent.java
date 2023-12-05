package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonFightEvent extends ICMonEvent {

    private ICMonFight fightPauseMenu;
    public PokemonFightEvent(ICMonPlayer player, ICMonFight fightPauseMenu) {
        super(player);
        this.fightPauseMenu = fightPauseMenu;
    }

    @Override
    public void update(float deltaTime) {
        if (!this.fightPauseMenu.isRunning()) {
            this.complete();
        }
    }

    @Override
    public boolean hasPauseMenu() {
        return true;
    }

    @Override
    public PauseMenu getPauseMenu() {
        return this.fightPauseMenu;
    }
}
