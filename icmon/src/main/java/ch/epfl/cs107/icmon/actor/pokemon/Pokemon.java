package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.pokemon.actions.AttackFightAction;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAwayFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightableActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor {
    private final PokemonProperties properties;
    private final RPGSprite sprite;
    private final List<ICMonFightAction> actions;
    private final static ICMonFightAction ATTACK_ACTION = new AttackFightAction();
    private final static ICMonFightAction RUN_AWAY_ACTION = new RunAwayFightAction();
    private final static ICMonFightAction[] DEFAULT_ACTIONS = {ATTACK_ACTION, RUN_AWAY_ACTION};

    public Pokemon(Area area, Orientation orientation, DiscreteCoordinates spawnPosition, String pokemonName, float maxHp, int damage, ICMonFightAction ...extraActions) {
        super(area, orientation, spawnPosition);

        this.properties = new PokemonProperties(pokemonName);
        this.properties.setHp(maxHp);
        this.properties.setMaxHp(maxHp);
        this.properties.setDamage(damage);

        this.sprite = new RPGSprite("pokemon/" + this.properties.name(), 1, 1, this);

        this.actions = new ArrayList<>(Arrays.asList(DEFAULT_ACTIONS));
        Collections.addAll(this.actions, extraActions);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        this.sprite.draw(canvas);
    }

    public void damage (int damage) {
        if (this.properties.hp() - damage <= 0) {
            this.properties.setHp(0);
        } else {
            this.properties.setHp(this.properties.hp() - damage);
        }
    }

    public boolean isAlive () {
        return this.properties.hp() > 0;
    }

    // todo list ou Array
    public List<ICMonFightAction> getActions() {
        return actions;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith((ICMonFightableActor) this, isCellInteraction);
    }

    public PokemonProperties properties() {
        return this.properties;
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public static final class PokemonProperties {

        private float hp;
        final private String name;
        private float maxHp;
        private int damage;

        private PokemonProperties(String name) {
            this.name = name;
        }

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

        private void setHp(float hp) {
            this.hp = hp;
        }

        private void setMaxHp(float maxHp) {
            this.maxHp = maxHp;
        }

        private void setDamage(int damage) {
            this.damage = damage;
        }

    }

}