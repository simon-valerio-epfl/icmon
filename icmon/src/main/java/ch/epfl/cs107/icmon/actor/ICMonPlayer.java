package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.PokemonOwner;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.gamelogic.actions.AfterPokemonSelectionFightAction;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonSelectionEvent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Represents the main player in the game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMonPlayer extends ICMonActor implements Interactor, PokemonOwner {

    // Sprites' management
    private enum SpriteType { SWIMMING_SPRITE, RUNNING_SPRITE, UNDERWATER_SPRITE }
    final private static String SPRITE_NAME = "actors/player";
    final private static String SPRITE_SWIMMING_NAME = "actors/player_water";
    final private static String SPRITE_UNDERWATER_NAME = "actors/player_underwater";
    final private static String SPRITE_SWIMMING_MASK_NAME = "actors/player_swimming_mask";
    private final OrientedAnimation swimmingOrientedAnimation;
    private OrientedAnimation runningOrientedAnimation;
    private final OrientedAnimation underWaterOrientedAnimation;
    private final OrientedAnimation swimmingMaskOrientedAnimation;
    private SpriteType currentSprite = SpriteType.RUNNING_SPRITE;
    final private static int ANIMATION_DURATION = 8;
    final private static int MOVE_DURATION = 3;
    private boolean blockNextMove = false;
    private boolean lastOrientationChecked = false;

    // Interactions
    final private ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();

    // Game states and managers
    final private ICMon.ICMonGameState gameState;
    final private ICMon.ICMonEventManager eventManager;
    private final ICMonSoundManager soundManager;

    // Pokemons' management
    final private List<Pokemon> pokemons = new ArrayList<>();
    private boolean isFightStarting = false;

    // Dialogs' management
    private Dialog dialog;
    private boolean inDialog = false;
    private boolean dialogIsLocked = false;

    // Current player's aquatic state (whether he is in the water or not)
    private boolean isDiver = false;

    // Sound management
    private boolean muteWalkingSound = false;


    /**
     * Creates a new main player in the specified area.
     *
     * @param area the main area for the new player
     * @param orientation the spawning orientation of the player
     * @param spawnPosition the spawning position in the main area
     * @param gameState the game state
     * @param eventManager the game event manager
     * @param soundManager the game sound manager
     */
    public ICMonPlayer(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonSoundManager soundManager) {
        super(area, orientation, spawnPosition);
        this.swimmingOrientedAnimation = new OrientedAnimation(SPRITE_SWIMMING_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.runningOrientedAnimation = new OrientedAnimation(SPRITE_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.underWaterOrientedAnimation = new OrientedAnimation(SPRITE_UNDERWATER_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.swimmingMaskOrientedAnimation = new OrientedAnimation(SPRITE_SWIMMING_MASK_NAME, ANIMATION_DURATION/2, this.getOrientation(), this);
        this.gameState = gameState;
        this.eventManager = eventManager;
        this.soundManager = soundManager;
    }

    /**
     * Gets the current animation depending on the current sprite type.
     * @return the current animation
     */
    public OrientedAnimation getCurrentOrientedAnimation () {
        return switch (this.currentSprite) {
            case SWIMMING_SPRITE -> swimmingOrientedAnimation;
            case UNDERWATER_SPRITE -> underWaterOrientedAnimation;
            default -> runningOrientedAnimation;
        };
    }

    /**
     * Mutes the player's walking sound (footsteps, swimming...)
     * @param muteWalkingSound true to mute, false to unmute
     */
    public void setMuteWalkingSound(boolean muteWalkingSound) {
        this.muteWalkingSound = muteWalkingSound;
    }

    /**
     * Whether the player wants to interact with an item or a NPC.
     * @return true if the player wants to interact with a neighbouring entity
     */
    public boolean wantsEntityViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button lKey = keyboard.get(Keyboard.L);
        return lKey.isDown() && !this.inDialog;
    }

    /**
     * Whether the player wants to go underwater
     * @return true if the player wants to go underwater and he's not in dialog
     */
    public boolean wantsUnderWater() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button wKey = keyboard.get(Keyboard.W);
        return wKey.isDown() && !this.inDialog;
    }

    /**
     * Whether the player wants to sprint
     * @return true if the player wants to sprint and he's not in dialog
     */
    public boolean wantsSprint() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button xKey = keyboard.get(Keyboard.X);
        return xKey.isDown() && !this.inDialog;
    }

    /**
     * Whether the specified cell is a valid cell the player may enter when he is underwater
     * @param cell the cell to test
     * @return true if entering the cell is allowed
     */
    public boolean isAllowedUnderwaterCell(ICMonBehavior.ICMonCell cell) {
        return cell.getWalkingType().equals(ICMonBehavior.AllowedWalkingType.FEET_OR_UNDERWATER)
            || cell.getWalkingType().equals(ICMonBehavior.AllowedWalkingType.ENTER_WATER);
    }

    /**
     * Displays a new dialog to the user and makes the player enter the dialog's state
     * @param dialog the dialog to display, not null
     */
    public void openDialog (Dialog dialog) {
        assert dialog!=null;
        this.dialog = dialog;
        this.inDialog = true;
    }

    /**
     * Suspend the game with an event that contains a pause menu
     * @param pokemonFightEvent the event that will handle the pause, not null
     */
    public void suspendGameWithFightEvent(PokemonFightEvent pokemonFightEvent) {
        assert pokemonFightEvent!=null;
        this.gameState.createSuspendWithEventMessage(pokemonFightEvent);
    }

    /**
     * Move the player once a specific key is pressed.
     * @param orientation the orientation to move to
     * @param key the key to press
     */
    private void moveIfPressed(Orientation orientation, Button key) {
        if (key.isDown()) {
            if (!isDisplacementOccurs()) {
                if (!getOrientation().equals(orientation)) {
                    lastOrientationChecked = false;
                }
                orientate(orientation);
                if (!blockNextMove && lastOrientationChecked) {
                    move(wantsSprint() ? 2 : MOVE_DURATION);
                    if (!muteWalkingSound) {
                        boolean isSwimming = currentSprite.equals(SpriteType.SWIMMING_SPRITE) || currentSprite.equals(SpriteType.UNDERWATER_SPRITE);
                        String soundName = isSwimming ? "swimming" : "footsteps";
                        soundManager.playSound(soundName, 10);
                    }
                }
                lastOrientationChecked = true;
            }
        }
    }

    /**
     * Fights a pokemon (that may be either wild or owned by a NPC)
     * Moves the player back if he has no pokemon in his deck
     * @param pokemon the pokemon to fight
     * @param pokemonOwner the owner of the pokemon, null if the pokemon is wild
     * @param performOnWin the action to perform if the player wins
     * @param performOnLose the action to perform if the player loses
     */
    public void fight(Pokemon pokemon, ICMonActor pokemonOwner, Action performOnWin, Action performOnLose) {
        if (this.pokemons.isEmpty()) {
            this.orientate(Orientation.DOWN);
            this.move(1);
            this.openDialog(new Dialog("no_pokemon"));
        } else {
            PokemonSelectionMenu pokemonSelectionMenu = new PokemonSelectionMenu(this);
            PokemonSelectionEvent pokemonSelectionEvent = new PokemonSelectionEvent(eventManager, this, pokemonSelectionMenu);
            this.gameState.createSuspendWithEventMessage(pokemonSelectionEvent);

            pokemonSelectionEvent.onComplete(new AfterPokemonSelectionFightAction(this, this.eventManager, pokemonSelectionMenu, pokemon, performOnWin, performOnLose, pokemonOwner));
        }

    }

    /**
     * Centers the camera on the player.
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /**
     * Checks whether a dialog needs to be updated or if the player wants to move
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // update dialogs
        if (this.inDialog) {
            Keyboard keyboard = getOwnerArea().getKeyboard();
            if (keyboard.get(Keyboard.SPACE).isDown() && !dialogIsLocked){
                this.dialog.update(deltaTime);
                dialogIsLocked = true;
                // wait some time before unlocking the dialog (to prevent double press)
                CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS).execute(() -> {
                    dialogIsLocked = false;
                });
            }
            if (this.dialog.isCompleted()) {
                this.dialog = null;
                this.inDialog = false;
            }
        }

        // if there is no dialog running, we allow the player to move
        else {
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

    /**
     * Gets the list containing this player's pokemons
     * @return the list of pokemons owned by the player
     */
    @Override
    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    /**
     * Draws the player depending on his orientation and the current dialogs
     * @param canvas target, not null
     */
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

    /**
     * A player prevents other entities from entering the cell where he is
     * @return always false
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    /**
     * Gets the coordinates of the cell in front of the player
     * @return the cell in front of the player
     */
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    /**
     * The player always want contact interactions
     * @return always true
     */
    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    /**
     * Whether the player wants distance interaction.
     * It is true because the player always interacts with neighbouring cells.
     * This lets us check the walking type of the cells around him
     * when he's swimming under the ice
     * to make sure he can not exit without passing through the holes in the frozen lake
     * To check whether the player wants a distance interaction with an NPC or an item,
     * one shall use the method wantsEntityViewInteraction()
     * @return always true
     */
    @Override
    public boolean wantsViewInteraction() {
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
        this.gameState.acceptInteraction(other, isCellInteraction);
    }

    /**
     * Handles the interactions between the player and the other actors.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        /**
         * Updates the sprite of the player depending on where he is
         * @param cell the cell the player shall interact with
         * @param isCellInteraction true if it's a contact interaction, false otherwise
         */
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
                // if the player is swimming underwater
                blockNextMove = !isAllowedUnderwaterCell(cell) && currentSprite.equals(SpriteType.UNDERWATER_SPRITE);
            }
        }
         /**
         * Lets the player collect an item if he wants so,
         * through a distance interaction
         * @param item the item the player shall interact with
         * @param isCellInteraction true if it's a contact interaction, false otherwise
         */
        @Override
        public void interactWith(ICMonItem item, boolean isCellInteraction) {
            if (!isCellInteraction && wantsEntityViewInteraction()) {
                item.collect();
                addRandomPokemon();
                soundManager.playSound("collect", 100);
            }
        }

        /**
         * Lets the player collect a gift if he wants so,
         * through a distance interaction, also
         * Opens a dialog to inform the player that he now has the ability to dive underwater
         * when a gift is collected... it's not a basic item!
         * @param gift the gift the player shall interact with
         * @param isCellInteraction true if it's a contact interaction, false otherwise
         */
        @Override
        public void interactWith(ICGift gift, boolean isCellInteraction) {
            if (!isCellInteraction && wantsEntityViewInteraction()) {
                gift.collect();
                soundManager.playSound("collect", 100, true);
                openDialog(new Dialog("collect_gift"));
                isDiver = true;
                runningOrientedAnimation = swimmingMaskOrientedAnimation ;
            }
        }

        /**
         * Lets the player pass a door through a contact interaction
         * @param door the player shall interact with
         * @param isCellInteraction true if it's a contact interaction, false otherwise
         */
        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            if (isCellInteraction) {
                gameState.createPassDoorMessage(door);
            }
        }

        /**
         * Starts a fight with a pokemon if the player gets too close to it
         * @param pokemon the player shall interact with
         * @param isCellInteraction true if it's a contact interaction, false otherwise
         */
        @Override
        public void interactWith(Pokemon pokemon, boolean isCellInteraction) {

            if (isCellInteraction) {
                if (isFightStarting) return;

                // this prevents a double interaction with the pokemon
                isFightStarting = true;

                // to orientate the player, we have to reset motion
                resetMotion();
                CompletableFuture.delayedExecutor((long) (2 * gameState.getFrameDuration()), TimeUnit.MILLISECONDS).execute(() -> {
                    orientate(getOrientation().opposite());
                    move(0);
                });


                // we wait for the player to move, then start the fight
                CompletableFuture.delayedExecutor((long) (2 * gameState.getFrameDuration()), TimeUnit.MILLISECONDS).execute(() -> {
                    fight(pokemon, null, null, null);
                    isFightStarting = false;
                });
            }
        }

        /**
         * Lets the player speak with prof Oaf if he wants so,
         * through a distance interaction
         * @param profOak
         * @param isCellInteraction
         */
        @Override
        public void interactWith(ProfOak profOak, boolean isCellInteraction) {
            if (!isCellInteraction && wantsEntityViewInteraction()) {
                soundManager.playSound("npc", 20, true);
            }
        }
    }
}
