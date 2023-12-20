package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class Firework extends NPCActor {
    final private static String SPRITE_NAME = "actors/firework";
    private int timeBeforeExplosion = 50;
    private final ICMonSoundManager soundManager;

    public Firework(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMonSoundManager soundManager) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 3, 3, 64, 64);
        this.soundManager = soundManager;

        soundManager.playOverlappingSound("firework");
    }

    public static DiscreteCoordinates getSpawnPosition() {
        return new DiscreteCoordinates(20, 7);
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

        orientate(Orientation.UP);
        if (timeBeforeExplosion > 0) {
            move(8);
        }

        if (timeBeforeExplosion == 0) {
            this.setSpriteName("actors/firework_explosion");
            soundManager.playOverlappingSound("firework_explosion");
        }

        timeBeforeExplosion--;
    }
}
