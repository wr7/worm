package worm;

import worm.graphics.Sprite;

public enum Tile {
  Grass(Sprite.Grass),
  Count(Sprite.Count), // For testing purposes
  GrassDecoration(Sprite.GrassDecoration);
  public final Sprite sprite;

  private Tile(Sprite sprite) {
    this.sprite = sprite;
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
