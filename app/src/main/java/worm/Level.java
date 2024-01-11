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
   public static moveInDirection(Direction d){
       if (d==Direction.Up){
        if(Tile[TilePosition[0]][TilePosition[0]-1]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Up);
            TilePosition.remove(TilePosition.size-1);
        }
       }
       if(d==Direction.Down){
        if(Tile[TilePosition[0]][TilePosition[0]+1]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Down);
            TilePosition.remove(TilePosition.size-1);
        }
       }
       if(d==Direction.Left){
        if(Tile[TilePosition[0]-1][TilePosition[0]]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Left);
            TilePosition.remove(TilePosition.size-1);
        }
       }
       if(d==Direction.Right){
       if(Tile[TilePosition[0]+1][TilePosition[0]]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Right);
            TilePosition.remove(TilePosition.size-1);
        }
       }
    }
}
