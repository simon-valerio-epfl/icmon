// todo final fabrice ?

package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
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
    // todo est-ce que c'est vraiment une bonne idée de faire ça en terme d'encapsulation ?
    final protected ICMonPlayer player;

    public ICMonEvent (ICMonPlayer player) {
        this.player = player;
    }

    public void start() {
        if (!started) {
            onStartActions.forEach(Action::perform);
            started = true;
        }
    }

    public void complete() {
        if (started && !completed) {
            onCompleteActions.forEach(Action::perform);
            completed = true;
        }
    }

    public void suspend() {
        if (started && !completed && !suspended) {
            onSuspensionActions.forEach(Action::perform);
            suspended = true;
        }
    }

    public void resume() {
        if (started && !completed && suspended) {
            onResumeActions.forEach(Action::perform);
            suspended = false;
        }
    }

    public void onStart(Action action) {
        onStartActions.add(action);
    }

    public void onComplete(Action action) {
        onCompleteActions.add(action);
    }

    public void onSuspension(Action action) {
        onSuspensionActions.add(action);
    }

    public void onResume(Action action) {
        onResumeActions.add(action);
    }

    public boolean isStarted () {
        return this.started;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public boolean hasPauseMenu() { return false; }
    public PauseMenu getPauseMenu() { return null; }

}