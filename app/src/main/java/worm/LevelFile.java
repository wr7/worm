package worm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import worm.graphics.Sprite;

/**
 * Represents a level's file. An instance of this enum can be constructed like so:
 *
 * <p>LevelFile level = LevelFile.TestLevelOne;
 *
 * <p>An array of all of the levels can be obtained via LevelFile.getLevels()
 */
public enum LevelFile {
  // The possible levels. Each variant maps to a txt file in the app/src/main/resources/level
  // directory
  Level1("level/level1.txt"),
  Pear("level/pear.txt"),
  Push("level/push.txt"),
  Backtrack("level/backtrack.txt"),
  Shock("level/shock.txt"),
  Climb("level/climb.txt"),
  Entrapment("level/entrapment.txt"),
  Maze("level/maze.txt"),
  Saw("level/saw.txt");

  // The content of the file
  // Example value:
  // ..2.....
  // .01DDD..
  // DDDDDDDD
  final String content;

  // Cache used for the getLevels method
  private static Level[] levels = null;

  /** Gets all of the levels as an array. This method will cache the result. */
  public static Level[] getLevels() {
    // Checks if there is a level to load in, and if there is, it is sent to main.
    if (levels != null) {
      return levels;
    }
    // Creates an array that has the length of the number of levels to store all of the levels in.
    LevelFile[] files = LevelFile.class.getEnumConstants();
    levels = new Level[files.length];
    // A for loop that reads the contents of every level file in the array.
    for (int i = 0; i < levels.length; i++) {
      levels[i] = files[i].readLevel();
    }

    return levels;
  }

  public Level readLevel() {
    int height = 0;
    int width = 0;
    int wormlength = 0;
    // A nested for loop that finds the dimensions of the level
    for (int i = 0; i < content.length(); i++) {
      if (content.charAt(i) == '\n') {
        height++;
      } else if (height == 0) {
        width++;
      }
    }
    // Creates a 2D array of tiles that correspond to graphical tiles
    Tile[][] tiles = new Tile[height][width];
    // A nested for loop that reads the file and assigns the corresponding graphic to that spot and
    // also finds the length of the worm.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int i = (width + 1) * y + x;

        if (content.charAt(i) == 'D') {
          tiles[y][x] = Tile.Grass;
        } else if (content.charAt(i) == 'd') {
          tiles[y][x] = Tile.GrassDecoration;
        } else if (content.charAt(i) == 'F') {
          tiles[y][x] = Tile.Pear;
        } else if (content.charAt(i) == 'E') {
          tiles[y][x] = Tile.Shock;
        } else if (content.charAt(i) == 'P') {
          tiles[y][x] = Tile.Push;
        } else if (content.charAt(i) == '.') {
          tiles[y][x] = null;
        } else if (content.charAt(i) == 'G') {
          tiles[y][x] = Tile.Goal;
        } else if (content.charAt(i) == 'S') {
          tiles[y][x] = Tile.Saw;
        } else if (Character.isDigit(content.charAt(i))) {
          int digit = Character.digit(content.charAt(i), 10);
          wormlength = Math.max(digit + 1, wormlength);
        }
      }
    }

    // Create an array of instances of the TilePosition class that stores the worm's position. //
    TilePosition[] worm = new TilePosition[wormlength];

    // A nested for loop that assigns the positions of each segment of the worm to the complete
    // array.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int i = (width + 1) * y + x;
        if (Character.isDigit(content.charAt(i))) {
          int digit = Character.digit(content.charAt(i), 10);
          worm[digit] = new TilePosition(x, y);
        }
      }
    }

    // Creates a new instance of Level with all of the appropriate information
    Level level = new Level(tiles, worm, Sprite.SkyBackground);
    return level;
  }

  private LevelFile(String file_path) {
    // Horribly complicated line of code to open the resource file and assign its contents to
    // `content` in a jar-friendly way.
    String fcontent =
        new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file_path)))
            .lines()
            .collect(Collectors.joining("\n"));
    if (!fcontent.endsWith("\n")) {
      fcontent += "\n";
    }

    content = fcontent;
  }
}
