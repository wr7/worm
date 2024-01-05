package worm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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

    Level readLevel() {
        // The file has already been read, so the contents of the file can be accessed through the "content" variable

        // Crash program if this method is called //
        throw new RuntimeException("The code for reading levels from files has not been written yet");
    }

    private LevelFile(String file_path) {
        // Horrible line of code to open the resource file and assign its contents to `content` in a jar-friendly way.
        content = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file_path)))
            .lines().collect(Collectors.joining("\n"));
    }
}
