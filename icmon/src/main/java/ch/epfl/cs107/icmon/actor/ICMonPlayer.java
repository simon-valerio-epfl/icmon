package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.icmon.actor.pokemon.Nidoqueen;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
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
    final private ICMon.ICMonEventManager eventManager;
    private Dialog dialog;
    private boolean inDialog = false;
    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    final private Area spawningArea;

    public ICMonPlayer(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        super(area, orientation, spawnPosition);
        this.swimmingOrientedAnimation = new OrientedAnimation(SPRITE_SWIMMING_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.runningOrientedAnimation = new OrientedAnimation(SPRITE_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.gameState = gameState;
        this.eventManager = eventManager;

        this.spawningArea = area;
    }

    public void giftPokemon(String pokemonName) {
        switch (pokemonName) {
            case "latios" -> {
                this.pokemons.add(new Latios(spawningArea, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case "bulbizarre" -> {
                this.pokemons.add(new Bulbizarre(spawningArea, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            case "nidoqueen" -> {
                this.pokemons.add(new Nidoqueen(spawningArea, Orientation.UP, new DiscreteCoordinates(0, 0)));
            }
            default -> {

            }
        }
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
        if (this.inDialog) {
            this.dialog.draw(canvas);
        }
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

        if (this.inDialog) {
            Keyboard keyboard = getOwnerArea().getKeyboard();
            if (keyboard.get(Keyboard.SPACE).isDown()){
                this.dialog.update(deltaTime);
            }
            if (this.dialog.isCompleted()) {
               this.dialog = null;
               this.inDialog = false;
            }
        } else {
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
        return lKey.isDown() && !this.inDialog;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
        this.gameState.acceptInteraction(other, isCellInteraction);
    }

    public void openDialog (Dialog dialog) {
        this.dialog = dialog;
        this.inDialog = true;
    }

    public void fight(ICMonFightableActor actor) {
        if (!(actor instanceof Pokemon)) {
            System.out.println("Something bad is happening. WHAT HAVE YOU CREATED?");
            return;
        }
        ICMonFight ourFight = new ICMonFight(this.pokemons.get(0), (Pokemon) actor);
        PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(eventManager, this, ourFight);
        this.gameState.createSuspendWithEventMessage(pokemonFightEvent);

        pokemonFightEvent.onComplete(new LeaveAreaAction((ICMonActor) actor));
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

        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction) {
                gameState.createPassDoorMessage(door);
            }
        }

        @Override
        public void interactWith(ICMonFightableActor actor, boolean isCellInteraction) {
            if (isCellInteraction) {
                fight(actor);
            }
        }
    }
}
