package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;

import java.util.Arrays;
import java.util.LinkedList;

public class ICMonChainedEvent extends ICMonEvent{

    final private ICMonEvent initialEvent;
    private LinkedList<ICMonEvent> eventList;

    ICMonChainedEvent(ICMonPlayer player, ICMonEvent initialEvent, ICMonEvent ... chain){
        super(player);
        this.initialEvent = initialEvent;
        this.eventList = new LinkedList<>(Arrays.asList(chain));

        this.startNextEvent();

    }





}
