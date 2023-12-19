package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Dialog;

public class FightPedroEvent extends ICMonEvent {

    private boolean keyHasBeenStolen = false;
    private ICMon.ICMonSoundManager soundManager;

    public FightPedroEvent(ICMon.ICMonEventManager eventManager, ICMon.ICMonSoundManager soundManager, ICMonPlayer player) {
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

            soundManager.playSound("pedro", 200, false);
            getPlayer().openDialog(new Dialog("pedro_steal_key"));
        }
    }
}
