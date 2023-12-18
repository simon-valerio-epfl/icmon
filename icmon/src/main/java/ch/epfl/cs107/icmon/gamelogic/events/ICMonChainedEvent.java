package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;

import java.util.Arrays;
import java.util.LinkedList;

public class ICMonChainedEvent extends ICMonEvent{

    final private ICMonEvent currentEvent;


    /**
     * It creates an ordered list of events that will autonomously start
     * the one following them on completion
     * @param eventManager
     * @param player
     * @param initialEvent the first event, it will start automatically
     * @param chain the other events, in the wanted order
     */
    public ICMonChainedEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonEvent initialEvent, ICMonEvent ... chain){
        super(eventManager, player);
        LinkedList<ICMonEvent> eventList = new LinkedList<>(Arrays.asList(chain));

        this.currentEvent = initialEvent;
        this.currentEvent.start();
        this.currentEvent.onComplete(new StartEventAction(eventList.get(0)));

        for (int i = 0; i < eventList.size() - 1; i++) {
            eventList.get(i).onComplete(new StartEventAction(eventList.get(i+1)));
        }

        eventList.getLast().onComplete(new CompleteEventAction(this));
    }

    /**
     * updates the current event, it will manage
     * the happening interactions
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        this.currentEvent.update(deltaTime);
    }

}
