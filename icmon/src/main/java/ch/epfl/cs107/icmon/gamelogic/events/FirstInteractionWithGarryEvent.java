package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaPokemonDeadAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;

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
            ICMonFight ourFight = new ICMonFight(getPlayer().getPokemons().get(0), garry.getPokemons().get(0));
            PokemonFightEvent pokemonFightEvent = new PokemonFightEvent(getEventManager(), getPlayer(), ourFight);
            this.gameState.createSuspendWithEventMessage(pokemonFightEvent);
            pokemonFightEvent.onComplete(new LeaveAreaPokemonDeadAction(garry, garryPokemon));
        }
    }

    @Override
    public void update(float deltaTime) {

    }
}
