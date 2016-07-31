package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;

import controller.events.EventListenerBase;
import controller.events.IEventListener;

public class CatchWindow extends JFrame {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private JTextArea console;

  CatchWindow() {
    super("Catch log");
    listener = new EventListener();
    JPanel middlePanel = new JPanel();
    add(middlePanel);
    console = new JTextArea(16, 58);
    console.setEditable(false);
    JScrollPane scroll = new JScrollPane(console);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
                                      VERTICAL_SCROLLBAR_ALWAYS);
    middlePanel.add(scroll);
    middlePanel.setSize(new Dimension(80, 40));
    setResizable(false);
    setVisible(true);
    pack();
  }

  // --- Eventlistener
  class EventListener extends EventListenerBase {
    @Override
    public void onPokemonCatch(CatchablePokemon pokemon, CatchResult result) {
      String entry = pokemon.getPokemonId().name() + " appeared ";
      entry += "Result: " + (result != null ? result.getStatus() : "FLEED");
      console.append(entry + "\n");
    }
  };
  
  private EventListener listener;
  
  public IEventListener getListener() { return listener; }
  
}
