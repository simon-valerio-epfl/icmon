package ch.epfl.cs107.icmon.gamelogic.actions;

public class LogAction implements Action {

    final private String message;

    public LogAction(String message) {
        this.message = message;
    }

    public void perform() {
        System.out.println(message);
    }
}
