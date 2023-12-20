package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * Represents a simple attack action
 *
 * @author Valerio De Santis
 * @author Simon Lefort
 */
final public class AttackFightAction extends PokemonFightAction {

    /**
     * Creates a new attack action
     *
     * @param pokemonDoer the pokemon that will do the action
     */
    public AttackFightAction(Pokemon pokemonDoer) {
        super("Attack", pokemonDoer);
    }

    /**
     * Basic attack taking 2 hp from the enemy pokemon
     * @param target the pokemon that will be targeted by the action
     * @return whether the action had success
     */
    @Override
    public boolean doAction(Pokemon target) {
        target.damage(this.getPokemonDoer().properties().damage());
        return true;
    }
}
