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
    private final ICMonPlayer player;
    private ICMonFightPokemonSelectionGraphics arena;
    private boolean arenaCreated = false;
    private boolean isSelected = false;

    public PokemonSelectionMenu(ICMonPlayer player) {
        this.player = player;
    }

    /**
     * draws the menu interface once the fight has started
     * @param c (Canvas): the context canvas : here the Window
     */
    @Override
    protected void drawMenu(Canvas c) {
        if (this.arenaCreated) {
            this.arena.draw(c);
        }
    }

    /**
     * creates the arena
     * and then waits for the pokemon to be chosen
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (!arenaCreated) {
            this.arena = new ICMonFightPokemonSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), this.player.getPokemons());
            arenaCreated = true;
        }
        this.arena.update(deltaTime);
        if (this.arena.choice() != null) {
            isSelected = true;
        }
    }

    /**
     *
     * @return the chosen pokemon
     */
    public Pokemon getPokemon () {
        return this.arena.choice();
    }

    /**
     *
     * @return whether the player is still choosing or not
     */
    public boolean isRunning() {
        return !isSelected;
    }
}
