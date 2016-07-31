package controller;

import java.util.Collection;
import java.util.List;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.EggIncubator;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

// TODO: Rewrite bot to not use behavior and just use a single class...
// TODO: Use event handler to signalize catch of pokemons, reach of stops, etc.

public class Bot extends Thread {
  private static final int MS_TO_SEC = 1000;

  public Bot(Controller controller, PokemonGo go, IBehavior behavior/*, PokedeckWindow pokedeck*/) {
    this.api = go;
    this.behavior = behavior;
    this.controller = controller;
  }

  @Override
  public void run() {
    // Main loop of bot
    while (true) {
      // ---- Simulate some idle...
      int sleepTime = behavior.getSleepTime() * MS_TO_SEC;
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // ------------------------------------------------------------------
      // ---- Change location
      behavior.changeLocation();
      // ------------------------------------------------------------------
      // Get catchable pokemons and catch
      try {
        for (CatchablePokemon cp : api.getMap().getCatchablePokemon()) {
          CatchResult res = behavior.tryCatchPokemon(cp);
          if (res != null)
            behavior.onCatchedPokemon(cp, res);
        }
      } catch (LoginFailedException | RemoteServerException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      // ------ Collect pokestops
      {
        int tries = 0;
        Collection<Pokestop> pokestops = null;
        boolean retry = false;
        do {
          retry = false;
          try {
            pokestops = api.getMap().getMapObjects().getPokestops();
          } catch (LoginFailedException | RemoteServerException e) {
            e.printStackTrace();
          }
        } while(retry && tries < 5);
        //if (pokestops == null)
        //  return; // Will cause mainloop to exit...
        if (pokestops != null)
          for (Pokestop ps : pokestops)
            try {
              behavior.tryLootPokestop(ps);
            } catch (LoginFailedException | RemoteServerException e) {
              e.printStackTrace();
            }
      }
      // ------ Pokebank full?
      List<Pokemon> pokemons = api.getInventories().getPokebank().getPokemons();
      if (pokemons.size() >= 241)
        try {
          behavior.onPokemonFull();
        } catch (LoginFailedException | RemoteServerException e) {
          e.printStackTrace();
        }
      // ------ Inventory full?
      Collection<Item> items = api.getInventories().getItemBag().getItems();
      int itemCount = 0;
      for (Item i : items)
        itemCount += i.getCount();
      if (itemCount >= 350)
        try {
          behavior.onInventoryFull();
        } catch (LoginFailedException | RemoteServerException e) {
          e.printStackTrace();
        }
      // TODO: Check eggs
      // TODO: Use eggs which have reached their target reach
      // TODO: Put unused eggs in incubators
      for (EggIncubator i : api.getInventories().getIncubators())
        if (i.isInUse())
          if (i.getKmWalked() >= i.getKmTarget()) System.out.println("Egg is useable!!");
    }
  }
  IBehavior behavior;
  final PokemonGo api;
  final Controller controller;
};
