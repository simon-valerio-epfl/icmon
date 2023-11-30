package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.window.Window;

public final class ICMonBehavior extends AreaBehavior {
    /**
     * Default ICMonBehavior Constructor
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
    public enum AllowedWalkingType {
        NONE, // None
        SURF, // Only with surf
        FEET, // Only with feet
        ALL // All previous
    }

    public enum ICMonCellType {
        NULL (0, AllowedWalkingType.NONE),
        WALL (-16777216, AllowedWalkingType.NONE),
        BUILDING (-8750470, AllowedWalkingType.NONE),
        INTERACT (-256, AllowedWalkingType.NONE),
        DOOR (-195580, AllowedWalkingType.ALL),
        INDOOR_WALKABLE (-1, AllowedWalkingType.FEET),
        OUTDOOR_WALKABLE (-14112955, AllowedWalkingType.FEET),
        WATER (-16776961, AllowedWalkingType.SURF),
        GRASS (-16743680, AllowedWalkingType.FEET);

        final int type;
        final AllowedWalkingType surfaceType;

        ICMonCellType(int type, AllowedWalkingType surfaceType) {
            this.type = type;
            this.surfaceType = surfaceType;
        }

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
     * Cell adapted to the Tuto2 game
     */
    public class ICMonCell extends Cell {
        /// Type of the cell following the enum
        private final ICMonCellType type;

        /**
         * Default Tuto2Cell Constructor
         *
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public ICMonCell(int x, int y, ICMonCellType type) {
            super(x, y);
            this.type = type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            boolean cellIsTaken = false;
            for (Interactable alreadyThereEntity : this.entities) {
                if (alreadyThereEntity.takeCellSpace()) {
                    cellIsTaken = true;
                }
            }
            return !cellIsTaken;
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        }

    }
}

