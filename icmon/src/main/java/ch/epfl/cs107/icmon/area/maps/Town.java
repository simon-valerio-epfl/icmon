package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.Gift;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMagicBall;
import ch.epfl.cs107.icmon.actor.npc.Balloon;
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
    final static DiscreteCoordinates SPAWNING_POSITION = new DiscreteCoordinates(20,12);


    /**
     *
     * @return some default spawning coordinates on this area, namely (20, 12)
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return SPAWNING_POSITION;
    }

    /**
     * Town is the main area of the game
     * Creating it adds doors going to the every other area
     *
     * this area is created with a character, a shop assistant,
     * at some default conditions namely (6,8) and some other items belonging to the plot
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        ICShopAssistant assistant = new ICShopAssistant(this, Orientation.DOWN, new DiscreteCoordinates(8, 8));
        assistant.enterArea(this, new DiscreteCoordinates(6, 8));

        Door doorToLab = new Door("lab", new DiscreteCoordinates(6, 2), this, new DiscreteCoordinates(15, 24));
        registerActor(doorToLab);
        Door doorToArena = new Door("arena", new DiscreteCoordinates(4, 2), this, new DiscreteCoordinates(20, 16));
        registerActor(doorToArena);
        Door doorToHouse = new Door("house", new DiscreteCoordinates(2,2), this, new DiscreteCoordinates(7,27));registerActor(doorToArena);
        registerActor(doorToHouse);
        Door doorToShop = new Door("shop", new DiscreteCoordinates(3,2), this, new DiscreteCoordinates(25,20));
        registerActor(doorToShop);

        Balloon balloon = new Balloon(this, Orientation.UP, new DiscreteCoordinates(10, 11));
        registerActor(balloon);

        ICMagicBall magicBall = new ICMagicBall(this, new DiscreteCoordinates(4, 12));
        registerActor(magicBall);

        Gift gift = new Gift(this, new DiscreteCoordinates(25, 14));
        registerActor(gift);
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
        return "town";
    }

}