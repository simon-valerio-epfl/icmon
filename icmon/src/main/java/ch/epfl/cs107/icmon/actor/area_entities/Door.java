package ch.epfl.cs107.icmon.actor.area_entities;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Door extends AreaEntity {



    String landingArea;
    DiscreteCoordinates landingPosition;
    ICMonArea area;
    DiscreteCoordinates mainCoordinates;
    DiscreteCoordinates[] coordinates;

    /**
     * Create a new door in the Area
     * @param landingArea
     * @param landingPosition
     * @param area
     * @param coordinates
     */
    public Door (String landingArea, DiscreteCoordinates landingPosition, ICMonArea area, DiscreteCoordinates ...coordinates) {
        super(area, Orientation.UP, coordinates[0]);
        this.landingArea = landingArea;
        this.landingPosition = landingPosition;
        this.area = area;
        this.mainCoordinates = coordinates[0];
        this.coordinates = coordinates;
    }

    /**
     *
     * @return the name of the landing area
     */
    public String getLandingArea() {
        return this.landingArea;
    }

    /**
     *
     * @return the position where the player will appear on the landing area
     */
    public DiscreteCoordinates getLandingPosition() {
        return landingPosition;
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Arrays.asList(coordinates);
    }

    @Override
    public void draw(Canvas canvas) {}

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
