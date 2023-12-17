package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightPokemonSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class PokemonSelectionMenu extends PauseMenu {
    private ICMonPlayer player;
    private ICMonFightPokemonSelectionGraphics arena;
    private boolean arenaCreated = false;
    private boolean isSelected = false;

    public PokemonSelectionMenu(ICMonPlayer player) {
        this.player = player;
    }

    @Override
    protected void drawMenu(Canvas c) {
        System.out.println("draw menu of pokemon selection");
        this.arena.draw(c);
    }

    public void update(float deltaTime) {
        if (!arenaCreated) {
            this.arena = new ICMonFightPokemonSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), this.player.getPokemons());
            arenaCreated = true;
        }
        this.arena.update(deltaTime);
        System.out.println(this.arena.choice());
        if (this.arena.choice() != null) {
            isSelected = true;
        }
    }

    public Pokemon getPokemon () {
        return this.arena.choice();
    }

    public boolean isRunning() {
        return !isSelected;
    }
}
