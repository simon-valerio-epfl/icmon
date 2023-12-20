package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICKey;
import ch.epfl.cs107.icmon.actor.npc.Fabrice;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an event that is completed when the player collects the key in Atlantis.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class CollectKeyAtlantisEvent extends CollectItemEvent {

    /**
     * Creates a new event that will be completed when the player collects the given key
     *
     * @param eventManager the event manager used to add/remove events from icmon
     * @param player the player that will collect the key
     * @param key the key that the player has to collect
     */
    public CollectKeyAtlantisEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICKey key) {
        super(eventManager, player, key);
    }

    // todo add this to chained event
    @Override
    public void interactWith(Fabrice fabrice, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            Dialog dialog = new Dialog("collect_key_fabrice");
            getPlayer().openDialog(dialog);
        }
    }

}
