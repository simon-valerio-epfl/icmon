package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents an event that includes a bus of events that will be executed in order.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMonChainedEvent extends ICMonEvent {

    /**
     * It creates an ordered list of events that will autonomously start
     * the one following them on completion
     *
     * @param eventManager the event manager to register the event
     * @param player the player that will interact with the events
     * @param initialEvent the first event, it will start automatically
     * @param chain the other events, in the wanted order
     */
    public ICMonChainedEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonEvent initialEvent, ICMonEvent ... chain){
        super(eventManager, player);

        initialEvent.start();
        if (chain.length == 1) {
            initialEvent.onComplete(new CompleteEventAction(this));
        } else {
            initialEvent.onComplete(new StartEventAction(chain[0]));

            for (int i = 0; i < chain.length - 1; i++) {
                chain[i].onComplete(new StartEventAction(chain[i+1]));
            }

            chain[chain.length - 1].onComplete(new CompleteEventAction(this));
        }
    }

    @Override
    public void update(float deltaTime) {}

}
