package ch.epfl.cs107.icmon.gamelogic.actions;

/**
 * Represents an action that logs some message in the terminal.
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public final class LogAction implements Action {

    final private String message;

    /**
     * Creates a new log action.
     *
     * @param message the message to log
     */
    public LogAction(String message) {
        assert message!= null;
        this.message = message;
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
}
