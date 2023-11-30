package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
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

    final private static String SPRITE_NAME = "actors/player";
    final private static int ANIMATION_DURATION = 8;
    final private static int MOVE_DURATION = 8;
    private OrientedAnimation orientedAnimation;

    public ICMonPlayer(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition) {
        super(area, orientation, spawnPosition);
        this.orientedAnimation = new OrientedAnimation(SPRITE_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
    }

    @Override
    public void draw(Canvas canvas) {
        orientedAnimation.orientate(this.getOrientation());
        orientedAnimation.draw(canvas);
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
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        if (isDisplacementOccurs()) {
            orientedAnimation.update(deltaTime);
        } else {
            orientedAnimation.reset();
        }
        super.update(deltaTime);
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
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button lKey = keyboard.get(Keyboard.L);
        return lKey.isDown();
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                
            }
        }
    }
}
