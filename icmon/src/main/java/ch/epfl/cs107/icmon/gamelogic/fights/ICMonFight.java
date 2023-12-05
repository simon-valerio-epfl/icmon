package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {

    private float counter = 5f;

    private Pokemon playerPokemon;
    private Pokemon opponent;

    private ICMonFightArenaGraphics arena;

    public ICMonFight(Pokemon playerPokemon, Pokemon opponent) {
        this.playerPokemon = playerPokemon;
        this.opponent = opponent;

        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponent.properties());
        this.arena.setInteractionGraphics(
                new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Hello World")
        );
    }

    @Override
    protected void drawMenu(Canvas c) {
        this.arena.draw(c);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        counter -= deltaTime;
    }

    public boolean isRunning() {
        return counter > 0;
    }
}
