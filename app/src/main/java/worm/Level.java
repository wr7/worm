package worm;

import java.util.ArrayList;
import java.util.Arrays;

import worm.graphics.Sprite;

public class Level {
    public Tile[][] tiles;
    public ArrayList<TilePosition> worm;
    public Sprite background = Sprite.SkyBackground;
    public boolean alive=true;
    public boolean levelClear=false;

    public Level(Tile[][] tiles, TilePosition[] worm, Sprite background) {
        this.tiles = tiles;
        this.worm = new ArrayList<TilePosition>(Arrays.asList(worm)); // Create ArrayList from regular array
        this.background = background;
    }

   public void moveInDirection(Direction d){
     TilePosition old_worm_head = worm.get(worm.size() - 1);
       boolean move = true;
       boolean grow=false;
       
       if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]!=null)
           move=false;
       
       if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]==Tile.Pear){
           move=true;
           grow=true;
       }

       for(int a=0; a<worm.size(); a++){
           if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]==worm[a])
               move=false;
       }

       if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]==Tile.Goal)
           levelClear=true;

       //checking if the worm hit a shock tile
       if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]==Tile.Shock) 
            alive=false;

       //checking if the worm hit a saw tile
       if(tiles[old_worm_head.y.nextInDirection(d)][old_worm_head.x.nextInDirection(d)]==Tile.Saw)
           //saw stuff
       
       if(move=true)
         worm.add(old_worm_head.nextInDirection(d));

       if(grow=false)
         worm.remove(0);
    }
}
