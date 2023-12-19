package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.actions.PokemonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
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
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor {
    private final RPGSprite sprite;
    private List<PokemonFightAction> actions;

    private String name;
    private float hp;
    private float maxHp;
    private int damage;

    /**
     *
     * @param area
     * @param orientation
     * @param spawnPosition
     * @param pokemonName the pokemon type, e.g. pikachu
     * @param maxHp the spawning Hp as well, has to be a positive value
     * @param damage how hard it will hit his opponents in a fight, has to be a positive value
     */
    public Pokemon(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, String pokemonName, float maxHp, int damage) {
        super(area, orientation, spawnPosition);
        assert (maxHp >0);
        assert (damage>0);

        this.name = pokemonName;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;

        this.sprite = new RPGSprite("pokemon/" + name, 1, 1, this);
    }

    protected void setActions(PokemonFightAction ...actions) {
        this.actions = new ArrayList<>();
        Collections.addAll(this.actions, actions);
    }


    /**
     * another actor can be in the same cell as a pokemon, possibly
     * interacting with it
     * @return
     */
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    /**
     * It does not accept distance interactions
     * @return
     */
    @Override
    public boolean isViewInteractable() {
        return false;
    }

    /**
     * It accepts proximity interactions
     * @return
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    /**
     * The current instance gets hit, dying if it has
     * no more hp
     *
     * @param damage how many hp the pokemon will lose... if it's still alive
     */
    public void damage (int damage) {
        if (this.hp - damage <= 0) {
            this.hp = 0;
        } else {
            this.hp = hp - damage;
        }
    }

    /**
     *
     * @return whether it is able to continue the fight
     */
    public boolean isDead () {
        return this.hp > 0;
    }

    // todo list ou Array

    /**
     *
     * @return the actions this pokemon can perform
     */
    public List<PokemonFightAction> getActions() {
        return actions;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith((ICMonFightableActor) this, isCellInteraction);
    }

    /**
     *
     * @return a nested class that manages
     * some sensible methods
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

}