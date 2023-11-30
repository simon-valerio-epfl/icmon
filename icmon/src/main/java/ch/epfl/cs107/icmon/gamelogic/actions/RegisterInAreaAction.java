package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Actor;

public class RegisterInAreaAction implements Action {

    final private Area area;
    final private Actor actor;

    public RegisterInAreaAction(Area area, Actor actor) {
        this.area = area;
        this.actor = actor;
    }

    public void perform() {
        area.registerActor(actor);
    }
}
