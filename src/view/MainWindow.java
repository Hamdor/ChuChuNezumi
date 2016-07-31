package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainWindow {
  private JFrame frame;
  
  // -- Components
  private JTextArea console;
  
  public MainWindow() {
    frame = new JFrame("Chu Chu Nezumi");
    //frame.setSize(1920, 1080);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // ---- Create button pane
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new GridLayout(4,2,1,1));
    for (int i = 0; i < 4 * 2; ++i)
      buttonPane.add(new JButton());
    frame.getContentPane().add(buttonPane, BorderLayout.WEST);
    // ---- Maps
    frame.getContentPane().add(new JButton(), BorderLayout.CENTER);
    // ---- Player Values
    frame.getContentPane().add(new JButton(), BorderLayout.EAST);
    // ---- Console
    console = new JTextArea();
    console.setMinimumSize(new Dimension(1000,100));
    
    frame.getContentPane().add(new JTextArea(), BorderLayout.SOUTH);
    frame.pack();
  }
}
