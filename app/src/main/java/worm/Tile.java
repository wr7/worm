package worm;

import worm.graphics.Sprite;

/**
 * Represents a tile (block)
*/
public enum Tile {
  Grass(Sprite.Grass),
  Count(Sprite.Count), // For testing purposes
  GrassDecoration(Sprite.GrassDecoration),
  Pear(Sprite.Pear),
  Shock(Sprite.Shock),
  Push(Sprite.Push),
  Saw(Sprite.Saw),
  Goal(Sprite.Goal),
  ;

  // The image or image table used to draw the tile
  public final Sprite sprite;

  private Tile(Sprite sprite) {
    this.sprite = sprite;
  }

  /**
   * Whether or not the worm can fall through a tile
  */
  public static boolean canSupportWorm(Tile tile) {
    if(tile == null)
      return false;

    switch(tile) {
      case Grass: return true;
      case Pear: return true;
      case Push: return true;
      case Goal: return true;
      default: return false;
    }
  }
  
  /**
   * Whether or not the worm can move into/over a tile
  */
  public static boolean canBlockWorm(Tile tile) {
    if(tile == null)
      return false;

    switch(tile) {
      case Grass: return true;
      case Push: return true;
      default: return false;
    }
  }

  /**
   * Whether or not a tile can connect to another tile
   * This is purely a cosmetic thing
  */
  public boolean canConnectTo(Tile other) {
    if(other == null)
      return false;

    switch(this) {
      case Grass: return other.equals(Tile.Grass);
      default: return false;
    }
  }

  /**
   * Whether or not a tile can connect to the edge of the screen
  */
  public boolean canConnectToEdge() {
    switch(this) {
      case Grass: return true;
      default: return false;
    }
  }
}
