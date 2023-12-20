package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class AttackFightAction extends PokemonFightAction {

    public AttackFightAction(Pokemon pokemonDoer) {
        super("Attack", pokemonDoer);
    }

    /**
     * a basic attack taking 2 hp from the enemy pokemon
     * @param target your opponent
     * @return whether the action had success
     */
    @Override
    public boolean doAction(Pokemon target) {
        System.out.println(this.getPokemonDoer().properties().name() + " attacks " + target.properties().name() + "!");
        System.out.println(target.properties().name() + " loses " + this.getPokemonDoer().properties().damage() + " hp!");
        target.damage(this.getPokemonDoer().properties().damage());
        return true;
    }
}
