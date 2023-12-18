package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IntroductionEvent extends ICMonEvent {
    final private Dialog dialog = new Dialog("welcome_to_icmon");

    /**
     * Being the first event, it will start automatically, opening a dialog
     * @param eventManager
     * @param player
     */
    public IntroductionEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);

        onStart(new OpenDialogAction(player, this.dialog));
    }

    /**
     * completes the event on the dialog's completion
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (this.dialog.isCompleted()) {
            this.complete();
        }
    }

    /**
     * There is no interaction with the shop assistant,
     * as long as this event is not completed
     * @param assistant
     * @param isCellInteraction
     */
    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
    }
}
