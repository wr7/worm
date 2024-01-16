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
 */
public enum LevelFile {
    // The possible levels. Each variant maps to a txt file in the app/src/main/resources/level directory
    TestLevelOne("level/test1.txt"),
    ;

    // The content of the file
    // Example value:
    //    GGG  
    // GGGGGGGG
    final String content;

    public Level readLevel () {
        // The file has already been read, so the contents of the file can be accessed through the "content" variable
        int height=0;
        int width=0;
        int i;
        int wormlength=0;

        for(i=0;i<content.length();i++) {
            if(content.charAt(i)=='\n') {
                height++;
            } else if(height==0){
                width++;
            }
        }

        System.out.println("width: " + width);
        System.out.println("height: " + height);

        i=0;

        Tile[][] tiles = new Tile[height][width];
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
                } else if(content.charAt(i)=='2'&& wormlength<2) {
                    wormlength=2;
                }
                i++;
            }
            i++;
        }
        i=0;
        TilePosition[] worm = new TilePosition[wormlength];
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

        Level level = new Level(
            tiles,
            worm,
            Sprite.SkyBackground
        );
        return level;
    }
    private LevelFile(String file_path) {
        // Horrible line of code to open the resource file and assign its contents to `content` in a jar-friendly way.
        String fcontent = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file_path)))
            .lines().collect(Collectors.joining("\n"));
        if(!fcontent.endsWith("\n")) {
            fcontent += "\n";
        }

        content = fcontent;
    }
}
