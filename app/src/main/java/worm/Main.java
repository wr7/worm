package worm;

import worm.graphics.WormWindow;

public class Main {
    public static void main(String[] args) {
        Level test_level = LevelFile.TestLevelOne.readLevel();
        new WormWindow(test_level);
    }
}
