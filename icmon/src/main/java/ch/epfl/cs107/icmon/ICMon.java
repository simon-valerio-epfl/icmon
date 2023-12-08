package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Arena;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.*;
import ch.epfl.cs107.icmon.gamelogic.events.*;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;
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
 * ???
 */
public final class ICMon extends AreaGame {

    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final static String STARTING_MAP = "lab";
    private final static String BALL_STARTING_MAP = "town";
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
        Town town = new Town();
        addArea(town);
        eventAreas.put("town", town);
        Lab lab = new Lab();
        addArea(lab);
        eventAreas.put("lab", lab);
        Arena arena = new Arena();
        addArea(arena);
        eventAreas.put("arena", arena);

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

            ICMonArea townArea = (ICMonArea) eventAreas.get("town");
            ICBall ball = new ICBall(townArea, new DiscreteCoordinates(6, 6));
            ICMonEvent collectBallEvent = new CollectItemEvent(eventManager, player, ball);
            RegisterInAreaAction registerBall = new RegisterInAreaAction(townArea, ball);
            collectBallEvent.onStart(new LogAction("the event started !"));
            collectBallEvent.onStart(registerBall);
            collectBallEvent.onComplete(new LogAction("player is interacting with ball!"));

            IntroductionEvent introductionEvent = new IntroductionEvent(eventManager, player);
            FirstInteractionWithProfOakEvent firstInteractionWithProfOakEvent = new FirstInteractionWithProfOakEvent(eventManager, player);
            EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(eventManager, player);

            events(introductionEvent, firstInteractionWithProfOakEvent, collectBallEvent, endOfTheGameEvent);

            return true;
        }
        return false;
    }

    public void events (ICMonEvent initialEvent, ICMonEvent ...events) {
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

    public class ICMonPauseControlImpl implements ICMonPauseControl {
        public void requestPause() {
            ICMon.this.requestPause();
        }

        public PauseMenu setPauseMenu(PauseMenu menu) {
            return ICMon.this.setPauseMenu(menu);
        }

        public void requestResume() {
            ICMon.this.requestResume();
        }
    }

    public ICMonPauseControl getPauseControl() {
        return new ICMonPauseControlImpl();
    }

    public static class GamePlayMessage {
        public void process() {

        }
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
                event.onStart(new PauseGameAction(getPauseControl(), event.getPauseMenu()));
                event.onComplete(new ResumeGameAction(getPauseControl()));

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

        private ICMonGameState() {

        }

        public void acceptInteraction (Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        public void createPassDoorMessage (Door door) {
            this.message = new PassDoorMessage(door);
        }

        public void createSuspendWithEventMessage (ICMonEvent event) {
            this.message = new SuspendWithEventMessage(event);
        }

        public void readMessage () {
            if (this.message != null) {
                this.message.process();
                this.message = null;
            }
        }
    }

}