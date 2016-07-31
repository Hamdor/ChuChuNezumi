package controller.events;

import java.util.HashSet;
import java.util.Set;

import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.fort.PokestopLootResult;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;

import main.util.Waypoint;

public class EventHandler implements IEventListener {
  
  private Set<IEventListener> listeners = new HashSet<IEventListener>();
  
  public void register(IEventListener listener) {
    if (listener != null)
      listeners.add(listener);
  }
  
  public void unregister(IEventListener listener) {
    if (listener != null)
      listeners.remove(listener);
  }

  @Override
  public void onPositionChanged(Waypoint wp) {
    for (IEventListener l : listeners)
      if (l != null) l.onPositionChanged(wp);
  }

  @Override
  public void onPokemonEncounted(CatchablePokemon pokemon) {
    for (IEventListener l : listeners)
      if (l != null) l.onPokemonEncounted(pokemon);
  }

  @Override
  public void onPokemonFlee(CatchablePokemon pokemon, CatchResult result) {
    for (IEventListener l : listeners)
      if (l != null) l.onPokemonFlee(pokemon, result);
  }

  @Override
  public void onPokemonCatch(CatchablePokemon pokemon, CatchResult result) {
    for (IEventListener l : listeners)
      if (l != null) l.onPokemonCatch(pokemon, result);
  }

  @Override
  public void onPokestopReached(Pokestop stop, PokestopLootResult result) {
    for (IEventListener l : listeners)
      if (l != null) l.onPokestopReached(stop, result);
  }

  @Override
  public void onInventoryFull() {
    for (IEventListener l : listeners)
      if (l != null) l.onInventoryFull();
  }

  @Override
  public void onPokemonFull() {
    for (IEventListener l : listeners)
      if (l != null) l.onPokemonFull();
  }
}
