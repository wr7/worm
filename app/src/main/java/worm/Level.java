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
       TilePosition new_worm_head = old_worm_head.nextInDirection(d);
       boolean move = true;
       boolean grow=false;
       
       if(Tile.canBlockWorm(tiles[new_worm_head.y][new_worm_head.x])) {
            move = false;
       }
       
       if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Pear){
           grow=true;
       }

       for(int a=0; a<worm.size()-1; a++){
           if(new_worm_head.equals(worm.get(a)))
               move=false;
       }

        if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Shock)
            alive=false;
                
       if(move==true)
         worm.add(new_worm_head);

       if(grow==false && move==true)
         worm.remove(0);

        while(wormShouldFall()) 
            fall();

       levelClear |= goalCheck();
    }

    public boolean goalCheck(){
        for(int a=0; a<worm.size(); a++){
            if(tiles[worm.get(a).y][worm.get(a).x]==Tile.Goal)
                return true;
        }
        return false;
    }

    public boolean wormShouldFall(){
        for(int a=0; a<worm.size(); a++){
            TilePosition supporting_tile = worm.get(a).nextInDirection(Direction.Down);
            if(Tile.canSupportWorm(tiles[supporting_tile.y][supporting_tile.x]))
                return false;
        }      

        return true;
    }

    public void fall(){
         for(int a=0; a<worm.size(); a++){
             worm.set(a, worm.get(a).nextInDirection(Direction.Down));
         }
    }
        
    
        
}
