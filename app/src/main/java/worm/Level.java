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
    public boolean fall=true;

    public Level(Tile[][] tiles, TilePosition[] worm, Sprite background) {
        this.tiles = tiles;
        this.worm = new ArrayList<TilePosition>(Arrays.asList(worm)); // Create ArrayList from regular array
        this.background = background;
    }

   public void moveInDirection(Direction d){
     TilePosition old_worm_head = worm.get(worm.size() - 1);
       Tile Position new_worm_head = old_worm_head.nextInDirection(d);
       boolean move = true;
       boolean grow=false;

       do
           fallCheck();
        while(fall=true);
       
       if(tiles[new_worm_head.y][new_worm_head.x]!=null)
           move=false;
       
       if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Pear){
           move=true;
           grow=true;
       }

       for(int a=0; a<worm.size()-1; a++){
           if(tiles[new_worm_head.y][new_worm_head.x]==worm[a])
               move=false;
       }

       if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Goal)
           levelClear=true;

        if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Shock)
            alive=false;
                
       if(move=true)
         worm.add(new_worm_head);

       if(grow=false)
         worm.remove(0);
    }

    public boolean levelClear(){
        if(tiles[new_worm_head.y][new_worm_head.x]==Tile.Goal)
           levelClear=true;
    }

    public boolean fallCheck(){
        for(int a=0; a<worm.size()-1; a++){
            if(worm[a].nextInDirection(Direction.Down)==null)
            else
                return false;
            return true;
        }      
    }

    public void gFall(){
         for(int a=0; a<worm.size()-1; a++){
             worm[a].nextInDirection(Direction.Down);
         }
    }
        
    }
        
}
