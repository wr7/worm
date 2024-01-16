package worm;

import worm.graphics.Sprite;

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

  public final Sprite sprite;

  private Tile(Sprite sprite) {
    this.sprite = sprite;
  }

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
  
  public static boolean canBlockWorm(Tile tile) {
    if(tile == null)
      return false;

    switch(tile) {
      case Grass: return true;
      case Push: return true;
      default: return false;
    }
  }

  public boolean canConnectTo(Tile other) {
    if(other == null)
      return false;

    switch(this) {
      case Grass: return other.equals(Tile.Grass);
      default: return false;
    }
  }

  public boolean canConnectToEdge() {
    switch(this) {
      case Grass: return true;
      default: return false;
    }
  }
}
