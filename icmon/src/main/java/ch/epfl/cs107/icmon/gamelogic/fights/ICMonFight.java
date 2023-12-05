package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {

    private enum FightState { INTRODUCTION, COUNTER, CONCLUSION, ENDED };

    private float timeCounter = 5f;

    private Pokemon playerPokemon;
    private Pokemon opponent;

    private ICMonFightArenaGraphics arena;
    private FightState state = FightState.INTRODUCTION;
    private ICMonFightTextGraphics introductionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Welcome to the fight");
    private ICMonFightTextGraphics conclusionTextGraphics = new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Good fight!");

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

    public void update(float deltaTime) {
        super.update(deltaTime);

        switch (this.state) {
            case INTRODUCTION -> {
                this.arena.setInteractionGraphics(introductionTextGraphics);

                Keyboard keyboard = getKeyboard();
                if (keyboard.get(Keyboard.SPACE).isDown()) {
                    this.state = FightState.COUNTER;
                }

            }
            case CONCLUSION -> {
                this.arena.setInteractionGraphics(conclusionTextGraphics);

                Keyboard keyboard = getKeyboard();
                if (keyboard.get(Keyboard.SPACE).isDown()) {
                    this.state = FightState.ENDED;
                }
            }
            case COUNTER -> {
                timeCounter -= deltaTime;
                if (timeCounter <= 0) {
                    state = FightState.CONCLUSION;
                }
                System.out.println(timeCounter);
            }
            default -> {
                // do nothing
            }
        }
    }

    public boolean isRunning() {
        return !(this.state == FightState.ENDED);
    }
}
