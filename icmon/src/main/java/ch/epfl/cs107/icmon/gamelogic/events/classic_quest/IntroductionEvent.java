package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents the first event of the game, which is completed when the player closes the first dialog.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class IntroductionEvent extends ICMonEvent {
    final private Dialog dialog = new Dialog("welcome_to_icmon");

    /**
     * Being the first event, it will start automatically via the chained event
     *
     * @param eventManager the event manager to register the event
     * @param player the player that will give the key
     */
    public IntroductionEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);

        onStart(new OpenDialogAction(player, this.dialog));
    }

    /**
     * Completes the event on the dialog's completion
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (this.dialog.isCompleted()) {
            this.complete();
        }
    }

}
