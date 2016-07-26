package main;

import java.util.Collection;
import java.util.List;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

class Bot {
  private static final int MS_TO_SEC = 1000;
  private static final boolean DEBUG = true;

  public Bot(PokemonGo go, IBehavior behavior) {
    this.api = go;
    this.behavior = behavior;
  }

  public void run() throws InterruptedException, LoginFailedException, RemoteServerException {
    while (true) {
      // ---- Simulate some idle...
      int sleepTime = behavior.getSleepTime() * MS_TO_SEC;
      Thread.sleep(sleepTime);
      // ------------------------------------------------------------------
      // ---- Change location
      behavior.changeLocation();
      if (DEBUG)
        System.out.println("Lat: " + api.getLatitude() + " Long: " + api.getLongitude());
      // ------------------------------------------------------------------
      // Get catchable pokemons and catch
      for (CatchablePokemon cp : api.getMap().getCatchablePokemon()) {
        CatchResult res = behavior.tryCatchPokemon(cp);
        if (res != null)
          behavior.onCatchedPokemon(cp, res);
      }
      // ------------------------------------------------------------------
      // ------ Collect pokestops
      for (Pokestop ps : api.getMap().getMapObjects().getPokestops())
        behavior.tryLootPokestop(ps);
      // ------ Pokebank full?
      List<Pokemon> pokemons = api.getInventories().getPokebank().getPokemons();
      if (pokemons.size() >= 250) {
        // Inventory is full
        behavior.onPokemonFull();
      }
      // ------ Inventory full?
      Collection<Item> items = api.getInventories().getItemBag().getItems();
      int itemCount = 0;
      for (Item i : items)
        itemCount += i.getCount();
      if (itemCount >= 350)
        behavior.onInventoryFull();
    }
  }
  IBehavior behavior;
  final PokemonGo api;
};
