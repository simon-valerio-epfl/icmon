package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.actions.PokemonFightAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Pokémon, a monster that can be encountered in the game.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @author Valerio De Santis
 * @author Simon Lefort
 */
public abstract class Pokemon extends ICMonActor {
    private final RPGSprite sprite;
    private List<PokemonFightAction> actions;

    // properties
    private final String name;
    private float hp;
    private final float maxHp;
    private final int damage;

    /**
     * Creates a new Pokémon in the specified area
     *
     * @param area the area where the Pokémon shall spawn
     * @param orientation the orientation of the Pokémon
     * @param spawnPosition the position where the Pokémon shall spawn
     * @param pokemonName the pokemon type, e.g. pikachu
     * @param maxHp the spawning HP as well, has to be a positive value
     * @param damage how hard it will hit his opponents in a fight, has to be a positive value
     */
    public Pokemon(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, String pokemonName, float maxHp, int damage) {
        super(area, orientation, spawnPosition);
        assert (maxHp > 0);
        assert (damage > 0);

        this.name = pokemonName;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;

        this.sprite = new RPGSprite("pokemon/" + name, 1, 1, this);
    }

    /**
     * Sets the actions this pokemon can perform
     * @param actions the actions this pokemon can perform
     */
    protected void setActions(PokemonFightAction ...actions) {
        this.actions = new ArrayList<>();
        Collections.addAll(this.actions, actions);
    }

    /**
     * Make the current instance gets hit
     * and dying if it has no more hp
     *
     * @param damage how many hp the pokemon will lose
     */
    public void damage (int damage) {
        this.hp = Math.max(0, this.hp - damage);
    }

    /**
     * Gets the dead status of the pokemon
     * @return true if the pokemon is dead
     */
    public boolean isDead () {
        return this.hp <= 0;
    }

    /**
     * Gets the actions that the pokemon can perform
     * @return the actions this pokemon can perform
     */
    public List<PokemonFightAction> getActions() {
        return actions;
    }

    /**
     * Gets the properties of the pokemon
     * @return a PokemonProperties nested class instance that expose the properties of the pokemon as getters
     */
    public PokemonProperties properties() {
        return new PokemonProperties();
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public String name(){
            return name;
        }

        public float hp(){
            return hp;
        }

        public float maxHp(){
            return maxHp;
        }

        public int damage(){
            return damage;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

}