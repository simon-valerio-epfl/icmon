package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor {
    private final String pokemonName;
    private int hp;
    private final int maxHp;
    private final int damage;
    private final RPGSprite sprite;

    // todo ici on doit mettre un ICMONAREA OU UN AREA ?
    public Pokemon(ICMonArea area, Orientation orientation, DiscreteCoordinates spawnPosition, String pokemonName, int maxHp, int damage) {
        super(area, orientation, spawnPosition);
        this.pokemonName = pokemonName;
        this.maxHp = maxHp;
        this.damage = damage;

        this.sprite = new RPGSprite("pokemon/" + this.pokemonName, 1, 1, this);

        this.hp = maxHp;
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
        if (this.hp - damage <= 0) {
            this.hp = 0;
        } else {
            this.hp -= damage;
        }
    }

    public boolean isAlive () {
        return this.hp <= 0;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public String name(){
            return null;
        }

        public float hp(){
            return 0f;
        }

        public float maxHp(){
            return 0f;
        }

        public int damage(){
            return 0;
        }

    }

}