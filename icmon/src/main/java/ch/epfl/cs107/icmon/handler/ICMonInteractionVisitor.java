package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

public interface ICMonInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {

    }

    default void interactWith(ICMonPlayer player, boolean isCellInteraction) {

    }

    default void interactWith(ICBall ball, boolean isCellInteraction) {

    }
}
