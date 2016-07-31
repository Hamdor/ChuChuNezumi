package controller;

import java.util.ArrayList;
import java.util.List;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.inventory.ItemBag;
import com.pokegoapi.api.inventory.PokeBank;
import com.pokegoapi.api.inventory.Pokeball;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.fort.PokestopLootResult;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;
import com.pokegoapi.api.map.pokemon.EncounterResult;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import POGOProtos.Enums.PokemonIdOuterClass.PokemonId;
import POGOProtos.Inventory.Item.ItemAwardOuterClass.ItemAward;
import POGOProtos.Inventory.Item.ItemIdOuterClass.ItemId;
import POGOProtos.Networking.Responses.CatchPokemonResponseOuterClass.CatchPokemonResponse;
import main.util.Printers;
import main.util.Waypoint;

public class Walker implements IBehavior {

  private ArrayList<Waypoint> waypoints;
  private int current;
  private boolean fwd;
  private PokemonGo api;
  private Controller controller;
  
  private final static int EXCEPTION_RETRIES = 5;

  public Walker(final ArrayList<Waypoint> waypoints, PokemonGo impl, Controller controller) {
    this.waypoints = waypoints;
    this.current = 0;
    this.fwd = true;
    this.api = impl;
    this.controller = controller;
  }

  @Override
  public void changeLocation() {
    current += (fwd ? 1 : -1);
    if (current <= 0) {
      fwd = true;
      current = 0;
    } else if (current >= waypoints.size() - 1) {
      fwd = false;
      current = waypoints.size() - 1;
    }
    final Waypoint wp = waypoints.get(current);
    api.setLocation(wp.getLatitude(), wp.getLongitude(), 0);
    controller.onPositionChanged(wp);
  }

  @Override
  public int getSleepTime() {
    return 1; // sleep 1 sec...
  }

  @Override
  public CatchResult tryCatchPokemon(CatchablePokemon pokemon) throws LoginFailedException, RemoteServerException {
    CatchResult result = null;
    EncounterResult encResult = pokemon.encounterPokemon();
    if (encResult.wasSuccessful()) {
      Pokeball pokeball;
      // TODO: Make sensitive to catch chances :-)
      ItemBag bag = api.getInventories().getItemBag();
      if (bag.getItem(ItemId.ITEM_POKE_BALL).getCount() > 0) {
        pokeball = Pokeball.POKEBALL;
      } else if (bag.getItem(ItemId.ITEM_GREAT_BALL).getCount() > 0) {
        pokeball = Pokeball.GREATBALL;
      } else if (bag.getItem(ItemId.ITEM_ULTRA_BALL).getCount() > 0) {
        pokeball = Pokeball.ULTRABALL;
      } else {
        pokeball = Pokeball.MASTERBALL;
      }
      result = pokemon.catchPokemon(pokeball, bag.getItem(pokeball.getBallType()).getCount(), 0);
    }
    controller.onPokemonCatch(pokemon, result);
    return result;
  }
  
  void filterPokemon(PokemonId pokemon) throws LoginFailedException, RemoteServerException {
    transferPokemon(pokemon, 2);
  }
  
  void filterAllPokemons() throws LoginFailedException, RemoteServerException {
    for (PokemonId p : PokemonId.values())
      filterPokemon(p);
  }

  @Override
  public void onCatchedPokemon(CatchablePokemon pokemon, CatchResult result) throws LoginFailedException, RemoteServerException {
    System.out.println("Attempt to catch: " + pokemon.getPokemonId()  + " " + result.getStatus());
    // Filter to send pokemons to prof
    if (result.getStatus()  == CatchPokemonResponse.CatchStatus.CATCH_SUCCESS)
      filterPokemon(pokemon.getPokemonId());
  }
  
  private void useItem(ItemId itemId, int num) {
    Item item = api.getInventories().getItemBag().getItem(itemId);
    if (item.getCount() > 0) {
      // TODO: Use item...
    }
  }

  @Override
  public void tryLootPokestop(Pokestop stop) throws LoginFailedException, RemoteServerException {
    if (stop.canLoot()) {
      System.out.println("Reached stop: " + stop.getDetails().getName());
      PokestopLootResult res = stop.loot();
      Printers.print(res);
      // If we are awarded with an incense, use it :-)
      // TODO: Check if we are still buffed
      for (ItemAward i : res.getItemsAwarded())
        if (i.getItemId() == ItemId.ITEM_INCENSE_ORDINARY)
          useItem(ItemId.ITEM_INCENSE_ORDINARY, 1);
    }
  }
  
  private void dropItem(ItemId id, int keep) {
    ItemBag bag = api.getInventories().getItemBag();
    Item item = bag.getItem(id);
    int toDrop = 0;
    if (item.getCount() > keep)
      toDrop = (keep - item.getCount()) * -1;
    if (toDrop > 0) {
      int tries = 0;
      boolean retry = false;
      do {
        try {
          retry = false;
          bag.removeItem(id, toDrop);
        } catch (RemoteServerException | LoginFailedException e) {
          e.printStackTrace();
          retry = true;
          tries++;
        }
      } while(tries < EXCEPTION_RETRIES && retry);
    }
  }

  @Override
  public void onInventoryFull() throws LoginFailedException, RemoteServerException {
    // Drop potions and revivers
    dropItem(ItemId.ITEM_POTION, 1);
    dropItem(ItemId.ITEM_REVIVE, 1);
    dropItem(ItemId.ITEM_POKE_BALL, 60);
    dropItem(ItemId.ITEM_GREAT_BALL, 30);
  }
  
  private void transferPokemon(PokemonId id, int keep) throws LoginFailedException, RemoteServerException {
    PokeBank bank = api.getInventories().getPokebank();
    List<Pokemon> pokemons = bank.getPokemonByPokemonId(id);
    if (pokemons.size() == 1 || pokemons.size() <= keep)
      return;
    // Find strongest and keep
    int max = 0;
    for (Pokemon p : pokemons)
      max = Math.max(p.getStamina(), max);
    int send = pokemons.size() - keep;
    for (Pokemon p : pokemons)
      if (p.getStamina() != max && ! p.isFavorite()) {
        int tries = 0;
        boolean retry = false;
        do {
          try {
            retry = false;
            p.transferPokemon();
            if (--send == 0)
              return;
          } catch (RemoteServerException | LoginFailedException e) {
            e.printStackTrace();
            retry = true;
            tries++;
          }
        } while(tries < EXCEPTION_RETRIES && retry);
      }
  }

  @Override
  public void onPokemonFull() throws LoginFailedException, RemoteServerException {
    transferPokemon(PokemonId.PIDGEY, 1);
    transferPokemon(PokemonId.RATTATA, 1);
    transferPokemon(PokemonId.SPEAROW, 1);
    transferPokemon(PokemonId.ZUBAT, 1);
    transferPokemon(PokemonId.WEEDLE, 1);
  }
}
