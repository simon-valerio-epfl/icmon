package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class ICMonPlayer extends ICMonActor implements Interactor {

    private enum SpriteType { SWIMMING_SPRITE, RUNNING_SPRITE };

    final private static String SPRITE_NAME = "actors/player";
    final private static String SPRITE_SWIMMING_NAME = "actors/player_water";
    final private static int ANIMATION_DURATION = 8;
    final private static int MOVE_DURATION = 8;
    private OrientedAnimation swimmingOrientedAnimation;
    private OrientedAnimation runningOrientedAnimation;
    private SpriteType currentSprite = SpriteType.RUNNING_SPRITE;
    final private ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();
    final private ICMon.ICMonGameState gameState;

    public ICMonPlayer(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMon.ICMonGameState gameState) {
        super(area, orientation, spawnPosition);
        this.swimmingOrientedAnimation = new OrientedAnimation(SPRITE_SWIMMING_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.runningOrientedAnimation = new OrientedAnimation(SPRITE_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.gameState = gameState;

    }

    public OrientedAnimation getCurrentOrientedAnimation () {
        switch (this.currentSprite) {
            case SWIMMING_SPRITE:
                return swimmingOrientedAnimation;
            case RUNNING_SPRITE:
            default:
                return runningOrientedAnimation;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        this.getCurrentOrientedAnimation().orientate(getOrientation());
        this.getCurrentOrientedAnimation().draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        if (isDisplacementOccurs()) {
            this.getCurrentOrientedAnimation().update(deltaTime);
        } else {
            this.getCurrentOrientedAnimation().reset();
        }
    }

    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button lKey = keyboard.get(Keyboard.L);
        return lKey.isDown();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
        this.gameState.acceptInteraction(other, isCellInteraction);
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (cell.getWalkingType()) {
                    case FEET -> {
                        currentSprite = SpriteType.RUNNING_SPRITE;
                    }
                    case SURF -> {
                        currentSprite = SpriteType.SWIMMING_SPRITE;
                    }
                    default -> {
                        // do nothing
                    }
                }
            }
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            if (!isCellInteraction && wantsViewInteraction()) {
                ball.collect();
            }
        }
    }
}
