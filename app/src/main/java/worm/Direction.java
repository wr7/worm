package worm;

/** Represents a direction (up, down, left, or right). */
public enum Direction {
  Left,
  Right,
  Up,
  Down;

  /**
   * Returns the opposite of a direction. Example: <code>Direction.Left.opposite()</code> would
   * return <code>Direction.Right</code>
   */
  public Direction opposite() {
    switch (this) {
      case Down:
        return Direction.Up;
      case Left:
        return Direction.Right;
      case Right:
        return Direction.Left;
      case Up:
        return Direction.Down;
    }
    throw new NullPointerException();
  }
}
