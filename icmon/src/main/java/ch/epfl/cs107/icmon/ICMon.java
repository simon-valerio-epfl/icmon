package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.area_entities.Door;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnRegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
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
    // todo fabrice ce truc est implémenté dans la classe AreaGame, on peut pas
    // récupérer l'aire directement depuis la classe d'en haut au lieu de faire
    // notre propre map ?
    //private final Map<String, Area> areas = new HashMap<>();
    private final static String STARTING_MAP = "town";
    /** ??? */
    private ICMonPlayer player;
    /** ??? */
    private ArrayList<ICMonEvent> events = new ArrayList<>();
    private ArrayList<ICMonEvent> startingEvents = new ArrayList<>();
    private ArrayList<ICMonEvent> completedEvents = new ArrayList<>();
    private ICMonGameState gameState = new ICMonGameState();

    /**
     * ???
     */
    private void createAreas() {
        Town town = new Town();
        addArea(town);
        //areas.put("town", town);
        Lab lab = new Lab();
        addArea(lab);
        //areas.put("lab", lab);
    }

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        // todo should we remove the actors and the areas?

        startingEvents.clear();
        completedEvents.clear();
        events.clear();

        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea(STARTING_MAP);

            // todo: créer une fonction getAreaFromName()
            ICMonArea townArea = (ICMonArea) getCurrentArea();
            ICBall ball = new ICBall(townArea, new DiscreteCoordinates(6, 6));
            ICMonEvent collectBallEvent = new CollectItemEvent(ball, player);
            RegisterInAreaAction registerBall = new RegisterInAreaAction(townArea, ball);
            collectBallEvent.onStart(new LogAction("the event started !"));
            collectBallEvent.onStart(registerBall);
            collectBallEvent.onComplete(new LogAction("player is interacting with ball!"));

            EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(player);

            collectBallEvent.onStart(new RegisterEventAction(collectBallEvent, startingEvents));
            endOfTheGameEvent.onStart(new RegisterEventAction(endOfTheGameEvent, startingEvents));

            collectBallEvent.onComplete(new UnRegisterEventAction(collectBallEvent, completedEvents));
            endOfTheGameEvent.onComplete(new UnRegisterEventAction(endOfTheGameEvent, completedEvents));

            collectBallEvent.onComplete(endOfTheGameEvent::start);

            collectBallEvent.start();

            return true;
        }
        return false;
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
        
        events.forEach((ICMonEvent event) -> {
            event.update(deltaTime);
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
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState);
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

    public class GamePlayMessage {
        public void process() {

        }
    }

    public class PassDoorMessage extends GamePlayMessage {
        private Door door;
        public PassDoorMessage (Door door) {
            this.door = door;
        }
        @Override
        public void process () {
            player.leaveArea();
            String landingAreaName = this.door.getLandingArea();
            initArea(landingAreaName);
            player.changePosition(this.door.getLandingPosition());
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

        public void send (GamePlayMessage message) {
            this.message = message;
        }

        // todo comment faire mieux ?
        public PassDoorMessage createPassDoorMessage (Door door) {
            return new PassDoorMessage(door);
        }

        public void readMessage () {
            if (this.message != null) {
                this.message.process();
                this.message = null;
            }
        }
    }

}