package worm.graphics;

/**
 * Represents how a block is connected to its surrounding blocks
*/
public class ConnectionState {
  // Using bitmap to allow for switch statements
  private byte bits;

  /**
   * The connected blocks as bits where 1 is connected and 0 is not connected.
   * <ul>
   *   <li>3 - Left</li>
   *   <li>2 - Right</li>
   *   <li>1 - Top</li>
   *   <li>0 - Bottom</li>
   * </ul>
  */
  public byte bits() {
    return this.bits;
  }

  public boolean Left() {
    if((bits() << 3) % 2 == 1) {
      return true;
    } else {
      return false;
    }
  }
  public boolean Right() {
    if((bits() << 2) % 2 == 1) {
      return true;
    } else {
      return false;
    }
  }
  public boolean Top() {
    if((bits() << 1) % 2 == 1) {
      return true;
    } else {
      return false;
    }
  }
  public boolean Bottom() {
    if((bits() << 0) % 2 == 1) {
      return true;
    } else {
      return false;
    }
  }

  
  public ConnectionState(boolean left,  boolean right,  boolean top,  boolean bottom) {
    this.bits = (byte)(
      (left?8:0)|
      (right?4:0)|
      (top?2:0)|
      (bottom?1:0)
    );
  }

  /**
   * Gets the column index of a sprite based off of its connections to other blocks.
   *
   * @see Sprite#getImages()
  */
  public ConnectedSpriteType getType() {
    switch (bits()) {
      //           LRTB
      case (byte)0b0000: return ConnectedSpriteType.Disconnected;
      case (byte)0b0001: return ConnectedSpriteType.SingleConnection;
      case (byte)0b0010: return ConnectedSpriteType.SingleConnection;
      case (byte)0b0100: return ConnectedSpriteType.SingleConnection;
      case (byte)0b1000: return ConnectedSpriteType.SingleConnection;
      case (byte)0b1010: return ConnectedSpriteType.LConnection;
      case (byte)0b1001: return ConnectedSpriteType.LConnection;
      case (byte)0b0110: return ConnectedSpriteType.LConnection;
      case (byte)0b0101: return ConnectedSpriteType.LConnection;
      case (byte)0b1100: return ConnectedSpriteType.StraightConnection;
      case (byte)0b0011: return ConnectedSpriteType.StraightConnection;
      case (byte)0b0111: return ConnectedSpriteType.TripleConnection;
      case (byte)0b1011: return ConnectedSpriteType.TripleConnection;
      case (byte)0b1101: return ConnectedSpriteType.TripleConnection;
      case (byte)0b1110: return ConnectedSpriteType.TripleConnection;
      case (byte)0b1111: return ConnectedSpriteType.Surrounded;
    }

    // Unreachable //
    return ConnectedSpriteType.Disconnected;
  }

  /**
   * Gets the needed clockwise rotation (radians) of a sprite based off of its connections to other blocks.
   *
   * @see Sprite#getImages()
  */
  public double getRotation() {
    switch (bits()) {
      // Left, Right, Top, Bottom
      //           LRTB
      case (byte)0b0000: return 0;
      case (byte)0b1111: return 0;

      case (byte)0b0001: return 0;
      case (byte)0b0101: return 0;
      case (byte)0b0011: return 0;
      case (byte)0b1101: return 0;

      case (byte)0b1000: return Math.PI/2;
      case (byte)0b1001: return Math.PI/2;
      case (byte)0b1100: return Math.PI/2;
      case (byte)0b1011: return Math.PI/2;

      case (byte)0b0010: return Math.PI;
      case (byte)0b1010: return Math.PI;
      // case (byte)0b0011: return Math.PI;
      case (byte)0b1110: return Math.PI;

      case (byte)0b0100: return -Math.PI/2;
      case (byte)0b0110: return -Math.PI/2;
      // case (byte)0b1100: return -Math.PI/2;
      case (byte)0b0111: return -Math.PI/2;
    }

    return 0;
  }
}
