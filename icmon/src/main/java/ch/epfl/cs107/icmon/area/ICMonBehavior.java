package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Window;

import java.util.HashMap;
import java.util.Vector;

/**
 * Represents the behavior of an area.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class ICMonBehavior extends AreaBehavior {
    /**
     * Default ICMonBehavior Constructor
     * It creates a 2d array representing the map
     * and initialises its cells depending on their colour
     *
     * @param window (Window), not null
     * @param name   (String): Name of the Behavior, not null
     */
    public ICMonBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICMonCellType color = ICMonCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICMonCell(x, y, color));
            }
        }
    }

    // the possible walking types a cell can have
    public enum AllowedWalkingType {
        NONE, // None
        SURF, // Only with surf
        FEET, // Only with feet
        FEET_OR_UNDERWATER, // The player can walk on the ice or swim under it
        ENTER_WATER, // Only with surf
        ALL // All previous
    }

    /**
     * Represents the different types of cells.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public enum ICMonCellType {
        NULL (0, AllowedWalkingType.NONE),
        WALL (-16777216, AllowedWalkingType.NONE),
        BUILDING (-8750470, AllowedWalkingType.NONE),
        INTERACT (-256, AllowedWalkingType.NONE),
        DOOR (-195580, AllowedWalkingType.ALL),
        INDOOR_WALKABLE (-1, AllowedWalkingType.FEET),
        OUTDOOR_WALKABLE (-14112955, AllowedWalkingType.FEET),
        ICE (-16776961, AllowedWalkingType.FEET_OR_UNDERWATER),
        GRASS (-16743680, AllowedWalkingType.FEET),
        ENTER_WATER( -65296, AllowedWalkingType.ENTER_WATER);

        final int type;
        final AllowedWalkingType walkingType;

        /**
         * Creates a new cell type
         *
         * @param type the int value of the color
         * @param walkingType the walking type of the cell
         */
        ICMonCellType(int type, AllowedWalkingType walkingType) {
            this.type = type;
            this.walkingType = walkingType;
        }

        /**
         * Gets the representation of the cell type
         *
         * @param type the int value of the color
         * @return the cell type
         */
        public static ICMonCellType toType(int type) {
            for (ICMonCellType ict : ICMonCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NULL;
        }
    }


    /**
     * Represents a cell in the area.
     *
     * @author Valerio De Santis
     * @author Simon Lefort
     */
    public class ICMonCell extends Cell {
        /// Type of the cell following the enum
        private final ICMonCellType type;

        /**
         * Default ICMonCell Constructor
         *
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (ICMonCellType), not null
         */
        public ICMonCell(int x, int y, ICMonCellType type) {
            super(x, y);
            this.type = type;
        }

        /**
         * Gets the walking type of the cell
         * @return the walking type of the cell
         */
        public AllowedWalkingType getWalkingType () {
            return this.type.walkingType;
        }

        /**
         * Checks if the entity can leave the cell
         * @param entity the entity that wants to leave the cell
         * @return true if the entity can leave the cell, false otherwise
         */
        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        /**
         * Checks if the entity can enter the cell
         * @param entity the entity that wants to enter the cell
         * @return true if the entity can enter the cell, false otherwise
         */
        @Override
        protected boolean canEnter(Interactable entity) {

            // this is useful especially for the balloon
            if (!entity.takeCellSpace()) return true;

            if (this.type.walkingType.equals(AllowedWalkingType.NONE)) return false;
            boolean cellIsTaken = false;
            for (Interactable alreadyThereEntity : this.entities) {
                if (alreadyThereEntity.takeCellSpace()) {
                    cellIsTaken = true;
                }
            }
            return !cellIsTaken;
        }

        /**
         * Checks if the cell accepts contact interactions
         * @return always true
         */
        @Override
        public boolean isCellInteractable() {
            return true;
        }

        /**
         * Checks if the cell accepts view interactions
         * We want the player to be able to interact with surrounding cells
         * to know their walking type
         * @return always true
         */
        @Override
        public boolean isViewInteractable() {
            return true;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
        }

    }
}

