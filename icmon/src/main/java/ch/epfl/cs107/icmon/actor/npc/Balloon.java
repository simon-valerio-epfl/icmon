package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Represents a balloon that shows the player the way to the gift.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Balloon extends NPCActor {
    final private static String SPRITE_NAME = "actors/balloon";
    final private static int BALLOON_ANIMATION_SPEED = 12;
    final private static int SECONDS_BEFORE_RESET = 5;

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

    /**
     * Makes the balloon follow its sequence of moves
     * @param deltaTime the time elapsed since the last update
     */
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
                    CompletableFuture.delayedExecutor(SECONDS_BEFORE_RESET, TimeUnit.SECONDS).execute(() -> {
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
            assert stepCount>0;
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
         * Resets the movement letting the balloon perform it again later
         */
        public void reset() {
            currentStepCountLeft = initialStepCountLeft;
        }
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {}

    /**
     * Some other entity may be able to enter the cell where the balloon is
     * @return always false
     */
    @Override
    public boolean takeCellSpace() {
        return false;
    }
}
