package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FirstInteractionWithProfOakEvent extends ICMonEvent {

    private Dialog dialog;
    private boolean isFinalInDialog = false;
    private boolean isInDialog = false;

    public FirstInteractionWithProfOakEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    @Override
    public void update(float deltaTime) {
        // lazy evaluation, we can access dialog
        if (this.isInDialog && this.dialog.isCompleted()) {
            this.isInDialog = false;
            getPlayer().addPokemon("latios");
            if (isFinalInDialog) this.complete();
        }
    }

    public void openDialog(String dialogName, boolean isFinalInDialog) {
        this.dialog = new Dialog(dialogName);
        this.isInDialog = true;
        this.isFinalInDialog = isFinalInDialog;
        getPlayer().openDialog(this.dialog);
    }

    @Override
    public void interactWith(ProfOak profOak, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted()) {
            openDialog("first_interaction_with_prof_oak", true);
        }
    }
    @Override
    public void interactWith(ICShopAssistant shopAssistant, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted()) {
            openDialog("first_interaction_with_oak_event_icshopassistant", false);
        }
    }


}
