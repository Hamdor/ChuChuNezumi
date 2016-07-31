package main;

import view.View;

public class GUIStarter {
  
  public static void main(String args[]) {
    View v = new View();
    v.getCatchWindow().getListener().onPokemonCatch(null, null);
  }
}
