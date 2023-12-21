package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.menu.GamePauseMenu;
import ch.epfl.cs107.play.engine.PauseMenu;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class PauseMenuEvent extends ICMonEvent {

    private final GamePauseMenu pauseMenu;
    private final ICMon.ICMonGameState gameState;

    /**
     * This event pauses the game to host a fight
     *
     * @param eventManager the event manager to register the event
     * @param player the player that will fight
     * @param pauseMenu the pause menu that will be used to host the fight
     */
    public PauseMenuEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, GamePauseMenu pauseMenu, ICMon.ICMonGameState gameState) {
        super(eventManager, player);
        assert pauseMenu != null;
        this.pauseMenu = pauseMenu;
        this.gameState = gameState;
    }

    /**
     * This event completes when the fight ends
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (!this.pauseMenu.isRunning()) {
            switch (this.pauseMenu.getType()) {
                case EXIT -> {
                    switch (this.pauseMenu.choice()) {
                        case FIRST -> // new game
                                this.complete();
                        case SECOND -> // exit
                                System.exit(0);
                    }
                }
                case RESUME -> {
                    switch (this.pauseMenu.choice()) {
                        case FIRST -> { // new game
                            this.complete();
                            CompletableFuture.delayedExecutor((long) (gameState.getFrameDuration() * 2), TimeUnit.MILLISECONDS).execute(this.gameState::resetGame);
                        }
                        case SECOND -> // resume
                                this.complete();
                    }
                }
            }
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
        return this.pauseMenu;
    }
}
