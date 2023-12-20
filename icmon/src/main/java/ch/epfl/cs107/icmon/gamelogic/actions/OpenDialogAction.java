package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class OpenDialogAction implements Action {
    final private ICMonPlayer player;
    final private Dialog dialog;

    public OpenDialogAction (ICMonPlayer player, Dialog dialog) {
        this.player = player;
        this.dialog = dialog;
    }
    public void perform() {
        this.player.openDialog(this.dialog);
    }
}
