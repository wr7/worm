package worm.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import worm.Direction;
import worm.Level;

public class WormWindow extends JPanel {
  public Level levelToResetTo;
  public Level currentLevel;

  private Level getCurrentLevel() {
    return currentLevel;
  }

  /**
  * Gets the current level. 
  *
  * This method HAS to exist because of weird Java shenanigans.
    */
  private void resetLevel() {
      currentLevel = levelToResetTo.clone();
  }

  public WormWindow(Level currentLevel) {
    super();
    
    this.currentLevel = currentLevel;
    this.levelToResetTo = currentLevel.clone();

    JFrame frame = new JFrame("Worm");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);

    // Add keyboard bindings //

    Action resetLevel = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          resetLevel();
          repaint();
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("R"),
                                "resetLevel");
    this.getActionMap().put("resetLevel",
                                 resetLevel);

    Action moveUp = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          getCurrentLevel().moveInDirection(Direction.Up);
          repaint();
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("W"),
                                "moveUp");
    this.getActionMap().put("moveUp",
                                 moveUp);
    
    Action moveDown = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          getCurrentLevel().moveInDirection(Direction.Down);
          repaint();
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("S"),
                                "moveDown");
    this.getActionMap().put("moveDown",
                                 moveDown);

    Action moveLeft = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          getCurrentLevel().moveInDirection(Direction.Left);
          repaint();
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("A"),
                                "moveLeft");
    this.getActionMap().put("moveLeft",
                                 moveLeft);
    
    Action moveRight = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          getCurrentLevel().moveInDirection(Direction.Right);
          repaint();
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("D"),
                                "moveRight");
    this.getActionMap().put("moveRight",
                                 moveRight);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(384, 448);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    // Kinda a janky place to put this, but whatever
    if(!currentLevel.alive) {
      resetLevel();
    }
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics, getWidth(), getHeight());

    super.paintComponent(graphics);

    wrg.drawLevel(this.currentLevel);
  }
}
