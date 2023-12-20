package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

import java.util.concurrent.CompletableFuture;

/**
 * Represents an action that ends the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public class EndGameAction implements Action {
    private final ICMon.ICMonGameState gameState;

    /**
     * Creates a new end game action.
     *
     * @param gameState the game state
     */
    public EndGameAction(ICMon.ICMonGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void perform() {
        gameState.createEndMessage();
    }

}
