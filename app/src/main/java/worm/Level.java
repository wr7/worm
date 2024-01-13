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

    /**
     * Creates an unlinked, independent copy of the level.
     * Say we have the following two lines of code:
     * Level l1 = new Level(...);
     * Level l2 = l1;
     * If we set l2.alive to false, it will also set l1.alive to false because they both reference the same object.
     * This method creates an unlinked copy and can be used to prevent this.
    */
    public Level clone() {
        Tile[][] tiles_clone = new Tile[tiles.length][];
        for(int i = 0; i < tiles.length; i++) {
            tiles_clone[i] = tiles[i].clone();
        }

        TilePosition[] worm_clone = new TilePosition[worm.size()];
        for(int i = 0; i < worm.size(); i++) {
            worm_clone[i] = worm.get(i).clone();
        }

        return new Level(tiles_clone, worm_clone, background);
    }

   public void moveInDirection(Direction d){
     TilePosition old_worm_head = worm.get(worm.size() - 1);
       TilePosition new_worm_head = old_worm_head.nextInDirection(d);
       boolean move = true;
       boolean grow=false;

        if(new_worm_head.isOffscreen(tiles))
            return;
       
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

        while(wormShouldFall() && alive) {
            if(isWormOffscreen()) {
                alive = false;
            }
            fall();
        }

       levelClear |= goalCheck();
    }

    public boolean goalCheck(){
        for(int a=0; a<worm.size(); a++){
            if(worm.get(a).isOffscreen(tiles))
                continue;
            if(tiles[worm.get(a).y][worm.get(a).x]==Tile.Goal)
                return true;
        }
        return false;
    }

    public boolean wormShouldFall(){
        for(int a=0; a<worm.size(); a++){
            TilePosition supporting_tile = worm.get(a).nextInDirection(Direction.Down);
            if(supporting_tile.isOffscreen(tiles)) {
                continue;
            }
            if(Tile.canSupportWorm(tiles[supporting_tile.y][supporting_tile.x]))
                return false;
        }      

        return true;
    }

    private boolean isWormOffscreen() {
        for(TilePosition position: worm) {
            if(!position.isOffscreen(tiles)) {
                return false;
            }
        }

        return true;
    }

    public void fall(){
         for(int a=0; a<worm.size(); a++){
             worm.set(a, worm.get(a).nextInDirection(Direction.Down));
         }
    }
        
    
        
}
