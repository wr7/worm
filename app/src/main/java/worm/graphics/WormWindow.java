package worm.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import worm.Level;
import worm.Tile;
import worm.TilePosition;

public class WormWindow extends JPanel {
  public Level currentLevel;

  public WormWindow(Level currentLevel) {
    super();
    
    this.currentLevel = currentLevel

    JFrame frame = new JFrame("Worm");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);

    Action moveUp = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          currentLevel.moveInDirection(Direction.Up);
          repaint();

        }
    };
    this.getInputMap().put(KeyStroke.getKeyStroke("W"),
                                "moveUp");
    this.getActionMap().put("moveUp",
                                 moveUp);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(384, 448);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics, getWidth(), getHeight());

    super.paintComponent(graphics);

    wrg.drawLevel(this.currentLevel);
  }
}
