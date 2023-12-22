package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Actor;

/**
 * Represents an action that makes an actor register in an area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class RegisterInAreaAction implements Action {

    final private Area area;
    final private Actor actor;

    /**
     * Creates a new register in area action.
     *
     * @param area the area to register in
     * @param actor the actor to register
     */
    public RegisterInAreaAction(Area area, Actor actor) {
        assert area != null;
        assert actor != null;
        this.area = area;
        this.actor = actor;
    }

    @Override
    public void perform() {
        area.registerActor(actor);
    }
}
