package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.icmon.gamelogic.actions.DelayedAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

/**
 * Represents a firework that will explode after a certain amount of time.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Firework extends NPCActor {
    private final static String SPRITE_NAME = "actors/firework";
    private int timeLeftBeforeExplosion = 50;
    private boolean hasBeenTriggered = false;
    private boolean hasBeenRemoved = false;
    private final int framePerMove;
    private final ICMonSoundManager soundManager;

    public Firework(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMonSoundManager soundManager) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 3, 3, 64, 64);
        this.soundManager = soundManager;
        // calculate speed based on where the firework spawns
        this.framePerMove = spawnPosition.y * 4 / 7 + 8;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {}

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // play sound!
        if (!hasBeenTriggered) {
            soundManager.playOverlappingSound("firework");
            hasBeenTriggered = true;
        }

        // a firework always go from bottom to top
        orientate(Orientation.UP);
        if (timeLeftBeforeExplosion > 0) {
            move(framePerMove);
            timeLeftBeforeExplosion--;
        }

        // explose!
        if (timeLeftBeforeExplosion == 0 && !hasBeenRemoved) {
            this.setSpriteName(ResourcePath.getSprite("actors/firework_explosion"));
            soundManager.playOverlappingSound("firework_explosion");

            hasBeenRemoved = true;
            new DelayedAction(new LeaveAreaAction(this), 2000).perform();
        }

    }
}
