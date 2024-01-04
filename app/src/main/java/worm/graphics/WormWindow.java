package worm.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import worm.Tile;
import worm.TilePosition;

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
    return new Dimension(384, 448);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics);

    super.paintComponent(graphics);

    Tile[][] tiles = {
      {null      , null      , null      , null      , null      , null      , },
      {null      , null      , null      , null      , null      , null      , },
      {null      , null      , null      , null      , null      , null      , },
      {null      , null      , null      , null      , null      , null      , },
      {null      , null      , null      , null      , null      , Tile.Grass, },
      {null      , null      , Tile.Grass, null      , Tile.Grass, Tile.Grass, },
      {null      , Tile.Grass, Tile.Grass, Tile.Grass, Tile.Grass, Tile.Grass, },
    };

    List<TilePosition> worm = Arrays.asList(new TilePosition[] {
      new TilePosition(0, 6),
      new TilePosition(0, 5),
      new TilePosition(1, 5),
      new TilePosition(1, 4),
      new TilePosition(1, 3),
    });

    wrg.drawImage(Sprite.SkyBackground.getFullImage(), this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight(), 0);
    wrg.drawTiles(tiles);
    wrg.drawWorm(worm);
  }
}
