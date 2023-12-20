package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an action that opens a dialog.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class OpenDialogAction implements Action {
    final private ICMonPlayer player;
    final private Dialog dialog;

    /**
     * Creates a new open dialog action.
     *
     * @param player the player to open the dialog
     * @param dialog the dialog to open
     */
    public OpenDialogAction (ICMonPlayer player, Dialog dialog) {
        this.player = player;
        this.dialog = dialog;
    }

    @Override
    public void perform() {
        this.player.openDialog(this.dialog);
    }
}
