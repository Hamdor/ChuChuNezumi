package view;

import java.util.concurrent.atomic.AtomicInteger;

import controller.Controller;

public class View {

  public View() {
    windowCount = new AtomicInteger();
    // TODO: Bot settings window (Pause/Resume/Behavior/etc.)
    // TODO: Google maps view with current location and console
    // TODO: Inventory window
    // TOOD: Pokebank Window ==> Very simular to pokedecks, but lists all pokemons, with strength etc
    // TODO: Main Window ==> This window can create instances of all other windows (reopen them)
    pokedeck = new PokedeckWindow(this, false);
    map = new MapWindow();
    catched = new CatchWindow();
  }
  
  void updateViews() {
    // TODO: Update all open windows
    pokedeck.update();
  }
  
  void newWindow() {
    // Increase window count
    windowCount.getAndIncrement();
  }
  
  public void closedWindow() {
    if (windowCount.getAndDecrement() >= 1) {
      // TODO: Close application
      //       This also includes to stop the bot correctly
    }
  }
  
  public int getWindowCounter() { return windowCount.get(); }
  
  // ----- Controller getter/setter--------------------------------------------
  
  public void setController(final Controller controller) {
    this.controller = controller;
    controller.register(map.getListener());
    controller.register(pokedeck.getListener());
    controller.register(catched.getListener());
  }
  
  public Controller getController() { return controller; }

  // ----- Window getter ------------------------------------------------------
  
  public PokedeckWindow getPokedeck() { return pokedeck; }
  
  public MapWindow getMapWindow() { return map; }
  
  public CatchWindow getCatchWindow() { return catched; }
  

  // ----- Variables ----------------------------------------------------------
  
  private AtomicInteger windowCount;
  private Controller controller = null;
  
  private final PokedeckWindow pokedeck;
  private final MapWindow map;
  private final CatchWindow catched;
  
  // --------------------------------------------------------------------------
}
