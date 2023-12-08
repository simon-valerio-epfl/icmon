package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
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
public final class Town extends ICMonArea {

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        //return new DiscreteCoordinates(5, 5);
        return new DiscreteCoordinates(20, 12);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        ICShopAssistant assistant = new ICShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(8, 8));
        assistant.enterArea(this, new DiscreteCoordinates(8, 8));

        Door doorToLab = new Door("lab", new DiscreteCoordinates(6, 2), this, new DiscreteCoordinates(15, 24));
        registerActor(doorToLab);
        Door doorToArena = new Door("arena", new DiscreteCoordinates(4, 2), this, new DiscreteCoordinates(20, 16));
        registerActor(doorToArena);
        Door doorToHouse = new Door("house", new DiscreteCoordinates(2,2), this, new DiscreteCoordinates(7,27));registerActor(doorToArena);
        registerActor(doorToHouse);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "town";
    }

}