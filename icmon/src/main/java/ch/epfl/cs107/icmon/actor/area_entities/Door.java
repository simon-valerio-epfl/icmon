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

public class Door extends AreaEntity {

    String landingArea;
    DiscreteCoordinates landingPosition;
    Area area;
    DiscreteCoordinates mainCoordinates;
    DiscreteCoordinates[] coordinates;


    public Door (String landingArea, DiscreteCoordinates landingPosition, Area area, DiscreteCoordinates ...coordinates) {
        super(area, Orientation.UP, coordinates[0]);
        this.landingArea = landingArea;
        this.landingPosition = landingPosition;
        this.area = area;
        this.mainCoordinates = coordinates[0];
        this.coordinates = coordinates;
    }

    public String getLandingArea() {
        return this.landingArea;
    }

    public DiscreteCoordinates getLandingPosition() {
        return landingPosition;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Arrays.asList(coordinates);
    }

    @Override
    public void draw(Canvas canvas) {

    }

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
