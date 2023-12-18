// todo final fabrice ?

package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;

public abstract class ICMonEvent implements Updatable, ICMonInteractionVisitor {

    private boolean started = false;
    private boolean completed = false;
    private boolean suspended = false;
    private ArrayList<Action> onStartActions = new ArrayList<>();
    private ArrayList<Action> onCompleteActions = new ArrayList<>();
    private ArrayList<Action> onSuspensionActions = new ArrayList<>();
    private ArrayList<Action> onResumeActions = new ArrayList<>();
    final private ICMonPlayer player;
    final private ICMon.ICMonEventManager eventManager;

    /**
     * The current event will register and unregister the relative actions
     * when it starts and finishes
     * @param eventManager
     * @param player the main player of the game
     */
    public ICMonEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        this.player = player;
        this.eventManager = eventManager;

        this.onStart(new RegisterEventAction(this, eventManager));
        this.onComplete(new UnRegisterEventAction(this, eventManager));
    }

    /**
     * It performs the actions related to the start of this event
     * It won't work if it has already started once
     */
    public void start() {
        if (!started) {
            onStartActions.forEach(Action::perform);
            started = true;
        }
    }

    /**
     * It performs the actions related to the completion of this event
     * It won't work if it is already completed or not yet started
     */
    public void complete() {
        if (started && !completed) {
            onCompleteActions.forEach(Action::perform);
            completed = true;
        }
    }

    /**
     * It performs the actions related to the suspension of this event
     * It won't work if it is already completed, or suspended or not yet started
     */
    public void suspend() {
        if (started && !completed && !suspended) {
            onSuspensionActions.forEach(Action::perform);
            suspended = true;
        }
    }

    /**
     * It performs the actions related to the resuming of this event
     * It won't work if it is already completed, or not yet started or suspended
     */
    public void resume() {
        if (started && !completed && suspended) {
            onResumeActions.forEach(Action::perform);
            suspended = false;
        }
    }

    /**
     *
     * @param action to add to the list of actions to perform on the event's starting
     */
    public void onStart(Action action) {
        onStartActions.add(action);
    }

    /**
     *
     * @param action to add to the list of actions to perform on the event's completion
     */
    public void onComplete(Action action) {
        onCompleteActions.add(action);
    }

    /**
     *
     * @param action to add to the list of actions to perform on the event's suspension
     */
    public void onSuspension(Action action) {
        onSuspensionActions.add(action);
    }

    /**
     *
     * @param action to add to the list of actions to perform on the event's resuming
     */
    public void onResume(Action action) {
        onResumeActions.add(action);
    }

    /**
     *
     * @return whether this event has already started once
     */
    public boolean isStarted () {
        return this.started;
    }

    /**
     *
     * @return whether this event has already completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     *
     * @return whether this event is already suspended
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * An event has no pause menu by default
     * one should feel free to override this method
     * @return whether this event has a pause menu
     */
    public boolean hasPauseMenu() { return false; }

    /**
     * An event has no pause menu by default
     * one should feel free to override this method
     * @return this event's pause menu
     */
    public PauseMenu getPauseMenu() { return null; }

    /**
     *
     * @return the main player associated to this event
     */
    protected ICMonPlayer getPlayer() {
        return player;
    }

    protected ICMon.ICMonEventManager getEventManager() { return eventManager; }

}