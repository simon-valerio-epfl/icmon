package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.GraphicsEntity;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.List;

import static ch.epfl.cs107.play.io.ResourcePath.getBackground;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.nonNull;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class ICMonFightPokemonSelectionGraphics implements Updatable {

    private static final float FONT_SIZE = .6f;

    private final Keyboard keyboard;
    private final float scalefactor;
    private final Pokemon[] pokemons;

    private final GraphicsEntity[][] selectors;

    private Pokemon choice;
    private ImageGraphics background;

    private int currentChoice;

    public ICMonFightPokemonSelectionGraphics(float scaleFactor, Keyboard keyboard, List<Pokemon> pokemons) {
        assert !pokemons.isEmpty();
        this.keyboard = keyboard;
        this.scalefactor = scaleFactor;
        this.pokemons = pokemons.toArray(new Pokemon[0]);
        selectors = new GraphicsEntity[3][2];
        currentChoice = 0;
    }

    @Override
    public void update(float deltaTime) {
        // HR : Keyboard management
        if (keyboard.get(Keyboard.LEFT).isPressed()){
            currentChoice = max(0, currentChoice - 1);
        } else if (keyboard.get(Keyboard.RIGHT).isPressed())
            currentChoice = min(currentChoice + 1, pokemons.length - 1);
        else if (keyboard.get(Keyboard.ENTER).isPressed())
            choice = pokemons[currentChoice];
        // HR : Prepare the left selector
        if (currentChoice == 0){
            selectors[0] = null;
        } else {
            selectors[0] = createPokemonSelector(pokemons[currentChoice - 1], false, -1);
        }
        // HR : Prepare the middle selector
        selectors[1] = createPokemonSelector(pokemons[currentChoice], true, 0);
        // HR : Prepare the Right selector
        if (currentChoice == pokemons.length - 1 ){
            selectors[2] = null;
        } else {
            selectors[2] = createPokemonSelector(pokemons[currentChoice + 1], false, 1);
        }
    }

    private ImageGraphics getPokemonGraphics(Pokemon pokemon, boolean isSelected) {
        String spriteName = "pokemon/"+ pokemon.properties().name();
        ImageGraphics image = new ImageGraphics(ResourcePath.getSprite(spriteName), scalefactor/2, scalefactor/2);
        image.setAlpha(isSelected ? 1 : .6f);
        return image;
    }

    private GraphicsEntity[] createPokemonSelector (Pokemon pokemon, boolean isSelected, int positionComparedToMiddle) {
        ImageGraphics image = getPokemonGraphics(pokemon, isSelected);
        // todo regarder comment ça fonctionne exactement l'affichage
        GraphicsEntity imageEntity = new GraphicsEntity (new Vector((float) (scalefactor * (positionComparedToMiddle * 0.5) + scalefactor * 1.5 / 3 - 2f), scalefactor / 2 - 4f), image);
        TextGraphics pokemonName = new TextGraphics(pokemon.properties().name(), FONT_SIZE, Color.WHITE, Color.BLACK, 0.0f, isSelected, false, Vector.ZERO, TextAlign.Horizontal.LEFT, TextAlign.Vertical.MIDDLE,  .6f, 1003);
        GraphicsEntity textEntity = new GraphicsEntity (new Vector((float) (scalefactor * (positionComparedToMiddle * 0.5) + scalefactor * 1.5 / 3 - 0.5f), scalefactor / 2 - 5f), pokemonName);
        return new GraphicsEntity[]{imageEntity, textEntity};
    }

    public Pokemon choice(){
        return choice;
    }

    public void draw(Canvas canvas) {
        // HR : Draw the selectors that are visible (not null)
        for (var selector : selectors)
            if(nonNull(selector) && nonNull(selector[0]) && nonNull(selector[1])) {
                selector[0].draw(canvas);
                selector[1].draw(canvas);
            }
    }
}
