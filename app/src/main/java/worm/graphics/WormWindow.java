package worm.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import worm.Tile;

public class WormWindow extends JPanel {
  public WormWindow() {
    super();
    JFrame frame = new JFrame("Worm");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(640, 640);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics);

    super.paintComponent(graphics);

    Tile[][] tiles = {
      {Tile.Grass, Tile.Grass, null      , null      , null, null},
      {null      , null      , Tile.Grass, null      , null, null},
      {null      , Tile.Grass, Tile.Grass, Tile.Grass, null, null},
      {null      , null      , Tile.Grass, null      , null, null},
      {null      , Tile.Grass, Tile.Grass, null      , null, null},
      {null      , null      , null      , null      , Tile.Grass, null},
      {null      , null      , null      , null      , null, null},
    };

    wrg.drawTiles(tiles);
  }
}
