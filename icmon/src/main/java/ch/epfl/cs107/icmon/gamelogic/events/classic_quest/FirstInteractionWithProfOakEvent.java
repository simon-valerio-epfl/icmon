package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FirstInteractionWithProfOakEvent extends ICMonEvent {

    private Dialog dialog;
    private boolean isTheDialogComplete = false;
    private boolean isInDialog = false;

    public FirstInteractionWithProfOakEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    /**
     * When the dialog completes, the player gets a pokemon, namely a Latios
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        // lazy evaluation, we can access dialog
        if (this.isInDialog && this.dialog.isCompleted()) {
            this.isInDialog = false;
            getPlayer().addPokemon("latios");
            if (isTheDialogComplete) this.complete();
        }
    }

    /**
     * The dialog continues until the end
     * @param dialogName the path to a dialog
     * @param isTheDialogComplete whether the dialog is completed or not
     */
    public void openDialog(String dialogName, boolean isTheDialogComplete) {
        this.dialog = new Dialog(dialogName);
        this.isInDialog = true;
        this.isTheDialogComplete = isTheDialogComplete;
        getPlayer().openDialog(this.dialog);
    }
    /**
     * While this event is active,
     * there can be a certain dialog between the player and a prof Oak
     * following a proximity interaction
     * @param profOak
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(ProfOak profOak, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted() && getPlayer().wantsRealViewInteraction()) {
            openDialog("first_interaction_with_prof_oak", true);
        }
    }
    /**
     * While this event is active,
     * there can be a certain dialog between the player and the shop assistants
     * following a proximity interaction
     * @param shopAssistant
     * @param isCellInteraction whether it's a proximity interaction(true) or not
     */
    @Override
    public void interactWith(ICShopAssistant shopAssistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted() && getPlayer().wantsRealViewInteraction()) {
            openDialog("first_interaction_with_oak_event_icshopassistant", false);
        }
    }

}
