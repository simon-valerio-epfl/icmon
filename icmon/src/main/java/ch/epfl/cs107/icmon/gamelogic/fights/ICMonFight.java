package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {
    private enum FightState { INTRODUCTION, SELECT_ACTION, EXECUTE_ACTION, SELECT_OPPONENT_ACTION, CONCLUSION, ENDED }

    private enum ConclusionReason { PLAYER_LEFT, OPPONENT_LEFT, PLAYER_DEAD, OPPONENT_DEAD }

    private final float timeCounter = 5f;

    private final Pokemon playerPokemon;
    private final Pokemon opponent;

    private final ICMonFightArenaGraphics arena;
    private FightState state = FightState.INTRODUCTION;
    private final ICMonFightTextGraphics introductionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Welcome to the fight");
    private final ICMonFightTextGraphics wonConclusionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The player has won the fight");
    private final ICMonFightTextGraphics cancelledConclusionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The player decided not to continue the fight");
    private final ICMonFightTextGraphics deadConclusionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The opponent has won the fight");
    private final ICMonFightTextGraphics cancelledOpponentConclusionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "The opponent decided not to continue the fight");
    private final ICMonFightTextGraphics emptyTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, null);
    private ICMonFightActionSelectionGraphics selectionGraphics;
    private ICMonFightAction selectedAction;
    private ConclusionReason conclusionReason;

    /**
     * Gets the competitors to the fight arena
     * @param playerPokemon the main player's one
     * @param opponent the evil one
     */
    public ICMonFight(Pokemon playerPokemon, Pokemon opponent) {
        this.playerPokemon = playerPokemon;
        this.opponent = opponent;

        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponent.properties());
        this.arena.setInteractionGraphics(introductionTextGraphics);
    }

    @Override
    protected void drawMenu(Canvas c) {
        this.arena.draw(c);
    }

    /**
     * Carries out the fight,
     * following the competitors' choices
     * and the evolution of the battle itself
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    public void update(float deltaTime) {
        super.update(deltaTime);

        switch (this.state) {
            case INTRODUCTION -> {

                Keyboard keyboard = getKeyboard();
                if (keyboard.get(Keyboard.SPACE).isDown()) {
                    this.state = FightState.SELECT_ACTION;

                    this.selectionGraphics = new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), this.playerPokemon.getActions());
                }

            }
            case SELECT_ACTION -> {
                this.arena.setInteractionGraphics(this.selectionGraphics);
                this.selectionGraphics.update(deltaTime);
                if (this.selectionGraphics.choice() != null) {
                    selectedAction = this.selectionGraphics.choice();

                    state = FightState.EXECUTE_ACTION;
                    // todo est-ce qu'ici on doit le recréer à chaque fois ou est-ce qu'on peut vider le choice ?
                    this.selectionGraphics = new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), this.playerPokemon.getActions());
                }
            }
            case SELECT_OPPONENT_ACTION -> {
                for (ICMonFightAction action : this.opponent.getActions()) {
                    if (action.name().equals("Attack")) {
                        boolean isFinished = action.doAction(this.opponent, this.playerPokemon);
                        if (!this.playerPokemon.isAlive()) {
                            conclusionReason = ConclusionReason.PLAYER_DEAD;
                            state = FightState.CONCLUSION;
                        } else if (!isFinished) {
                            conclusionReason = ConclusionReason.OPPONENT_LEFT;
                            state = FightState.CONCLUSION;
                        } else {
                            state = FightState.SELECT_ACTION;
                        }
                    }
                }
            }
            case EXECUTE_ACTION -> {
                boolean hasFinished = selectedAction.doAction(this.playerPokemon, opponent);
                if (!opponent.isAlive()) {
                    conclusionReason = ConclusionReason.OPPONENT_DEAD;
                    state = FightState.CONCLUSION;
                } else if (!hasFinished) {
                    conclusionReason = ConclusionReason.PLAYER_LEFT;
                    state = FightState.CONCLUSION;
                } else {
                    state = FightState.SELECT_OPPONENT_ACTION;
                }
            }
            case CONCLUSION -> {

                switch (conclusionReason) {
                    case PLAYER_DEAD -> {
                        this.arena.setInteractionGraphics(deadConclusionTextGraphics);
                    }
                    case OPPONENT_DEAD -> {
                        this.arena.setInteractionGraphics(wonConclusionTextGraphics);
                    }
                    case PLAYER_LEFT -> {
                        this.arena.setInteractionGraphics(cancelledConclusionTextGraphics);
                    }
                    case OPPONENT_LEFT -> {
                        this.arena.setInteractionGraphics(cancelledOpponentConclusionTextGraphics);
                    }
                }

                Keyboard keyboard = getKeyboard();
                if (keyboard.get(Keyboard.SPACE).isDown()) {
                    this.state = FightState.ENDED;
                }
            }
            default -> {
                // do nothing
            }
        }
    }

    /**
     *
     * @return whether the fight is still going on
     */
    public boolean isRunning() {
        return !(this.state == FightState.ENDED);
    }

    public boolean isWin() {
        return this.state == FightState.ENDED && (conclusionReason == ConclusionReason.OPPONENT_DEAD || conclusionReason == ConclusionReason.OPPONENT_LEFT);
    }
}
