package worm;

import java.util.ArrayList;
import java.util.Arrays;

import worm.graphics.Sprite;

/** Represents the state of a level */
public class Level {
  // The current tiles in the level
  public Tile[][] tiles;
  // The positions occupied by the worm (index 0 corresponds to the tail) and the head is the last
  // element
  // If the worm is alive, this array MUST be in order and must be longer than 1 tile
  public ArrayList<TilePosition> worm;
  // The background used for the level
  public Sprite background = Sprite.SkyBackground;
  // Whether or not the worm is alive
  public boolean alive = true;
  // Whether or not the level has been beaten and the worm should move on
  public boolean levelCleared = false;

  public Level(Tile[][] tiles, TilePosition[] worm, Sprite background) {
    this.tiles = tiles;
    this.worm =
        new ArrayList<TilePosition>(Arrays.asList(worm)); // Create ArrayList from regular array
    this.background = background;
  }

  /**
   * Creates an unlinked, independent copy of the level. Say we have the following two lines of
   * code: 
   * <code>
   * Level l1 = new Level(...);
   * Level l2 = l1;
   * </code> 
   * If we set l2.alive to false, it will also set
   * l1.alive to false because they both reference the same object. This method creates an unlinked
   * copy and can be used to prevent this.
   */
  public Level clone() {
    Tile[][] tiles_clone = new Tile[tiles.length][];
    for (int i = 0; i < tiles.length; i++) {
      tiles_clone[i] = tiles[i].clone();
    }

    TilePosition[] worm_clone = new TilePosition[worm.size()];
    for (int i = 0; i < worm.size(); i++) {
      worm_clone[i] = worm.get(i).clone();
    }

    return new Level(tiles_clone, worm_clone, background);
  }

  /** Moves the worm in a direction (up, down, left, or right) */
  public void moveInDirection(Direction d) {
    TilePosition old_worm_head = worm.get(worm.size() - 1);
    TilePosition new_worm_head = old_worm_head.nextInDirection(d);

    if (new_worm_head.equals(worm.get(worm.size() - 2))) {
      moveBackwards();
      return;
    }

    if (new_worm_head.isOffscreen(tiles)) {
      return;
    }

    if (Tile.canBlockWorm(tiles[new_worm_head.y][new_worm_head.x])) {
      return;
    }

    // Check if worm will collide with itself //
    for (int a = 1; a < worm.size() - 1; a++) {
      if (new_worm_head.equals(worm.get(a))) {
        return;
      }
    }

    if (tiles[new_worm_head.y][new_worm_head.x] == Tile.Push) {
      if(!tryPushBlock(d, new_worm_head)) {
        return;
      }
    }

    worm.add(new_worm_head);

    if (tiles[new_worm_head.y][new_worm_head.x] == Tile.Pear) {
      tiles[new_worm_head.y][new_worm_head.x] = null;
    } else {
      worm.remove(0);
    }

    simulateWorm();
  }

  private void simulateWorm() {
    checkWormTiles();

    while (wormShouldFall() && alive) {
      if (isWormOffscreen()) {
        alive = false;
      }

      // Simulate gravity //
      fall();

      checkWormTiles();
    }
  }

  /** Checks what tiles the worm is touching. */
  private void checkWormTiles() {
    for (int segNum = 0;
        segNum < worm.size();
        segNum++) { // looping through each element in the worm list to scan the entire worm
      if (!alive) return;

      if (worm.get(segNum).isOffscreen(tiles)) continue;

      Tile tile =
          tiles[worm.get(segNum).y][
              worm.get(segNum)
                  .x]; // taking in the worm segment into a tile that we can use to compare
      if (tile == null) continue;

      switch (tile) {
        case Shock: // if the segment is on a shock block, the worm will die
          alive = false;
          break;
        case Goal: // if the segment is on a goal block, the level is won
          levelCleared = true;
          break;
        case Saw: // if the segment is on a saw block, then
          sawWorm(worm.get(worm.size() - 1), worm.get(segNum));
          segNum = -1; // The worm length has been changed; redo all calculations
          continue;
        default:
      }
    }
  }

  /**
   * Tries to push a push block. Returns true upon success.
   */
  private boolean tryPushBlock(Direction d, TilePosition block_position) {
    TilePosition new_block_position = block_position.nextInDirection(d);

    if (new_block_position.isOffscreen(tiles)) {
      return false;
    }

    if (tiles[new_block_position.y][new_block_position.x] != null) {
      return false;
    }

    for (TilePosition segment : worm) {
      if (segment.equals(new_block_position)) {
        return false;
      }
    }

    tiles[block_position.y][block_position.x] = null;
    tiles[new_block_position.y][new_block_position.x] = Tile.Push;
    
    return true;
  }

  // this method performs the cutting function of the worm if it hits a saw block
  public void sawWorm(TilePosition wormHead, TilePosition segment) {
    // Check if wormHead and segment equal or if the worm is too short
    // If the conditions are true, then the worm will die
    if (wormHead.equals(segment)) {
      alive = false;
    } else {
      // this look keeps removing the worm segments until it hits the stopping point
      while (!worm.get(0).equals(segment)) {
        worm.remove(0);
      }
      // removing the segment that is the stopping point
      worm.remove(0);

      if (worm.size() < 2) {
        alive = false;
      }
    }
  }

  public boolean wormShouldFall() {
    // checks if worm is floating
    for (int a = 0; a < worm.size(); a++) {
      TilePosition supporting_tile = worm.get(a).nextInDirection(Direction.Down);
      if (supporting_tile.isOffscreen(tiles)) {
        continue;
      }
      if (Tile.canSupportWorm(tiles[supporting_tile.y][supporting_tile.x])) return false;
      // returns false if there is a solid block under worm
    }

    return true;
  }

  private boolean isWormOffscreen() {
    // checks if worm is offscreen
    for (TilePosition position : worm) {
      if (!position.isOffscreen(tiles)) {
        return false;
      }
    }

    return true;
  }

  public void fall() {
    // moves worm down
    for (TilePosition segment : worm) {
      segment.y += 1;
    }
  }

  /** Called when the user tries to move backwards */
  private void moveBackwards() {
    if (!alive) return;

    // Calculate where the new tail of the worm should be //
    TilePosition old_worm_tail = worm.get(0);
    TilePosition second_segment = worm.get(1);

    TilePosition new_worm_tail =
        new TilePosition(
            2 * old_worm_tail.x - second_segment.x, 2 * old_worm_tail.y - second_segment.y);

    // Check if the worm would go offscreen //
    if (new_worm_tail.isOffscreen(tiles)) return;

    // Check if the worm would be blocked by any tiles //
    Tile new_tail_tile = tiles[new_worm_tail.y][new_worm_tail.x];
    if (Tile.canBlockWorm(new_tail_tile) || Tile.canSupportWorm(new_tail_tile)) return;

    // Check if the worm would be blocked by itself //
    for(int i = 0; i < worm.size() - 1; i++) { // Skips the head because it will be moved
      if(worm.get(i).equals(new_worm_tail)) {
        return;
      }
    }

    // Move the worm backwards //
    worm.add(0, new_worm_tail);
    worm.remove(worm.size() - 1);

    simulateWorm();
  }
}
