package ch.epfl.cs107.icmon.gamelogic.events.choco_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class GiveKeyFabrice extends ICMonEvent {

    public GiveKeyFabrice(ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(eventManager, player);
    }

    @Override
    public void update(float deltaTime) {

    }
}
