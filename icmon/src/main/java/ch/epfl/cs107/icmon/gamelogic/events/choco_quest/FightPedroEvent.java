package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Pedro;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FightPedroEvent extends ICMonEvent {

    private final boolean hasMetPedro = false;
    private boolean keyHasBeenStolen = false;
    private final ICMonSoundManager soundManager;

    public FightPedroEvent(ICMon.ICMonEventManager eventManager, ICMonSoundManager soundManager, ICMonPlayer player) {
        super(eventManager, player);
        this.soundManager = soundManager;
    }

    @Override
    public void update(float deltaTime) {

    }

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
                    this // the event that will be completed if the fight is a win
            );
        }
    }
}
