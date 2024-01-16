package worm.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.geom.AffineTransform;

import worm.Tile;
import worm.TilePosition;
import worm.Direction;
import worm.Level;

/**
 * A wrapper for drawing things for the game 'worm'.
 * <ul>
 *   <li>The {@link WormGraphics#drawWorm} method can be used to draw the worm to the window.</li>
 *   <li>The {@link WormGraphics#drawImage} method can be used to draw an image to the window.</li>
 *   <li>The {@link WormGraphics#drawTiles} method can be used to draw the tiles.</li>
 * </ul>
*/
public class WormGraphics {
  public final static int TILE_SIZE = WormWindow.TILE_SIZE;
  private final Graphics2D g2d;
  private final int width;
  private final int height;

  public WormGraphics(Graphics2D g2d, int width, int height) {
    this.g2d = g2d;
    this.width = width;
    this.height = height;
  }

  /**
   * Draws a level object
  */
  public void drawLevel(Level level) {
    drawImage(level.background.getFullImage(), width/2, height/2, width, height, 0);
    drawTiles(level.tiles);
    drawWorm(level.worm);
  }

  /** 
   * Draws a worm from a list of {@link TilePosition}s where the last position is the position of the head.
   *
   * @param worm_position
   * A list of the {@link TilePosition}s that the worm occupies. This list should end with the worm's head
   * and start with the worm's tail.
   * @throws IllegalArgumentException If the worm is shorter than two tiles.
   */
  private void drawWorm(List<TilePosition> worm_position) {
    if(worm_position.size() <= 1) {
      throw new IllegalArgumentException("Worm must be at-least two tiles long");
    }

    for(int i = 0; i < worm_position.size(); i++) {
      TilePosition tile = worm_position.get(i);
      boolean is_head = i == worm_position.size() - 1;

      // Find the previous and next tile if they exist
      // This is needed to compute how this segment connects to the other ones
      final TilePosition[] next_and_previous_tile;
      if(i == 0) {
        next_and_previous_tile = new TilePosition[] {worm_position.get(1)};
      } else if(i == worm_position.size() - 1) {
        next_and_previous_tile = new TilePosition[] {worm_position.get(i - 1)};
      } else {
        next_and_previous_tile = new TilePosition[] {worm_position.get(i - 1), worm_position.get(i + 1)};
      }

      // Calculate ConnectionState (see what sides this worm tile is connected to) //
      final Direction[] directions = {Direction.Left, Direction.Right, Direction.Up, Direction.Down};
      boolean[] connectsTo = {false,false,false,false};

      for(int j = 0; j < directions.length; j++) {
        TilePosition neighboringTile  = tile.nextInDirection(directions[j]);

        for(TilePosition tile2: next_and_previous_tile) {
          connectsTo[j] |= tile2.equals(neighboringTile);
        }
      }

      ConnectionState connectionState = new ConnectionState(connectsTo[0],connectsTo[1],connectsTo[2],connectsTo[3]);

      // Find the sprite that needs to be drawn for this segment
      BufferedImage image = connectionState.getType().getWormSprite(is_head).getFullImage();
      // Calculate the position and rotation needed
      double rotation = connectionState.getRotation();
      double x = TILE_SIZE * tile.x + TILE_SIZE / 2;
      double y = TILE_SIZE * tile.y + TILE_SIZE / 2;

      drawImage(image, x, y, TILE_SIZE, TILE_SIZE, rotation);
    }
  }

  /**
   * Draw the 2d tile array for a level. This array includes everything except the worm and the background.
  */
  private void drawTiles(Tile[][] tiles) {
    for(int row = 0; row < tiles.length; row++) {
      for(int col = 0; col < tiles[row].length; col++) {
        // null represents air. Do not do anything in this case
        if(tiles[row][col] == null)
          continue;

        // Calculate ConnectionState (see what sides this tile is connected to) //
        final Direction[] directions = {Direction.Left, Direction.Right, Direction.Up, Direction.Down};
        boolean[] connectsTo = {false,false,false,false};

        for(int i = 0; i < directions.length; i++) {
          TilePosition neighboringTile  = new TilePosition(col, row).nextInDirection(directions[i]);

          if(neighboringTile.isOffscreen(tiles)) {
            connectsTo[i] = tiles[row][col].canConnectToEdge();
          } else {
            connectsTo[i] = tiles[row][col].canConnectTo(tiles[neighboringTile.y][neighboringTile.x]);
          }
        }

        // calculate various parameters needed for drawing the sprite //

        BufferedImage[][] spriteTable = tiles[row][col].sprite.getImages();

        ConnectionState state = new ConnectionState(connectsTo[0],connectsTo[1],connectsTo[2],connectsTo[3]);
        int spriteCol = state.getType().columnIndex();
        int spriteRow = worm.Util.randomInt(row, col, 0, spriteTable.length - 1);
        int x = TILE_SIZE * col + TILE_SIZE/2;
        int y = TILE_SIZE * row + TILE_SIZE/2;
        double rotation = state.getRotation();

        drawImage(spriteTable[spriteRow][spriteCol], x, y, TILE_SIZE, TILE_SIZE, rotation);
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
