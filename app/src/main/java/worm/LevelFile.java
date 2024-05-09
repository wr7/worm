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

    // Find the dimensions of the level
    for (int i = 0; i < content.length(); i++) {
      if (content.charAt(i) == '\n') {
        height++;
      } else if (height == 0) {
        width++;
      }
    }

    Tile[][] tiles = new Tile[height][width];

    // Parse the tiles and find the length of the worm
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int i = (width + 1) * y + x;

        if (Character.isDigit(content.charAt(i))) {
          int digit = Character.digit(content.charAt(i), 10);
          wormlength = Math.max(digit + 1, wormlength);

          continue;
        }

        tiles[y][x] = switch(content.charAt(i)) {
          case 'D' -> Tile.Grass;
          case 'd' -> Tile.GrassDecoration;
          case 'F' -> Tile.Pear;
          case 'E' -> Tile.Shock;
          case 'P' -> Tile.Push;
          case '.' -> null;
          case 'G' -> Tile.Goal;
          case 'S' -> Tile.Saw;
          default -> throw new RuntimeException("Failed to load level file: invalid character '" + content.charAt(i) + "'");
        };
      }
    }

    TilePosition[] worm = new TilePosition[wormlength];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int i = (width + 1) * y + x;

        if (Character.isDigit(content.charAt(i))) {
          int digit = Character.digit(content.charAt(i), 10);
          worm[digit] = new TilePosition(x, y);
        }
      }
    }

    return new Level(tiles, worm, Sprite.SkyBackground);
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
