package worm;

import worm.graphics.Sprite;
import worm.graphics.WormWindow;

public class Main {
    public static void main(String[] args) {
        Level testLevel = new Level(
            new Tile[][]{ // tiles
                 {null                , null                , null                , null                , null                , null                , },
                 {null                , null                , null                , null                , null                , null                , },
                 {null                , null                , null                , null                , Tile.GrassDecoration, Tile.GrassDecoration, },
                 {null                , null                , null                , null                , Tile.Grass          , Tile.Grass          , },
                 {null                , null                , Tile.GrassDecoration, null                , Tile.GrassDecoration, Tile.Grass          , },
                 {null                , null                , Tile.Grass          , Tile.Grass          , Tile.Grass          , Tile.Grass          , },
                 {null                , Tile.Grass          , Tile.Grass          , Tile.Grass          , Tile.Grass          , Tile.Grass          , },
            }, 
            new TilePosition[]{ // snake position
                new TilePosition(0, 6),
                new TilePosition(0, 5),
                new TilePosition(1, 5),
                new TilePosition(1, 4),
                new TilePosition(1, 3),
            }, 
            Sprite.SkyBackground
        );

        // Print out the contents of the file for level one
        System.out.println("Level one contents: \n" + LevelFile.TestLevelOne.content);

        new WormWindow(testLevel);
    }
}
