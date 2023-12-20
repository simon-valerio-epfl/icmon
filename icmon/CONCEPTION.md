# Developer Documentation

Welcome to the developer documentation of the ICeMon game.

## Changes from original ICeMon

### Fights

* We made a Pokémon selector as suggested in the project description. We modularized through to the `createPokemonSelector` and `getPokemonGraphics` methods. This selector appears in the fights with Garry, Pedro and in the arena.
* We also replaced the interface `ICMonFightAction` by the class `PokemonFightAction`, as we have to access the pokemon that does the action. We think it is a better design than adding a `Pokemon pokemonDoer` to the `doAction()` method as its value is fixed once the instance is created.
* We created a new interface `PokemonOwner`, that applies to `Garry`, `Pedro` and `ICMonPlayer`. It allows us to factorize the code of the `addPokemon()` method and forces the actors to define a `getPokemons()` method.
* We also created a new area, called `MyPocket`. It is shared between all `PokemonOwner`. It does not serve any concrete feature, but helps to keep the code clean (we think it is better to have a dedicated empty area for Pokémons than selecting a random one that we could remove by mistake without thinking it held the Pokémons).
* We added a check in the `Arena`, so when the player does not have any pokemon, it throws an error using a dialog.
* We also reversed the order of the pokemons in the fight (our pokemon is now on the left, and the opponent on the right), as Hamza told us to do so because of a small mistake in the game engine.

### Actors

* We created a new `Fabrice` NPC class. As we needed a tall Fabrice (more than 16x21 pixels), we had to update the `NPC` class and allow a new constructor that could specify a custom `RegionOfInterest` and `scaleFactor`. 
* We created a new `Balloon` NPC class, along with the `BalloonStep` nested class. This one handles the automated movement of the balloon.
* We created a new `Pedro` class (classic NPC).

### Items

* We created a new `ICGift` class (classic item).
* We created a new `ICKey` class (classic item).

### Events

* We created a new `icmon.events.choco_quest` and `icmon.events.classic_quest` to split the official requested quest and ours.
* We factorized `CollectBallEvent` and `CollectGiftEvent` with the `CollectItemEvent` abstract class to avoid duplicate code.
* We created a new `CollectGiftEvent` classic event, similar to the `CollectBallEvent`.
* We created a new `CollectKeyAtlantisEvent` classic event, similar to the `CollectBallEvent` event.
* We created a new `FightPedroEvent` classic event, similar to the `FirstInteractionWithGarry` event.
* We created a new `GiveKeyFabriceEvent` classic event, that concludes the quest.

### Actions

* We created a new `DelayedAction`, that allows us to perform some actions a few seconds after the completion of an event.

### Areas

* We created a new `Atlantis` area, along with a `transferToAtlantis` method in the `GameState` class.
* `Atlantis` has its own `AreaBehavior` that we designed by ourselves, and a custom foreground.

### ICMonPlayer

* We wanted to make the `ICMonPlayer` swim underwater. To do so, we made two pink rectangles in the area behavior.
* We also wanted to force the player to go back to the holes in the ice to get out of the lake. This was quite difficult because by default, if the cell is a valid walking type, there is no way to prevent the player from entering the cell. We thought about creating a `getNeighbouringCells` in the `ICMonAreaBehavior` (that we could use in the `moveIfPressed` method of the player) but the behavior is not exposed by the game engine to the `ICMonArea`. Therefore, we had to figure out another way to block the move. To do so, we used view interactions. We keep the view interactions enabled, so we can always interact with the cell in front of us, save its type, and decide if we have to block the move or not. This forces us to create a `wantsRealViewInteraction()` method, that "bypass" the original `wantsViewInteraction()` and has to be checked everywhere. This is the optimal solution without updating the game engine: if we were to publish the game, we would simply modify it and expose the area behavior using a protected getter.

## Sounds

* We created a new `ICMonSoundManager`, that is given to the player constructor.
* It allows us to play sounds from anywhere in the game using the `playSound()` method, or the `playBackgroundSound()` one.
* Doors were also modified (new `getSound()` getter to play a custom sound when they are used.

### Dialogs

* When the player uses the `SPACE` key to jump from a `Dialog` slide to another, it now uses `CompletableFuture` Java util to add a mandatory delay between sldies. This is useful because on some computers, the minimum time the key is pressed is longer than the frame rate and you miss some slides.
* We created new dialogs: `collect_gift`, `collect_gift_fabrice`, `collect_gift_pedro`, `collect_key_fabrice`, `no_pokemon`, `pedro_fight_end_win`, `pedro_steal_key`.

### Other changes

