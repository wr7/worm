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

public class WormWindow extends JPanel {
  JFrame window;

  private int levelNumber = 0;
  private Level levelToResetTo;
  private Level currentLevel;

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
    
    this.levelToResetTo = LevelFile.getLevels()[0];
    currentLevel = levelToResetTo.clone();

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

  private void resetLevel() {
    currentLevel = levelToResetTo.clone();
    setSize(levelToResetTo.tiles[0].length * 64, levelToResetTo.tiles.length * 64);
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
      return new Dimension(levelToResetTo.tiles[0].length * 64, levelToResetTo.tiles.length * 64);
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
