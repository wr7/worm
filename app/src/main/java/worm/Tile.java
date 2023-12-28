package worm;

import worm.graphics.Sprite;

public enum Tile {
  Grass(Sprite.Grass);

  public final Sprite sprite;

  private Tile(Sprite sprite) {
    this.sprite = sprite;
  }

  public boolean canConnectTo(Tile other) {
    if(other == null)
      return false;

    switch(this) {
      case Grass: return other.equals(Tile.Grass);
    }

    return false;
  }

  public boolean canConnectToEdge() {
    switch(this) {
      case Grass: return true;
    }

    return false;
  }
}
