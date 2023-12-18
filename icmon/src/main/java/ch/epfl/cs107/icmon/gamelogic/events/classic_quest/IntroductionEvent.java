package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class IntroductionEvent extends ICMonEvent {
    final private Dialog dialog = new Dialog("welcome_to_icmon");
    public IntroductionEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);

        onStart(new OpenDialogAction(player, this.dialog));
    }

    @Override
    public void update(float deltaTime) {
        if (this.dialog.isCompleted()) {
            this.complete();
        }
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
    }
}
