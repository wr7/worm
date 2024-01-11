package worm;

import java.util.ArrayList;
import java.util.Arrays;

import worm.graphics.Sprite;

public class Level {
    public Tile[][] tiles;
    public ArrayList<TilePosition> worm;
    public Sprite background = Sprite.SkyBackground;

    public Level(Tile[][] tiles, TilePosition[] worm, Sprite background) {
        this.tiles = tiles;
        this.worm = new ArrayList<TilePosition>(Arrays.asList(worm)); // Create ArrayList from regular array
        this.background = background;
    }

   public void moveInDirection(Direction d){
     TilePosition old_worm_head = worm.get(worm.size() - 1);

     if (d==Direction.Up){
       if(tiles[old_worm_head.y-1][old_worm_head.x]==null){
         worm.add(old_worm_head.nextInDirection(d));
         worm.remove(0);
      }
     }
     if(d==Direction.Down){
       if(tiles[old_worm_head.y+1][old_worm_head.x]==null){
         worm.add(old_worm_head.nextInDirection(Direction.Down));
         worm.remove(0);
      }
     }
     if(d==Direction.Left){
       if(tiles[old_worm_head.y][old_worm_head.x-1]==null){
         worm.add(old_worm_head.nextInDirection(Direction.Left));
         worm.remove(0);
      }
     }
     if(d==Direction.Right){
       if(tiles[old_worm_head.y][old_worm_head.x+1]==null){
         worm.add(old_worm_head.nextInDirection(Direction.Right));
         worm.remove(0);
       }
      }
    }
}
