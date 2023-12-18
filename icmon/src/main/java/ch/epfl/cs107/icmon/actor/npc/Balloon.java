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
    private BalloonStep[] stepsLeft = {
            new BalloonStep(Orientation.RIGHT, 3),
            new BalloonStep(Orientation.UP, 4),
            new BalloonStep(Orientation.RIGHT, 5),
            new BalloonStep(Orientation.UP, 9),
            new BalloonStep(Orientation.RIGHT, 7),
            new BalloonStep(Orientation.UP, 4),
    };
    private boolean hasBeenReset = false;

    public Balloon (ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition, SPRITE_NAME, 3, 64, 64);
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
                orientate(stepsLeft[0].orientation);
                move(12);

                if (stepsLeft[0].applyNextStep()) {
                    if (stepsLeft.length > 1) {
                        stepsLeft = Arrays.copyOfRange(stepsLeft, 1, stepsLeft.length);
                    } else {
                        stepsLeft = new BalloonStep[]{
                                new BalloonStep(Orientation.RIGHT, 3),
                                new BalloonStep(Orientation.UP, 4),
                                new BalloonStep(Orientation.RIGHT, 5),
                                new BalloonStep(Orientation.UP, 9),
                                new BalloonStep(Orientation.RIGHT, 6),
                                new BalloonStep(Orientation.UP, 4),
                        };
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

    class BalloonStep {
        private Orientation orientation;
        private int stepCountLeft;

        private BalloonStep(Orientation orientation, int stepCount) {
            this.orientation = orientation;
            this.stepCountLeft = stepCount;
        }

        public boolean applyNextStep () {
            stepCountLeft--;
            return stepCountLeft == 0;
        }
    }
}
