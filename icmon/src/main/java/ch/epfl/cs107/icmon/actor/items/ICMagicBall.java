package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class ICMagicBall extends ICMonItem {
    final private static String SPRITE_NAME = "items/icmagicball_big";
    final private static String SPAWNING_AREA = "town";
    public ICMagicBall(ICMonArea area, DiscreteCoordinates spawnPosition) {
        super(area, spawnPosition, SPRITE_NAME);
    }

    static public String getSpawningArea() {
        return SPAWNING_AREA;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
