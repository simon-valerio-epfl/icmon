package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICKey;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.*;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.gamelogic.actions.AfterPokemonSelectionFightAction;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.PokemonSelectionEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonSelectionMenu;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ICMonPlayer extends ICMonActor implements Interactor, PokemonOwner {

    private enum SpriteType { SWIMMING_SPRITE, RUNNING_SPRITE, UNDERWATER_SPRITE };

    final private static String SPRITE_NAME = "actors/player";
    final private static String SPRITE_SWIMMING_NAME = "actors/player_water";
    final private static String SPRITE_UNDERWATER_NAME = "actors/player_underwater";
    final private static int ANIMATION_DURATION = 8;
    final private static int MOVE_DURATION = 8;
    private OrientedAnimation swimmingOrientedAnimation;
    private OrientedAnimation runningOrientedAnimation;
    private OrientedAnimation underWaterOrientedAnimation;
    private SpriteType currentSprite = SpriteType.RUNNING_SPRITE;
    final private ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();
    final private ICMon.ICMonGameState gameState;
    final private ICMon.ICMonEventManager eventManager;
    private ICMon.ICMonSoundManager soundManager;
    private Dialog dialog;
    private boolean inDialog = false;
    final private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private boolean blockNextMove = false;
    private boolean lastOrientationChecked = false;
    private boolean isDiver = false;
    private boolean dialogIsLocked = false;
    private boolean muteWalkingSound = false;

    //todo document this class
    public ICMonPlayer(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMon.ICMonSoundManager soundManager) {
        super(area, orientation, spawnPosition);
        this.swimmingOrientedAnimation = new OrientedAnimation(SPRITE_SWIMMING_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.runningOrientedAnimation = new OrientedAnimation(SPRITE_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.underWaterOrientedAnimation = new OrientedAnimation(SPRITE_UNDERWATER_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.gameState = gameState;
        this.eventManager = eventManager;
        this.soundManager = soundManager;

        // todo remove this
        addPokemon("bulbizarre");
        addPokemon("latios");
        addPokemon("nidoqueen");

        /*blockedCoordinates.put(Orientation.LEFT, false);
        blockedCoordinates.put(Orientation.RIGHT, false);
        blockedCoordinates.put(Orientation.UP, false);
        blockedCoordinates.put(Orientation.DOWN, false);*/
    }

    @Override
    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public OrientedAnimation getCurrentOrientedAnimation () {
        switch (this.currentSprite) {
            case SWIMMING_SPRITE:
                return swimmingOrientedAnimation;
            case UNDERWATER_SPRITE:
                return underWaterOrientedAnimation;
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
            if (keyboard.get(Keyboard.SPACE).isDown() && !dialogIsLocked){
                this.dialog.update(deltaTime);
                dialogIsLocked = true;
                CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS).execute(() -> {
                    dialogIsLocked = false;
                });
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
                if (!getOrientation().equals(orientation)) {
                    lastOrientationChecked = false;
                }
                orientate(orientation);
                if (!blockNextMove && lastOrientationChecked) {
                    move(MOVE_DURATION-5);
                    if (!getMuteWalkingSound()) {
                        if (
                                currentSprite.equals(SpriteType.SWIMMING_SPRITE)
                                || currentSprite.equals(SpriteType.UNDERWATER_SPRITE)
                        ) {
                            soundManager.playSound("swimming", 10);
                        } else {
                            soundManager.playSound("footsteps", 10);
                        }
                    }
                }
                lastOrientationChecked = true;
            }
        }
    }

    public void setMuteWalkingSound(boolean muteWalkingSound) {
        this.muteWalkingSound = muteWalkingSound;
    }

    public boolean getMuteWalkingSound() {
        return muteWalkingSound;
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
        return true;
    }

    public boolean wantsRealViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button lKey = keyboard.get(Keyboard.L);
        return lKey.isDown() && !this.inDialog;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
        this.gameState.acceptInteraction(other, isCellInteraction);
    }

    public boolean wantsUnderWater() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button wKey = keyboard.get(Keyboard.W);
        return wKey.isDown() && !this.inDialog;
    }

    public void openDialog (Dialog dialog) {
        this.dialog = dialog;
        this.inDialog = true;
    }

    public void suspendGameWithFightEvent(PokemonFightEvent pokemonFightEvent) {
        this.gameState.createSuspendWithEventMessage(pokemonFightEvent);
    }

    public void fight(ICMonFightableActor fightable, ICMonActor fightableOwner) {
        if (!(fightable instanceof Pokemon)) {
            System.out.println("Something bad is happening. WHAT HAVE YOU CREATED?");
            return;
        }

        if (this.pokemons.isEmpty()) {
            this.orientate(Orientation.DOWN);
            this.move(1);
            this.openDialog(new Dialog("no_pokemon"));
        } else {

            PokemonSelectionMenu pokemonSelectionMenu = new PokemonSelectionMenu(this);
            PokemonSelectionEvent pokemonSelectionEvent = new PokemonSelectionEvent(eventManager, this, pokemonSelectionMenu);
            this.gameState.createSuspendWithEventMessage(pokemonSelectionEvent);

            pokemonSelectionEvent.onComplete(new AfterPokemonSelectionFightAction(this, this.eventManager, pokemonSelectionMenu, (Pokemon) fightable, fightableOwner));
        }

    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            if (isCellInteraction) {
                switch (cell.getWalkingType()) {
                    case FEET_OR_UNDERWATER -> {
                        // if the previous sprite was swimming
                        // and the rights key is pressed
                        // go underwater!
                        if (currentSprite.equals(SpriteType.SWIMMING_SPRITE)) {
                            if (wantsUnderWater()) {
                                if (isDiver) {
                                    gameState.transferToAtlantis();
                                } else {
                                    currentSprite = SpriteType.UNDERWATER_SPRITE;
                                }
                            } else {
                                currentSprite = SpriteType.RUNNING_SPRITE;
                            }
                        }
                    }
                    case FEET -> {
                        currentSprite = SpriteType.RUNNING_SPRITE;
                    }
                    case SURF, ENTER_WATER -> {
                        currentSprite = SpriteType.SWIMMING_SPRITE;
                    }
                    default -> {
                        // do nothing
                    }
                }
            } else {
                // if the player is swimming
                // todo check
                blockNextMove = (
                        !cell.getWalkingType().equals(ICMonBehavior.AllowedWalkingType.FEET_OR_UNDERWATER)
                        && !cell.getWalkingType().equals(ICMonBehavior.AllowedWalkingType.ENTER_WATER)
                ) && currentSprite.equals(SpriteType.UNDERWATER_SPRITE);
            }
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            if (!isCellInteraction && wantsRealViewInteraction()) {
                ball.collect();
                soundManager.playSound("collect", 100);
            }
        }

        @Override
        public void interactWith(ICKey key, boolean isCellInteraction) {
            if (!isCellInteraction && wantsRealViewInteraction()) {
                key.collect();
                soundManager.playSound("collect", 100);
            }
        }

        @Override
        public void interactWith(ICGift gift, boolean isCellInteraction) {
            if (!isCellInteraction && wantsRealViewInteraction()) {
                gift.collect();
                soundManager.playSound("collect", 100, false);
                openDialog(new Dialog("collect_gift"));
                // todo change skin of the player
                isDiver = true;
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
                fight(actor, null);
            }
        }

        @Override
        public void interactWith(ProfOak profOak, boolean isCellInteraction) {
            if (!isCellInteraction && wantsRealViewInteraction()) {
                soundManager.playSound("npc", 20, false);
            }
        }
    }
}
