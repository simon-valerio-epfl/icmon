# Developer Documentation

Welcome to the developer documentation of the ICeMon game.

### Export en fichier binaire


## Changes from original ICeMon

### Fights

* We made a Pokémon selector as suggested in the project description. We try to modularize as much as we could using the `createPokemonSelector` and `getPokemonGraphics` methods. This selector appears in the fights with Garry, Pedro and in the arena.
* We also replaced the interface `ICMonFightAction` by the class `PokemonFightAction`. We think it is a better design as it allows us to access the pokemon's details (the one that does the action). It is useful to have a more precise handling of the action depending on who applies it. (note: we could also have used an extra parameter in the doAction method, but it seemed more natural to have a fixed attribute in the constructor to store it).

### Other changes

* When the player uses the `SPACE` key to jump from a `Dialog` slide to another, it now uses `CompletableFuture` Java util to add a mandatory delay between sldies. This is useful because on some computers, the minimum time the key is pressed is longer than the frame rate and you miss some slides.

### Combats

* ajout d'un sélecteur de Pokemon
    * ajout du graphics `ICMonFightPokemonSelectionGraphics`
    * ajout de la méthode `createPokemonSelector`
    * ajout de la méthode

### Pokémons

* création d'une aire vide `MyPocket` dédiée aux pokémons qui ne sont jamais affichés, permet d'éviter de faire un choix arbitraire et sans sens

### Actors

* nouveau scaleFactor

### Events

* Meilleure abstraction de `CollectItemEvent` (nouvelles classes `CollectBallEvent`, `CollectGiftEvent`).
