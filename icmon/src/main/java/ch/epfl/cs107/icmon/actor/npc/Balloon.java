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

public class Balloon extends NPCActor {
    final private static String SPRITE_NAME = "actors/balloon";
    private BalloonStep[] steps = {
            new BalloonStep(Orientation.RIGHT, 3),
            new BalloonStep(Orientation.UP, 4),
            new BalloonStep(Orientation.RIGHT, 5),
            new BalloonStep(Orientation.UP, 9),
            new BalloonStep(Orientation.RIGHT, 6),
            new BalloonStep(Orientation.UP, 4),
    };

    private int currentStepIdx = 0;
    private boolean hasBeenReset = false;

    public Balloon (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 3, 3, 64, 64);
    }

    public static DiscreteCoordinates getSpawnPosition() {
        return new DiscreteCoordinates(5, 6);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector())))) {
            if (!isDisplacementOccurs() && !hasBeenReset) {
                orientate(steps[currentStepIdx].orientation);
                move(12);

                if (steps[currentStepIdx].applyNextStep()) {
                    if (steps.length > currentStepIdx + 1) {
                        currentStepIdx++;
                        steps[currentStepIdx].reset();
                    } else {
                        currentStepIdx = 0;
                        hasBeenReset = true;
                        CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
                            setCurrentPosition(getSpawnPosition().toVector());
                            hasBeenReset = false;
                        });
                    }
                }
            }
        }

    }

    static class BalloonStep {
        final private Orientation orientation;
        final private int initialStepCountLeft;
        private int currentStepCountLeft;

        private BalloonStep(Orientation orientation, int stepCount) {
            this.orientation = orientation;
            this.initialStepCountLeft = stepCount;
            this.currentStepCountLeft = stepCount;
        }

        public boolean applyNextStep () {
            if (currentStepCountLeft > 0) {
                currentStepCountLeft--;
                return false;
            } else {
                currentStepCountLeft = initialStepCountLeft;
                return true;
            }
        }

        public void reset() {
            currentStepCountLeft = initialStepCountLeft;
        }
    }
}
