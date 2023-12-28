package worm.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.geom.AffineTransform;

import worm.Tile;
import worm.TilePosition;
import worm.Direction;

/**
 * A wrapper for drawing things for the game 'worm'.
 * The drawSprite method can be used to draw sprites to the window.
*/
public class WormGraphics {
  private final Graphics2D g2d;

  public WormGraphics(Graphics2D g2d) {
    this.g2d = g2d;
  }

  public void drawTiles(Tile[][] tiles) {
    for(int row = 0; row < tiles.length; row++) {
      for(int col = 0; col < tiles[row].length; col++) {
        if(tiles[row][col] == null)
          continue;

        // Calculate ConnectionState (see what sides this tile is connected to) //
        final Direction[] directions = {Direction.Left, Direction.Right, Direction.Up, Direction.Down};
        boolean[] connectsTo = {false,false,false,false};

        for(int i = 0; i < directions.length; i++) {
          TilePosition neighboringTile  = new TilePosition(col, row).nextInDirection(directions[i]);

          if(
              neighboringTile.x < tiles[0].length && neighboringTile.x >= 0 &&
              neighboringTile.y < tiles.length && neighboringTile.y >= 0
          ) {
            connectsTo[i] = tiles[row][col].canConnectTo(tiles[neighboringTile.y][neighboringTile.x]);
          } else {
            connectsTo[i] = tiles[row][col].canConnectToEdge();
          }
        }

        // calculate various parameters needed for drawing the sprite //

        BufferedImage[][] spriteTable = tiles[row][col].sprite.getImages();

        ConnectionState state = new ConnectionState(connectsTo[0],connectsTo[1],connectsTo[2],connectsTo[3]);
        int spriteCol = state.getColumn();
        int spriteRow = worm.Util.randomInt(row, col, 0, spriteTable.length - 1);
        int x = 64 * col + 32;
        int y = 64 * row + 32;
        double rotation = state.getRotation();

        drawImage(spriteTable[spriteRow][spriteCol], x, y, 64, 64, rotation);
      }
    }
  }

  /**
   * Draws the sprite with the specified, width, height, and rotation.
   *
   * @param image the image to draw
   * @param x the distance from the left of the window to the center of the image in pixels
   * @param y the distance from the top of the window to the center of the image in pixels
   * @param width the desired width of the image in pixels
   * @param height the desired height of the image in pixels
   * @param rotation the clockwise rotation of the image in radians
   */
  public void drawImage(BufferedImage image, double x, double y, int width, int height, double rotation) {
    double scale_x = width / ((double) image.getWidth());
    double scale_y = height / ((double) image.getHeight());

    AffineTransform transform = new AffineTransform();
    transform.translate(x, y);
    transform.rotate(rotation);
    transform.translate(-((double) width)/2, -((double) height)/2);
    transform.scale(scale_x, scale_y);

    g2d.drawImage(image, transform, null);
  }
}
