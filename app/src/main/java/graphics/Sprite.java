package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;

import javax.imageio.ImageIO;

public enum Sprite {
  WormHead("sprites/worm/head.png"), 
  WormSegment("sprites/worm/segment.png"), 
  WormTurn("sprites/worm/turn.png"), 
  WormTail("sprites/worm/tail.png"),
  Grass("sprites/grass.png"),
  ;

  private final URL url;
  private static EnumMap<Sprite, BufferedImage[][]> cached_sprites = new EnumMap<Sprite, BufferedImage[][]>(Sprite.class);

  private Sprite(String file_path) {
    url = getClass().getResource(file_path);
  }

  /**
   * Gets the first image for a sprite.
  */
  public BufferedImage getImage() {
    return getImages()[0][0];
  }

  /**
   * Gets the images for a sprite. Note: this is a two dimensional array.
   *
   * Some sprites may have multiple rows where a random row should be drawn every time the sprite is rendered.
   *
   * Additionally, some sprites may have multiple columns where the correct column number depends on the surrounding blocks,
   * and typically, when a sprite has multiple rows, a random row is selected. The typical column scheme is 
   * optional and is as follows:
   * <ul>
   *   <li>0: The block is surrounded by air/not connected to anything</li>
   *   <li>1: The block is connected from the bottom</li>
   *   <li>2: The block is connected from the bottom and the right</li>
   *   <li>3: The block is connected from the bottom and the top</li>
   *   <li>4: The block is connected from the bottom, left, and right</li>
   *   <li>5: The block is connected from all sides</li>
   * </ul>
  */
  public BufferedImage[][] getImages() {
    BufferedImage[][] cached_images = cached_sprites.get(this);

    if(cached_images != null) {
      return cached_images;
    }

    try {
      BufferedImage image = ImageIO.read(url);
      
      if(image == null) {
        System.err.println("Failed to load resource '" + url + "'");
      }

      int width = image.getWidth()/32;
      int height = image.getHeight()/32;

      BufferedImage[][] images = new BufferedImage[height][width];
      for(int row = 0; row < height; row++) {
        for(int col = 0; col < width; col++) {
          BufferedImage sim = image.getSubimage(col*32, row*32, 32, 32);
          images[row][col] = sim;
        }
      }

      cached_sprites.put(this, images);

      return images;
    } catch(IOException exception) {
      System.err.println("Failed to load resource '" + url + "': " + exception.toString());
      System.exit(1);
      return null; // UNREACHABLE because of exit above ^
    }
  }
}
