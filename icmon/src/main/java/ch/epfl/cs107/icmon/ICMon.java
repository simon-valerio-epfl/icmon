package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICGift;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.*;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.choco_quest.CollectGiftEvent;
import ch.epfl.cs107.icmon.gamelogic.events.classic_quest.*;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.window.swing.SwingSound;

import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ???
 */
public final class ICMon extends AreaGame {

    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final static String STARTING_MAP = "house";
    private final Map<String, Area> eventAreas = new HashMap<>();
    /** ??? */
    private ICMonPlayer player;
    /** ??? */
    private final ArrayList<ICMonEvent> events = new ArrayList<>();
    private final ArrayList<ICMonEvent> startingEvents = new ArrayList<>();
    private final ArrayList<ICMonEvent> completedEvents = new ArrayList<>();
    private final ICMonGameState gameState = new ICMonGameState();
    private final ICMonEventManager eventManager = new ICMonEventManager();

    /**
     * ???
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
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        startingEvents.clear();
        completedEvents.clear();
        events.clear();

        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea(STARTING_MAP);

            try {
                InputStream inputStream = fileSystem.read(ResourcePath.getSound("tf_nemesis"));
                SwingSound sound = new SwingSound(inputStream);

                // Obtenir un Clip et le jouer
                Clip clip = sound.openedClip(0);
                if (clip != null) {
                    clip.start();
                    // Vous pouvez ajouter d'autres logiques pour gérer la lecture du sonx
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("oh non wsh il est où le fichier");
            }

            // Setup Official Quest
            ICMonArea townArea = (ICMonArea) eventAreas.get("town");
            ICBall ball = new ICBall(townArea, new DiscreteCoordinates(6, 6));
            ICMonEvent collectBallEvent = new CollectBallEvent(eventManager, player, ball);
            RegisterInAreaAction registerBall = new RegisterInAreaAction(townArea, ball);
            collectBallEvent.onStart(registerBall);

            IntroductionEvent introductionEvent = new IntroductionEvent(eventManager, player);
            FirstInteractionWithProfOakEvent firstInteractionWithProfOakEvent = new FirstInteractionWithProfOakEvent(eventManager, player);
            FirstInteractionWithGarryEvent firstInteractionWithGarryEvent = new FirstInteractionWithGarryEvent(eventManager, player, gameState);
            EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(eventManager, player);

            events(introductionEvent, firstInteractionWithProfOakEvent, collectBallEvent, firstInteractionWithGarryEvent, endOfTheGameEvent);

            // Side choco quest
            ICGift gift = new ICGift(townArea, new DiscreteCoordinates(25, 14));
            ICMonEvent collectGiftItem = new CollectGiftEvent(eventManager, player, gift);
            RegisterInAreaAction registerGift = new RegisterInAreaAction(townArea, gift);
            collectGiftItem.onStart(registerGift);

            events(collectGiftItem);

            return true;
        }
        return false;
    }

    public void events (ICMonEvent initialEvent, ICMonEvent ...events) {
        // todo event doit créer la balle ?
        ICMonChainedEvent icMonChainedEvent = new ICMonChainedEvent(eventManager, player, initialEvent, events);
        icMonChainedEvent.start();
    }

    /**
     * ???
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
    }

    private void reset () {
        player.leaveArea();
        getCurrentArea().end();

        begin(this.getWindow(), this.getFileSystem());
    }

    /**
     * ???
     */
    @Override
    public void end() {

    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "ICMon";
    }

    /**
     * ???
     * @param areaKey ???
     */
    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState, eventManager);
        player.enterArea(area, coords);
        player.centerCamera();
    }

    /**
     * ???
     */
    private void switchArea() {
        /*player.leaveArea();
        areaIndex = (areaIndex == 0) ? 1 : 0;
        ICMonArea currentArea = (ICMonArea) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());*/
    }

    /**
     * This action changes the game pause menu.
     * It is triggered when an event is started.
     */
    public class SetPauseMenuAction implements Action {
        final private PauseMenu pauseMenu;

        public SetPauseMenuAction(PauseMenu pauseMenu) {
            this.pauseMenu = pauseMenu;
        }
        @Override
        public void perform() {
            setPauseMenu(this.pauseMenu);
        }
    }

    public static class GamePlayMessage {
        public void process() {}
    }

    private class PassDoorMessage extends GamePlayMessage {
        private final Door door;
        public PassDoorMessage (Door door) {
            this.door = door;
        }
        @Override
        public void process () {
            player.leaveArea();
            String landingAreaName = this.door.getLandingArea();
            ICMonArea currentArea = (ICMonArea) setCurrentArea(landingAreaName, false);
            player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
            player.changePosition(this.door.getLandingPosition());
        }
    }

    /**
     * Adds or removes events from the game.
     */
    public class ICMonEventManager {
        public void registerEvent(ICMonEvent event) {
            startingEvents.add(event);
        }
        public void unRegisterEvent(ICMonEvent event) {
            completedEvents.add(event);
        }
    }

    private class SuspendWithEventMessage extends GamePlayMessage {
        private final ICMonEvent event;
        public SuspendWithEventMessage (ICMonEvent event) { this.event = event; }
        @Override
        public void process() {
            if (event.hasPauseMenu()) {
                event.onStart(new PauseGameAction((PauseMenu.Pausable) this, event.getPauseMenu()));
                event.onStart(new SetPauseMenuAction(event.getPauseMenu()));
                event.onComplete(new ResumeGameAction((PauseMenu.Pausable) this));

                for (ICMonEvent eventToSuspend: events) {
                    event.onStart(new SuspendEventAction(eventToSuspend));
                    event.onComplete(new ResumeEventAction(eventToSuspend));
                }
            }
            event.start();
        }
    }

    public class ICMonGameState {
        private GamePlayMessage message;

        private ICMonGameState() {}

        public void acceptInteraction (Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        /**
         * Move the player to the Atlantis arena (only if it's a diver)
         */
        public void transferToAtlantis() {
            Door door = new Door("atlantis", new DiscreteCoordinates(9, 8), eventAreas.get("atlantis"), new DiscreteCoordinates(7, 11));
            createPassDoorMessage(door);
        }

        /**
         * Creates a message that will be processed in the read message method.
         * @param door the door to pass
         */
        public void createPassDoorMessage (Door door) {
            this.message = new PassDoorMessage(door);
        }

        /**
         * Creates a message that will be processed in the read message method.
         * @param event the event to suspend
         */
        public void createSuspendWithEventMessage (ICMonEvent event) {
            this.message = new SuspendWithEventMessage(event);
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