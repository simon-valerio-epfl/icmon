package ch.epfl.cs107.icmon.gamelogic.events.classic_quest;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class FirstInteractionWithGarryEvent extends ICMonEvent {

    final private ICMon.ICMonGameState gameState;

    public FirstInteractionWithGarryEvent (ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMon.ICMonGameState gameState) {
        super(eventManager, player);
        this.gameState = gameState;
    }

    @Override
    public void interactWith(Garry garry, boolean isCellInteraction) {
        if (this.isStarted() && !this.isCompleted()) {
            Pokemon garryPokemon = garry.getPokemons().get(0);
            getPlayer().fight(garryPokemon, garry);
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
