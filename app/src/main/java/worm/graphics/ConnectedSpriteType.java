package worm.graphics;

/** 
 * Represents an orientationless sprite connection state.
 * Each column in a connectable tile's sprite table (eg Tile.Grass) corresponds to a variant of this enum.
 * When using the correct rotation value, any sprite connection combination can be rendered.
 */
enum ConnectedSpriteType {
  Disconnected, SingleConnection, LConnection, StraightConnection, TripleConnection, Surrounded;

  public Sprite getWormSprite(boolean is_head) {
    if(is_head) {
      return Sprite.WormHead;
    }
    switch(this) {
      case SingleConnection: return Sprite.WormTail;
      case StraightConnection: return Sprite.WormSegment;
      case LConnection: return Sprite.WormTurn;
      default: throw new RuntimeException("Invalid sprite type for worm tile. Tile must have have one or two connections.");
    }
  }

  public int columnIndex() {
    switch(this) {
      case Disconnected: return 0;
      case SingleConnection: return 1;
      case LConnection: return 2;
      case StraightConnection: return 3;
      case TripleConnection: return 4;
      case Surrounded: return 5;
    }
    throw new RuntimeException("Unreachable");
  }
}
