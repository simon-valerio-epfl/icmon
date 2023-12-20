package ch.epfl.cs107.icmon.actor.area_entities;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a door between two areas
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class Door extends AreaEntity {

    // areas configuration
    private final String landingArea;
    private final DiscreteCoordinates landingPosition;
    private final DiscreteCoordinates[] coordinates;

    // sound handling
    private final String soundName;
    private final int soundDuration;
    private final boolean muteWalkingSound;
    private final boolean muteBackgroundSound;
    private final String backgroundSoundName;

    /**
     * Creates a new door in the specified landing area
     *
     * @param landingArea the name of the landing area
     * @param landingPosition the position where the player will appear on the landing area
     * @param area the area where the door is
     * @param coordinates the coordinates of the door
     */
    public Door (String landingArea, DiscreteCoordinates landingPosition, Area area, DiscreteCoordinates ...coordinates) {
        this(landingArea, landingPosition, area, "door", 20, false, true, null, coordinates);
    }

    /**
     * Creates a new door in the specified landing area
     *
     * @param landingArea the name of the landing area
     * @param landingPosition the position where the player will appear on the landing area
     * @param area the area where the door is
     * @param soundName the name of the sound that is going to be played once the door is opened
     * @param soundDuration the duration of the sound that is going to be played once the door is opened
     * @param muteWalkingSound whether going through the door should mute walking sounds
     * @param muteBackgroundSound whether going through the door should mute background sounds
     * @param backgroundSoundName the name of the background sound that is going to be played once the door is opened
     * @param coordinates the coordinates of the door
     */
    public Door (String landingArea, DiscreteCoordinates landingPosition, Area area, String soundName, int soundDuration, boolean muteWalkingSound, boolean muteBackgroundSound, String backgroundSoundName, DiscreteCoordinates ...coordinates) {
        super(area, Orientation.UP, coordinates[0]);
        this.landingArea = landingArea;
        this.landingPosition = landingPosition;
        this.coordinates = coordinates;
        this.soundName = soundName;
        this.soundDuration = soundDuration;
        this.muteWalkingSound = muteWalkingSound;
        this.muteBackgroundSound = muteBackgroundSound;
        this.backgroundSoundName = backgroundSoundName;
    }

    /**
     * Gets the name of the landing area
     * @return the name of the landing area
     */
    public String getLandingArea() {
        return this.landingArea;
    }

    /**
     * Gets the position the player will appear at in the landing area
     * @return the position where the player will appear at in the landing area
     */
    public DiscreteCoordinates getLandingPosition() {
        return landingPosition;
    }

    /**
     * Gets the name of the sound that is going to be played once the door is opened
     * @return the name of the sound
     */
    public String getSoundName() {
        return soundName;
    }

    /**
     * Gets the duration of the sound that is going to be played once the door is opened
     * @return the duration of the sound
     */
    public int getSoundDuration() {
        return soundDuration;
    }

    /**
     * Whether going through the door should mute walking sounds
     * @return true if walking sounds should be muted, false otherwise
     */
    public boolean getMuteWalkingSound() {
        return muteWalkingSound;
    }

    /**
     * Whether going through the door should mute background sounds
     * @return true if background sounds should be muted, false otherwise
     */
    public boolean getMuteBackgroundSound() {
        return muteBackgroundSound;
    }

    /**
     * Gets the name of the background sound that is going to be played once the door is opened
     * Note: can be null.
     * @return the name of the background sound
     */
    public String getBackgroundSoundName() {
        return backgroundSoundName;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Arrays.asList(coordinates);
    }

    @Override
    public void draw(Canvas canvas) {}

    /**
     * A door does not accept distance interactions
     * @return always false
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    /**
     * A door accepts proximity interactions
     * @return always true
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * Another entity may enter the cell where the door is
     * @return always false
     */
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
