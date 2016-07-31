package controller.events;

import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.fort.PokestopLootResult;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;

import main.util.Waypoint;

public interface IEventListener {
  
  void onPositionChanged(Waypoint wp);
  
  void onPokemonEncounted(CatchablePokemon pokemon);
  
  void onPokemonFlee(CatchablePokemon pokemon, CatchResult result);
  
  void onPokemonCatch(CatchablePokemon pokemon, CatchResult result);
  
  void onPokestopReached(Pokestop stop, PokestopLootResult result);
  
  void onInventoryFull();
  
  void onPokemonFull();
}
