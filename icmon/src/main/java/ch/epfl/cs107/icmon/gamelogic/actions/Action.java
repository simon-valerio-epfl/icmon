package ch.epfl.cs107.icmon.gamelogic.actions;

public interface Action {
    /**
     * an action is characterized by the possibility
     * to perform it
     */
    abstract void perform();
}
