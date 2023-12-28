package worm;

public enum Direction {
  Left,Right,Up,Down;
  public Direction opposite() {
    switch(this) {
      case Down: return Direction.Up;
      case Left: return Direction.Right;
      case Right: return Direction.Left;
      case Up: return Direction.Down;
    }
    throw new NullPointerException();
  }
}
