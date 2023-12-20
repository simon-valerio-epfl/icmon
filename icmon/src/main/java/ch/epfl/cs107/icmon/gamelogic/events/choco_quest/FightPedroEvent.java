package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Pedro;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.DelayedAction;
import ch.epfl.cs107.icmon.gamelogic.actions.OpenDialogAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

/**
 * Represents an event that is completed when the player fights Pedro (and wins!).
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class FightPedroEvent extends ICMonEvent {

    private boolean keyHasBeenStolen = false;
    private final ICMonSoundManager soundManager;

    /**
     * Creates a new event that will be completed when the player fights Pedro and wins.
     *
     * @param eventManager the event manager used to add/remove events from icmon
     * @param soundManager the sound manager used to play sounds
     * @param player the player that will fight Pedro
     */
    public FightPedroEvent(ICMon.ICMonEventManager eventManager, ICMonSoundManager soundManager, ICMonPlayer player) {
        super(eventManager, player);
        this.soundManager = soundManager;
    }

    @Override
    public void update(float deltaTime) {}

    /**
     * Pedro steals the key when the player gets back to town after having taken the key
     * @param cell the cell
     * @param isCellInteraction
     */
    @Override
    public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
        if (
                isCellInteraction
                && cell.getWalkingType().equals(ICMonBehavior.AllowedWalkingType.ENTER_WATER)
                && !keyHasBeenStolen
        ) {
            keyHasBeenStolen = true;
            soundManager.playSound("pedro", 200, true);
            getPlayer().openDialog(new Dialog("pedro_steal_key"));
        }
    }

    @Override
    public void interactWith(Pedro pedro, boolean isCellInteraction) {
        if (getPlayer().wantsEntityViewInteraction()) {
            Pokemon pedroPokemon = pedro.getPokemons().get(0);
            getPlayer().fight(
                    pedroPokemon,
                    pedro, // the one that will disappear if the fight is a win
                    new CompleteEventAction(this), // the event that will be completed if the fight is a win
                    new DelayedAction(
                            new OpenDialogAction(this.getPlayer(), new Dialog("pedro_fight_end_lose")),
                            2000
                    )
            );
        }
    }
}
