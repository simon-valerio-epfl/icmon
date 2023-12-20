package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Represents a balloon that shows the player the way to the gift.
 */
public class Balloon extends NPCActor {
    final private static String SPRITE_NAME = "actors/balloon";
    final private static int BALLOON_ANIMATION_SPEED = 12;

    // automated moves handling
    private final BalloonMove[] steps = {
            new BalloonMove(Orientation.RIGHT, 3),
            new BalloonMove(Orientation.UP, 3),
            new BalloonMove(Orientation.RIGHT, 5),
            new BalloonMove(Orientation.UP, 8),
            new BalloonMove(Orientation.RIGHT, 6),
            new BalloonMove(Orientation.UP, 4),
    };
    private int currentStepIdx = 0;
    private boolean justGotReset = false;

    /**
     * Creates a new balloon in the specified area
     *
     * @param area the area where the balloon shall spawn
     * @param orientation the orientation of the balloon
     * @param spawnPosition the position where the balloon shall spawn
     */
    public Balloon (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 3, 3, 64, 64);
    }

    /**
     * Gets the spawn position of the balloon in the spawning area
     * @return the spawn position of the balloon
     */
    public static DiscreteCoordinates getSpawnPosition() {
        return new DiscreteCoordinates(5, 7);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!isDisplacementOccurs() && !justGotReset) {

            orientate(steps[currentStepIdx].orientation);
            move(BALLOON_ANIMATION_SPEED);

            // apply the step, and check whether it is ended
            if (steps[currentStepIdx].updateNextStep()) {

                // if there are more steps to perform
                if (steps.length > currentStepIdx + 1) {
                    currentStepIdx++;
                    steps[currentStepIdx].reset();
                }

                // if the sequence is ended, reset the balloon
                // to its original position
                else {
                    currentStepIdx = 0;
                    justGotReset = true;
                    CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
                        setCurrentPosition(getSpawnPosition().toVector());
                        justGotReset = false;
                    });
                }
            }
        }

    }

    /**
     * Represents a sequence of moves in a specific direction.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    static class BalloonMove {
        final private Orientation orientation;
        final private int initialStepCountLeft;
        private int currentStepCountLeft;

        /**
         * Creates a new balloon move with the specified orientation and step count
         *
         * @param orientation the orientation of the move
         * @param stepCount the number of steps to perform in the specified direction
         */
        private BalloonMove(Orientation orientation, int stepCount) {
            this.orientation = orientation;
            this.initialStepCountLeft = stepCount;
            this.currentStepCountLeft = stepCount;
        }

        /**
         * Decrements the number of left steps
         * @return whether this move is ended
         */
        public boolean updateNextStep () {
            if (currentStepCountLeft > 0) {
                currentStepCountLeft--;
                return false;
            } else {
                currentStepCountLeft = initialStepCountLeft;
                return true;
            }
        }

        /**
         * Resets the move so the balloon can perform it again later
         */
        public void reset() {
            currentStepCountLeft = initialStepCountLeft;
        }
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {}

    @Override
    public boolean takeCellSpace() {
        return false;
    }
}
