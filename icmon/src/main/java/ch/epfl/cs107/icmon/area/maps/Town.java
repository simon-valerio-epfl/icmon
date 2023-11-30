package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * ???
 */
public final class Town extends ICMonArea {

    private ICMonEvent event;

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5, 5);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));

        ICBall ball = new ICBall(this, new DiscreteCoordinates(6, 6));
        registerActor(ball);

        event = new CollectItemEvent(ball);

        event.onStart(new LogAction("CollectItemEvent started !"));
        event.onComplete(new LogAction("CollectItemEvent completed !"));

        event.start();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        event.update(deltaTime);
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