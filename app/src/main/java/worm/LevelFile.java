package worm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import worm.graphics.Sprite;

/**
 * Represents a level's file.
 * An instance of this enum can be constructed like so:
 *
 * LevelFile level = LevelFile.TestLevelOne;
 *
 * An array of all of the levels can be obtained via LevelFile.getLevels()
 */
public enum LevelFile {
    // The possible levels. Each variant maps to a txt file in the app/src/main/resources/level directory
    Level1("level/fullLevel1.txt"),
    Level2("level/fullLevel2.txt"),
    Level3("level/fullLevel3.txt"),
    Entrapment("level/entrapment.txt"),
    Level4("level/fullLevel4.txt"),
    Level5("level/fullLevel5.txt"),
    Level6("level/fullLevel6.txt"),
    Level7("level/fullLevelEnd.txt")
    ;

    // The content of the file
    // Example value:
    // ..2.....
    // .01DDD..
    // DDDDDDDD
    final String content;

    // Cache used for the getLevels method
    private static Level[] levels = null;

    /**
     * Gets all of the levels as an array. This method will cache the result.
    */
    public static Level[] getLevels() {
        //Checks if there is a level to load in, and if there is, it is sent to main. 
        if(levels != null) {
            return levels;
        }
        //Creates an array that has the length of the number of levels to store all of the levels in.
        LevelFile[] files =  LevelFile.class.getEnumConstants();
        levels = new Level[files.length];
        //A for loop that reads the contents of every level file in the array.
        for(int i = 0; i < levels.length; i++) {
            levels[i] = files[i].readLevel();
        }

        return levels;
    }
    public Level readLevel () {
        int height=0;
        int width=0;
        int i;
        int wormlength=0;
        //A nested for loop that finds the dimensions of the level
        for(i=0;i<content.length();i++) {
            if(content.charAt(i)=='\n') {
                height++;
            } else if(height==0){
                width++;
            }
        }
        i=0;
        //Creates a 2D array of tiles that correspond to graphical tiles
        Tile[][] tiles = new Tile[height][width];
        //A nested for loop that reads the file and assigns the corresponding graphic to that spot and also finds the length of the worm.
        for(int y=0; y<height;y++) {
            for(int x=0; x<width; x++) {
                if(content.charAt(i)=='D') {
                    tiles[y][x]=Tile.Grass;
                } else if(content.charAt(i)=='d') {
                    tiles[y][x]=Tile.GrassDecoration;
                } else if(content.charAt(i)=='F') {
                    tiles[y][x]=Tile.Pear;
                } else if(content.charAt(i)=='E') {
                    tiles[y][x]=Tile.Shock;
                } else if(content.charAt(i)=='P') {
                    tiles[y][x]=Tile.Push;
                } else if(content.charAt(i)=='.') {
                    tiles[y][x]=null;
                } else if(content.charAt(i)=='G') {
                    tiles[y][x]=Tile.Goal;
                } else if(content.charAt(i)=='S') {
                    tiles[y][x]=Tile.Saw;
                } else if(content.charAt(i)=='9'&& wormlength<10) {
                    wormlength=10;
                } else if(content.charAt(i)=='8'&& wormlength<9) {
                    wormlength=9;
                } else if(content.charAt(i)=='7'&& wormlength<8) {
                    wormlength=8;
                } else if(content.charAt(i)=='6'&& wormlength<7) {
                    wormlength=7;
                } else if(content.charAt(i)=='5'&& wormlength<6) {
                    wormlength=6;
                } else if(content.charAt(i)=='4'&& wormlength<5) {
                    wormlength=5;
                } else if(content.charAt(i)=='3'&& wormlength<4) {
                    wormlength=4;
                } else if(content.charAt(i)=='2'&& wormlength<3) {
                    wormlength=3;
                } else if(content.charAt(i)=='1'&& wormlength<2) {
                    wormlength=2;
                }
                i++;
            }
            i++;
        }
        i=0;
        //Creates an array of instances of the TilePosition class that stores the worm's position.
        TilePosition[] worm = new TilePosition[wormlength];
        //A nested for loop that assigns the positions of each segment of the worm to the complete array.
        for(int tilenum=wormlength - 1; tilenum>=0;tilenum--) {
            for(int y=0; y<height;y++) {
                for(int x=0; x<width; x++) {
                    i = (width + 1) * y + x;
                    if(Character.isDigit(content.charAt(i))) {
                        if(Character.digit(content.charAt(i), 10)==tilenum) {
                            worm[tilenum]= new TilePosition(x,y);
                        }
                    }
                }
            }
        }    
        //Creates a new instance of Level with all of the appropriate information
        Level level = new Level(
            tiles,
            worm,
            Sprite.SkyBackground
        );
        return level;
    }
    private LevelFile(String file_path) {
        // Horribly complicated line of code to open the resource file and assign its contents to `content` in a jar-friendly way.
        String fcontent = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file_path)))
            .lines().collect(Collectors.joining("\n"));
        if(!fcontent.endsWith("\n")) {
            fcontent += "\n";
        }

        content = fcontent;
    }
}
