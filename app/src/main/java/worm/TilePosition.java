package worm;

/**
 * Represents a possible location of a {@link Tile}.
 * 
 * This class contains an x and a y value where:
 * <ul>
 * 	<li>x=0 is at the left side of the screen. Positive x is to the right</li>
 * 	<li>y=0 is at the top of the screen. Positive y is downwards</li>
 * </ul>
 */
public class TilePosition {
  public int x;
  public int y;

  public TilePosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean isOffscreen(Tile[][] tiles) {
    if(this.x < 0 || this.y < 0)
      return true;

    if(this.y >= tiles.length || this.x >= tiles[0].length)
      return true;
    
    return false;
  }

  /**
   * Creates an unlinked independent copy of the tile position.
  */
  public TilePosition clone() {
    return new TilePosition(x, y);
  }

  /**
   * Gets the neighboring tile in a specific direction.
   * 
   * For example, <code>new TilePosition(2, 3).nextInDirection(Direction.Up)</code>
   * will return (2, 2) because (2, 2) is the tile above (2, 3).
   */
  public TilePosition nextInDirection(Direction direction) {
    int new_x = x;
    int new_y = y;

    switch(direction) {
      case Up: new_y -= 1; break;
      case Down: new_y += 1; break;
      case Left: new_x -= 1; break;
      case Right: new_x += 1; break;
      default: throw new NullPointerException();
    }

    return new TilePosition(new_x, new_y);
  }

  public boolean equals(TilePosition other) {
    return (x == other.x) && (y == other.y);
  }
}
