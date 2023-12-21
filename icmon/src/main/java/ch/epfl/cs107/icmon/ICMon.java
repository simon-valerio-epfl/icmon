package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICKey;
import ch.epfl.cs107.icmon.actor.npc.Firework;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.*;
import ch.epfl.cs107.icmon.audio.ICMonSoundManager;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonChainedEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.PauseMenuEvent;
import ch.epfl.cs107.icmon.gamelogic.events.choco_quest.CollectGiftEvent;
import ch.epfl.cs107.icmon.gamelogic.events.choco_quest.CollectKeyAtlantisEvent;
import ch.epfl.cs107.icmon.gamelogic.events.choco_quest.FightPedroEvent;
import ch.epfl.cs107.icmon.gamelogic.events.choco_quest.GiveKeyFabriceEvent;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.*;
import ch.epfl.cs107.icmon.gamelogic.menu.GamePauseMenu;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the IC(e)Mon game.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;

    // areas management
    private final static String STARTING_MAP = "house";
    private final Map<String, ICMonArea> eventAreas = new HashMap<>();

    // main player in the game
    private ICMonPlayer player;

    // events management
    private final ArrayList<ICMonEvent> events = new ArrayList<>();
    private final ArrayList<ICMonEvent> startingEvents = new ArrayList<>();
    private final ArrayList<ICMonEvent> completedEvents = new ArrayList<>();
    private final ICMonEventManager eventManager = new ICMonEventManager();

    // sound management
    private final ICMonSoundManager soundManager;

    // game state management
    private final ICMonGameState gameState = new ICMonGameState();

    /**
     * Creates a new ICMon game.
     *
     * @param fileSystem File system used in the game (to load resources)
     */
    public ICMon(FileSystem fileSystem) {
        super();
        this.soundManager = new ICMonSoundManager(fileSystem);
    }

    /**
     * Creates the areas of the game.
     */
    private void createAreas() {

        eventAreas.clear();
        House house = new House();
        addArea(house);
        eventAreas.put("house", house);
        Town town = new Town();
        addArea(town);
        eventAreas.put("town", town);
        Lab lab = new Lab();
        addArea(lab);
        eventAreas.put("lab", lab);
        Arena arena = new Arena();
        addArea(arena);
        eventAreas.put("arena", arena);
        Shop shop = new Shop();
        addArea(shop);
        eventAreas.put("shop", shop);
        Atlantis atlantis = new Atlantis();
        addArea(atlantis);
        eventAreas.put("atlantis", atlantis);

        for (Area area : eventAreas.values()) {
            area.begin(getWindow(), getFileSystem());
        }
    }

    /**
     * Begin the game. Initialize areas and setup quests.
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return (boolean): whether the game successfully started
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        startingEvents.clear();
        completedEvents.clear();
        events.clear();

        if (super.begin(window, fileSystem)) {

            createAreas();
            initArea(STARTING_MAP);

            GamePauseMenu pauseMenu = new GamePauseMenu(getWindow().getKeyboard(), GamePauseMenu.PauseMenuType.EXIT);
            PauseMenuEvent pauseMenuEvent = new PauseMenuEvent(eventManager, player, pauseMenu, gameState);
            gameState.createSuspendWithEventMessage(pauseMenuEvent);

            setupOfficialQuest();
            setupChocoQuest();

            return true;
        }
        return false;
    }

    /**
     * Set up our unofficial quest :)
     */
    private void setupChocoQuest () {
        ICMonArea townArea = eventAreas.get("town");
        ICGift gift = new ICGift(townArea, new DiscreteCoordinates(22, 27));
        ICMonEvent collectGiftItem = new CollectGiftEvent(eventManager, player, gift);
        RegisterInAreaAction registerGift = new RegisterInAreaAction(townArea, gift);
        collectGiftItem.onStart(registerGift);

        ICMonArea atlantisArea = eventAreas.get("atlantis");
        ICKey key = new ICKey(atlantisArea, new DiscreteCoordinates(13, 13));
        ICMonEvent collectKeyItem = new CollectKeyAtlantisEvent(eventManager, player, key);
        RegisterInAreaAction registerKey = new RegisterInAreaAction(atlantisArea, key);
        collectKeyItem.onStart(registerKey);

        FightPedroEvent fightPedroEvent = new FightPedroEvent(eventManager, soundManager, player);
        fightPedroEvent.onComplete(new DelayedAction(new OpenDialogAction(player, new Dialog("pedro_fight_end_win")), 2000));

        GiveKeyFabriceEvent giveKeyFabriceEvent = new GiveKeyFabriceEvent(eventManager, player);
        giveKeyFabriceEvent.onComplete(new EndGameAction(gameState));

        events(collectGiftItem, collectKeyItem, fightPedroEvent, giveKeyFabriceEvent);
    }

    /**
     * Set up the official quest.
     */
    private void setupOfficialQuest () {
        ICMonArea townArea = eventAreas.get("town");
        ICBall ball = new ICBall(townArea, new DiscreteCoordinates(6, 6));
        ICMonEvent collectBallEvent = new CollectBallEvent(eventManager, player, ball);
        RegisterInAreaAction registerBall = new RegisterInAreaAction(townArea, ball);
        collectBallEvent.onStart(registerBall);

        IntroductionEvent introductionEvent = new IntroductionEvent(eventManager, player);
        FirstInteractionWithProfOakEvent firstInteractionWithProfOakEvent = new FirstInteractionWithProfOakEvent(eventManager, player);
        FirstInteractionWithGarryEvent firstInteractionWithGarryEvent = new FirstInteractionWithGarryEvent(eventManager, player);
        EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(eventManager, player);

        events(introductionEvent, firstInteractionWithProfOakEvent, collectBallEvent, firstInteractionWithGarryEvent, endOfTheGameEvent);
    }

    /**
     * Creates a new chained event and start it based on an initial event and a list of events.
     *
     * @param initialEvent the initial event that will automatically start
     * @param events the list of events
     */
    public void events (ICMonEvent initialEvent, ICMonEvent ...events) {
        ICMonChainedEvent icMonChainedEvent = new ICMonChainedEvent(eventManager, player, initialEvent, events);
        icMonChainedEvent.start();
    }

    /**
     * Updates the current events, the sound manager and reads new messages from the game state.
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Keyboard keyboard = getWindow().getKeyboard();
        Button resetButton = keyboard.get(Keyboard.R);
        if (resetButton.isDown()) {
            reset();
        }

        Button pauseButton = keyboard.get(Keyboard.P);
        if (pauseButton.isPressed()) {
            GamePauseMenu pauseMenu = new GamePauseMenu(keyboard, GamePauseMenu.PauseMenuType.RESUME);
            PauseMenuEvent pauseMenuEvent = new PauseMenuEvent(eventManager, player, pauseMenu, gameState);
            gameState.createSuspendWithEventMessage(pauseMenuEvent);
        }

        events.addAll(startingEvents);
        events.removeAll(completedEvents);

        startingEvents.clear();
        completedEvents.clear();

        //System.out.println("Analyzing " + events.size() + " events");
        events.forEach((ICMonEvent event) -> {
            if (!event.isSuspended()) {
                event.update(deltaTime);
            }
        });

        gameState.readMessage();
        soundManager.update();
    }

    /**
     * Completely reset the game.
     */
    private void reset () {
        player.leaveArea();
        getCurrentArea().end();
        soundManager.resetSound();
        soundManager.resetBackgroundSound();

        begin(this.getWindow(), this.getFileSystem());
    }

    /**
     * Ends the game.
     * todo
     */
    @Override
    public void end() {
        ICMonArea town = eventAreas.get("town");
        DiscreteCoordinates[] spawnPositions = {
            new DiscreteCoordinates(15, 7),
            new DiscreteCoordinates(20, 0),
            new DiscreteCoordinates(22, 2),
            new DiscreteCoordinates(14, 5),
            new DiscreteCoordinates(12, 0),
            new DiscreteCoordinates(16, 0),
        };
        for(DiscreteCoordinates spawnPosition : spawnPositions) {
            Firework firework = new Firework(town, Orientation.DOWN, spawnPosition, soundManager);
            // random delay between 0 and 2 seconds
            int delay = (int) (Math.random() * 1000);
            new DelayedAction(new RegisterInAreaAction(town, firework), delay).perform();
        }
    }

    /**
     * Gets the title of the game.
     * @return the title of the game
     */
    @Override
    public String getTitle() {
        return "IC(e)Mon";
    }

    /**
     * Creates a new main area, and create a new main player in it.
     * @param areaKey the key of the area to create
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState, eventManager, soundManager);
        player.enterArea(area, coords);
        player.centerCamera();
    }


    /**
     * This action changes the game pause menu.
     * It is triggered when an event is started.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public class SetPauseMenuAction implements Action {
        final private PauseMenu pauseMenu;

        /**
         * Creates a new action to change the game pause menu.
         *
         * @param pauseMenu the new pause menu
         */
        public SetPauseMenuAction(PauseMenu pauseMenu) {
            this.pauseMenu = pauseMenu;
        }

        @Override
        public void perform() {
            setPauseMenu(this.pauseMenu);
        }
    }

    /**
     * Represents a message that can be sent to the game state.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public static class GamePlayMessage {
        public void process() {}
    }

    /**
     * Creates a new door message that will transfer the user to another area.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    private class PassDoorMessage extends GamePlayMessage {
        private final Door door;

        /**
         * Creates a new door message.
         * @param door the door to pass
         */
        public PassDoorMessage (Door door) {
            this.door = door;
        }

        // handle sounds and transfer the player to the new area
        @Override
        public void process () {
            if (door.getMuteBackgroundSound()) {
                soundManager.resetBackgroundSound();
            }
            if (door.getBackgroundSoundName() != null) {
                soundManager.playBackgroundSound(door.getBackgroundSoundName());
            }
            player.setMuteWalkingSound(door.getMuteWalkingSound());
            soundManager.playSound(door.getSoundName(), door.getSoundDuration(), true);
            player.leaveArea();
            String landingAreaName = this.door.getLandingArea();
            ICMonArea currentArea = (ICMonArea) setCurrentArea(landingAreaName, false);
            player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
            player.changePosition(this.door.getLandingPosition());
        }
    }

    /**
     * Represents a manager that can register and unregister events from the game.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public class ICMonEventManager {
        public void registerEvent(ICMonEvent event) {
            startingEvents.add(event);
        }
        public void unRegisterEvent(ICMonEvent event) {
            completedEvents.add(event);
        }
    }

    /**
     * Represents a message that can suspend the game via a main event.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    private class SuspendWithEventMessage extends GamePlayMessage {
        private final ICMonEvent event;

        /**
         * Creates a new message that can suspend the game via a main event.
         * @param event the main event
         */
        public SuspendWithEventMessage (ICMonEvent event) {
            this.event = event;
        }

        // handle pause menu, suspend events and resume once the main event is completed
        @Override
        public void process() {
            if (event.hasPauseMenu()) {
                event.onStart(new PauseGameAction(ICMon.this));
                event.onStart(new SetPauseMenuAction(event.getPauseMenu()));
                event.onComplete(new ResumeGameAction(ICMon.this));

                for (ICMonEvent eventToSuspend: events) {
                    event.onStart(new SuspendEventAction(eventToSuspend));
                    event.onComplete(new ResumeEventAction(eventToSuspend));
                }
            }
            event.start();
        }
    }

    /**
     * Represents the game state, given to the player.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public class ICMonGameState {
        private GamePlayMessage message;

        /**
         * Creates a new game state.
         */
        private ICMonGameState() {}

        /**
         * Gets the current frame rate of the game.
         * @return the frame rate of the game
         */
        public float getFrameDuration() {
            return (float) 1000 / getFrameRate();
        }

        public void resetGame() {
            reset();
        }

        /**
         * Makes all the events become handlers of the game's interactions.
         *
         * @param interactable the entity to interact with
         * @param isCellInteraction whether the interaction is a cell interaction
         */
        public void acceptInteraction (Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        /**
         * Move the player to the Atlantis arena (only if it's a diver)
         */
        public void transferToAtlantis() {
            Door door = new Door(
                    "atlantis",
                    new DiscreteCoordinates(9, 8),
                    eventAreas.get("atlantis"),
                    "atlantis",
                    50,
                    true,
                    false,
                    "atlantis-ambiance",
                    new DiscreteCoordinates(7, 11));
            createPassDoorMessage(door);
        }

        /**
         * Creates a new door message that will transfer the user to another area.
         * @param door the door to pass
         */
        public void createPassDoorMessage (Door door) {
            this.message = new PassDoorMessage(door);
        }

        /**
         * Creates a new message that can suspend the game via a main event.
         * @param event the main event
         */
        public void createSuspendWithEventMessage (ICMonEvent event) {
            this.message = new SuspendWithEventMessage(event);
        }

        /**
         * Creates a new message that leads to the end of the game.
         */
        public void createEndMessage () {
            end();
        }

        /**
         * Reads the mailbox in a polymorphic way
         */
        public void readMessage () {
            if (this.message != null) {
                this.message.process();
                this.message = null;
            }
        }
    }

}