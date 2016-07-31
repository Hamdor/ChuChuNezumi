package controller;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.pokemon.Pokemon;

import POGOProtos.Enums.PokemonIdOuterClass.PokemonId;
import controller.events.EventHandler;
import model.Model;
import view.View;

public class Controller extends EventHandler {

  public Controller(final View view, final Model model) {
    this.view  = view;
    this.model = model;
    this.api   = model.getApi();
    initPokedeck();
  }
  
  // --------------------------------------------------------------------------
  
  public void initPokedeck() {
    for (Pokemon p : api.getInventories().getPokebank().getPokemons())
      view.getPokedeck().addPokemon(p.getPokemonId().getNumber());
  }
  
  public PokemonId getPokemonIdByNumber(int number) {
    return PokemonId.internalGetValueMap().findValueByNumber(number);
  }
  
  // --------------------------------------------------------------------------

  public void pauseBot() {
    // TODO: Implement me
  }
  
  public void resumeBot() {
    // TODO: Implement me
  }
  
  public void setBot(final Bot bot) {
    this.bot = bot;
    this.bot.start();
  }
  
  public Bot getBot() { return bot; }
  
  // --------------------------------------------------------------------------
  
  private View view;
  private Model model;
  
  private Bot bot;
  
  private PokemonGo api;
  
  public View getView() { return view; }
  public Model getModel() { return model; }
}
