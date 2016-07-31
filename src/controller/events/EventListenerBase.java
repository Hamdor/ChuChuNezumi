package controller.events;

import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.fort.PokestopLootResult;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;

import main.util.Waypoint;

public class EventListenerBase implements IEventListener {

  @Override
  public void onPositionChanged(Waypoint wp) {
    // nop
  }

  @Override
  public void onPokemonEncounted(CatchablePokemon pokemon) {
    // nop
  }

  @Override
  public void onPokemonFlee(CatchablePokemon pokemon, CatchResult result) {
    // nop
  }

  @Override
  public void onPokemonCatch(CatchablePokemon pokemon, CatchResult result) {
    // nop
  }

  @Override
  public void onPokestopReached(Pokestop stop, PokestopLootResult result) {
    // nop
  }

  @Override
  public void onInventoryFull() {
    // nop
  }

  @Override
  public void onPokemonFull() {
    // nop
  }
}
