package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.*;
import ch.epfl.cs107.icmon.actor.npc.*;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

/**
 * Represents an entity's ability to interact with other entities in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public interface ICMonInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {

    }

    default void interactWith(ICMonPlayer player, boolean isCellInteraction) {

    }


    default void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {

    }

    default void interactWith(Door door, boolean isCellInteraction) {

    }

    default void interactWith(Pokemon pokemon, boolean isCellInteraction) {

    }

    default void interactWith(ProfOak profOak, boolean isCellInteraction) {

    }

    default void interactWith(Garry garry, boolean isCellInteraction) {

    }

    default void interactWith(Balloon balloon, boolean isCellInteraction) {

    }

    default void interactWith(ICGift gift, boolean isCellInteraction) {

    }

    default void interactWith(Fabrice fabrice, boolean isCellInteraction) {

    }

    default void interactWith(ICMonItem item, boolean isCellInteraction) {

    }

    default void interactWith(Pedro pedro, boolean isCellInteraction) {

    }

}
