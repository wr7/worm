package worm;

public class TilePosition {
  public int x;
  public int y;

  public TilePosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

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
}
