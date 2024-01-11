public class MoveWorm{

    public static up(){
        TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Up);
        TilePosition.remove(TilePosition.size-1);
    }

    public static down(){
        if(Tile[TilePosition[0]][TilePosition[0]+1]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Down);
            TilePosition.remove(TilePosition.size-1);
        }
    }

    public static left(){
        if(Tile[TilePosition[0]-1][TilePosition[0]]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Left);
            TilePosition.remove(TilePosition.size-1);
        }
    }

    public static right(){
        if(Tile[TilePosition[0]+1][TilePosition[0]]==null){
            TilePosition.add(0,TilePosition[0].nextInDirection(Direction.Right);
            TilePosition.remove(TilePosition.size-1);
        }
    }

    public static fall(){
        boolean fall=true;
        for(int x=0; x<TilePosition.size(); x++)
                if(Tile[TilePosition[x]][TilePosition[x]+1]!=null)
                    fall=false;
                    
        while (fall=true){
            for(int x=0; x<TilePosition.size(); x++){
                TilePosition[x].nextInDirection(Direction.Down);
                if(Tile[TilePosition[x]][TilePosition[x]+1]!=null)
                    fall=false;
            }
        }
    }
}
