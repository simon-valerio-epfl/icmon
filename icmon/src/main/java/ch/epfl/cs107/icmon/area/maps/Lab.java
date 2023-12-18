package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * ???
 */
public final class Lab extends ICMonArea {

    final static DiscreteCoordinates SPAWNING_POSITION = new DiscreteCoordinates(6, 2);

    /**
     *
     * @return some default spawning coordinates on this area, namely (6,2)
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return SPAWNING_POSITION;
    }

    /**
     * it adds a door taking back to the main Area, town
     * this area is created with a character, ProfOak,
     * at some default conditions namely (11, 7)
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        Door doorToTown = new Door("town", new DiscreteCoordinates(15, 23), this, new DiscreteCoordinates(6, 1), new DiscreteCoordinates(7, 1));
        registerActor(doorToTown);

        ProfOak profOak = new ProfOak(this, Orientation.DOWN, new DiscreteCoordinates(11,7));
        registerActor(profOak);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     *
     * @return the name of this area
     */
    @Override
    public String getTitle() {
        return "lab";
    }

}