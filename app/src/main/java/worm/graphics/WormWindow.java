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
import worm.LevelFile;

/**
 * This represents the window used to play the game. 
 * Code for switching between/resetting levels and handling keyboard presses lives here
 *
 * The actual graphics code lives in WormGraphics
*/
public class WormWindow extends JPanel {
  // The actual window object
  JFrame window;

  // The size (height and width) of each tile in pixels
  // THIS SHOULD BE A MULTIPLE OF 32
  public final static int TILE_SIZE = 64;

  // The current level number; 0 is the first level
  private int levelNumber = 0;
  private Level levelToResetTo;
  private Level currentLevel;

  // Moves the worm and resets/switches the level if necessary
  private void move(Direction d) {
    currentLevel.moveInDirection(d);

    if(currentLevel.levelCleared) {
      loadNextLevel();
    } else if(!currentLevel.alive) {
      resetLevel();
    }

    repaint();
  }

  public WormWindow() {
    super();
    
    // Get the first level //
    this.levelToResetTo = LevelFile.getLevels()[0];
    currentLevel = levelToResetTo.clone();

    // Create the actual window //
    window = new JFrame("Worm");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(this);
    window.pack();
    window.setVisible(true);

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
          move(Direction.Up);
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("W"),
                                "moveUp");
    this.getActionMap().put("moveUp",
                                 moveUp);
    
    Action moveDown = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          move(Direction.Down);
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("S"),
                                "moveDown");
    this.getActionMap().put("moveDown",
                                 moveDown);

    Action moveLeft = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          move(Direction.Left);
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("A"),
                                "moveLeft");
    this.getActionMap().put("moveLeft",
                                 moveLeft);
    
    Action moveRight = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          move(Direction.Right);
        }
    };

    this.getInputMap().put(KeyStroke.getKeyStroke("D"),
                                "moveRight");
    this.getActionMap().put("moveRight",
                                 moveRight);
  }

  /**
   * Resets the level and sets the appropriate window size.
  */
  private void resetLevel() {
    currentLevel = levelToResetTo.clone();
    setSize(levelToResetTo.tiles[0].length * TILE_SIZE, levelToResetTo.tiles.length * TILE_SIZE);
    window.pack();
  }

  private void loadNextLevel() {
    levelNumber += 1;
    Level[] levels = LevelFile.getLevels();

    if(levelNumber < levels.length) {
      levelToResetTo = levels[levelNumber];
    }

    resetLevel();
  }

  @Override
  public Dimension getPreferredSize() {
      return new Dimension(levelToResetTo.tiles[0].length * TILE_SIZE, levelToResetTo.tiles.length * TILE_SIZE);
  }

  /**
   * Called when the window needs to be redrawn.
  */
  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics, getWidth(), getHeight());

    super.paintComponent(graphics);

    wrg.drawLevel(this.currentLevel);
  }
}
