package view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.events.EventListenerBase;
import controller.events.IEventListener;
import main.util.Waypoint;

public class MapWindow extends JFrame {
  
  /**
   * 
   */
  private static final long serialVersionUID = -9003548770073036892L;
  
  // TODO: Make more configurable
  // ==> Add function which fills in arguments into query
  // TODO: Clean up

  private final static String MAPS_REQ = "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=16&size=412x412&scale=1&maptype=roadmap";

  private JLabel mapLabel = null;
  private Waypoint lastPosition = null;
  
  public MapWindow() {
    super("Bot Location");
    this.setSize(new Dimension(412, 412));
    this.setResizable(false);
    this.setVisible(true);
    this.mapLabel = new JLabel();
    this.listener = new EventListener();
    add(mapLabel);
  }
  
  // --- Eventlistener
  class EventListener extends EventListenerBase {
    @Override
    public void onPositionChanged(Waypoint wp) {
      // Prevent too much updates
      if (lastPosition != null && wp.distance(lastPosition) < 10)
        return;
      lastPosition = wp;
      // Update image
      URL url = null;
      try {
        url = new URL(String.format(MAPS_REQ, wp.getLatitude(),
            wp.getLongitude()) + String.format("&markers=size:mid%%7Ccolor:0xff0000%%7Clabel:1%%7C%f+%f&key=", wp.getLatitude(), wp.getLongitude()) 
            + "AIzaSyCJ_2P_sm_iHiqQbGK2YlxtnxGIwKpJtY4"); // TODO: KEy
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      BufferedImage c = null;
      try {
        c = ImageIO.read(url);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (c != null)
        mapLabel.setIcon(new ImageIcon(c));
    }
  };
  
  private EventListener listener;
  
  public IEventListener getListener() { return listener; }
}
