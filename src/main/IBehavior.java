package main;

import com.pokegoapi.api.map.fort.Pokestop;
import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

/**
 */
interface IBehavior {
  /**
   */
  void changeLocation();
  
  /**
   * @throws RemoteServerException 
   * @throws LoginFailedException 
   */
  CatchResult tryCatchPokemon(CatchablePokemon pokemon) throws LoginFailedException, RemoteServerException;

  /**
   * @param cp 
   */
  void onCatchedPokemon(CatchablePokemon cp, CatchResult result);

  /**
   * @throws RemoteServerException 
   * @throws LoginFailedException 
   */
  void tryLootPokestop(Pokestop stop) throws LoginFailedException, RemoteServerException;
  
  /**
   * @throws RemoteServerException 
   * @throws LoginFailedException 
   */
  void onInventoryFull() throws LoginFailedException, RemoteServerException;

  /**
   * @throws RemoteServerException 
   * @throws LoginFailedException 
   */
  void onPokemonFull() throws LoginFailedException, RemoteServerException;
  
  /**
   */
  int getSleepTime();
}
