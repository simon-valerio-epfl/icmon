package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMagicBall;
import ch.epfl.cs107.icmon.actor.npc.Balloon;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

public interface ICMonInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {

    }

    default void interactWith(ICMonPlayer player, boolean isCellInteraction) {

    }

    default void interactWith(ICBall ball, boolean isCellInteraction) {

    }

    default void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {

    }

    default void interactWith(Door door, boolean isCellInteraction) {

    }

    /*
    default void interactWith(Bulbizarre bulbizarre, boolean isCellInteraction) {

    }*/

    default void interactWith(ICMonFightableActor actor, boolean isCellInteraction) {

    }

    default void interactWith(ProfOak profOak, boolean isCellInteraction) {

    }

    default void interactWith(Garry garry, boolean isCellInteraction) {

    }

    default void interactWith(AreaBehavior.Cell cell, boolean isCellInteraction) {

    }

    default void interactWith(Balloon balloon, boolean isCellInteraction) {

    }

    default void interactWith(ICMagicBall magicBall, boolean isCellInteraction) {

    }

    default void interactWith(ICGift gift, boolean isCellInteraction) {

    }

}
