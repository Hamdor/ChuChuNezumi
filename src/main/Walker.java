package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Item;
import com.pokegoapi.api.inventory.ItemBag;
import com.pokegoapi.api.inventory.PokeBank;
import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;
import com.pokegoapi.api.map.pokemon.EncounterResult;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import POGOProtos.Enums.PokemonIdOuterClass.PokemonId;
import POGOProtos.Inventory.Item.ItemIdOuterClass.ItemId;
import main.util.Printers;

public class Walker implements IBehavior {

  private ArrayList<Waypoint> waypoints;
  private int current;
  private boolean fwd;
  private int tOutBegin;
  private int tOutEnd;
  private PokemonGo api;

  public Walker(final ArrayList<Waypoint> waypoints, int tOutBegin, int tOutEnd, PokemonGo impl) {
    this.waypoints = waypoints;
    this.current = 0;
    this.fwd = true;
    this.tOutBegin = tOutBegin;
    this.tOutEnd = tOutEnd;
    this.api = impl;
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
  }

  @Override
  public int getSleepTime() {
    return new Random().nextInt(tOutEnd - tOutBegin) + tOutBegin;
  }

  @Override
  public CatchResult tryCatchPokemon(CatchablePokemon pokemon) throws LoginFailedException, RemoteServerException {
    EncounterResult encResult = pokemon.encounterPokemon();
    if (encResult.wasSuccessful()) {
      System.out.println("Encounted: " + pokemon.getPokemonId());
      Printers.print(encResult.getCaptureProbability());
      return pokemon.catchPokemon();
    }
    return null;
  }

  @Override
  public void onCatchedPokemon(CatchablePokemon pokemon, CatchResult result) {
    System.out.println("Attempt to catch: " + pokemon.getPokemonId()  + " " + result.getStatus());
  }

  @Override
  public void tryLootPokestop(Pokestop stop) throws LoginFailedException, RemoteServerException {
    if (stop.canLoot())
      Printers.print(stop.loot());
  }
  
  private void dropItem(ItemId id, int keep) {
    ItemBag bag = api.getInventories().getItemBag();
    Item item = bag.getItem(id);
    if (item.getCount() > keep)
      item.setCount(keep);
  }

  @Override
  public void onInventoryFull() throws LoginFailedException, RemoteServerException {
    // Drop potions and revivers
    dropItem(ItemId.ITEM_POTION, 10);
    dropItem(ItemId.ITEM_REVIVE, 10);
  }
  
  private void transferPokemon(PokemonId id) throws LoginFailedException, RemoteServerException {
    PokeBank bank = api.getInventories().getPokebank();
    List<Pokemon> pokemons = bank.getPokemonByPokemonId(id);
    // Find strongest and keep
    int max = 0;
    for (Pokemon p : pokemons)
      max = Math.max(p.getStamina(), max);
    for (Pokemon p : pokemons)
      if (p.getStamina() != max && ! p.getFavorite())
        p.transferPokemon();
  }

  @Override
  public void onPokemonFull() throws LoginFailedException, RemoteServerException {
    transferPokemon(PokemonId.PIDGEY);
    transferPokemon(PokemonId.RATTATA);
  }
}