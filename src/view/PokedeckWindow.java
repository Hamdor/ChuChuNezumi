package view;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.pokegoapi.api.map.pokemon.CatchResult;
import com.pokegoapi.api.map.pokemon.CatchablePokemon;

import POGOProtos.Enums.PokemonIdOuterClass.PokemonId;
import controller.events.EventListenerBase;
import controller.events.IEventListener;

public class PokedeckWindow extends JFrame {
  
  private static final int MAX_ID = 147;
  private static final int ROWS = 21;
  private static final int COLS = 5;
  private static final int ABSTAND = 1;

  /**
   * 
   */
  private static final long serialVersionUID = -248865099280287738L;
  // --- Components
  private JLabel pokemons[] = new JLabel[MAX_ID];
  
  public PokedeckWindow(final View parent, boolean enableAll) {
    super("Pokedeck");
    listener = new EventListener();
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: Remove me ==> Window counter ==> if counter == 0 ==> exit...
    setLayout(new GridLayout(ROWS, COLS, ABSTAND,ABSTAND));
    for (int i = 0; i < MAX_ID; ++i) {
      add(pokemons[i] = new JLabel(new ImageIcon("/Users/Hamdor/pokebilder/" + String.format("%03d", i+1) + ".png")));
      pokemons[i].addMouseListener(new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
          // nop
        }

        @Override
        public void mousePressed(MouseEvent e) {
          // nop
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            JLabel origin = (JLabel)e.getComponent();
            int pokemonNr = findLabelIdx(origin) + 1;
            if (pokemonNr == 0)
              return; // Invalid number
            PokemonId id = parent.getController().getPokemonIdByNumber(pokemonNr);
            try {
              Desktop.getDesktop().browse(new URI("http://www.pokewiki.de/" +
                                          id.toString().toLowerCase()));
            } catch (IOException | URISyntaxException e1) {
              e1.printStackTrace();
            }
          }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          // nop
        }

        @Override
        public void mouseExited(MouseEvent e) {
          // nop
        }
      });
    }
    // Disable all
    if (! enableAll)
      for (int i = 0; i < MAX_ID; ++i)
        pokemons[i].setEnabled(false);
    pack();
  }
  
  private int findLabelIdx(JLabel searched) {
    for (int i = 0; i < MAX_ID; ++i)
      if (pokemons[i] == searched) return i;
    return -1;
  }
  
  public void addPokemon(int id) {
    if (id > 0 && id < MAX_ID + 1)
      pokemons[id-1].setEnabled(true);
  }
  
  public void removePokemon(int id) {
    if (id > 0 && id < MAX_ID + 1)
      pokemons[id-1].setEnabled(false);
  }

  public void update() {
    // TODO: Complete update window
    // ==> Fetch all pokedecks informations from api
    //     and toggle pokemons correctly
  }
  
  // --- Eventlistener
  class EventListener extends EventListenerBase {
    @Override
    public void onPokemonCatch(CatchablePokemon pokemon, CatchResult result) {
      if (! result.isFailed())
        addPokemon(pokemon.getPokemonId().getNumber());
    }
  };
  
  private EventListener listener;
  
  public IEventListener getListener() { return listener; }
  
}
