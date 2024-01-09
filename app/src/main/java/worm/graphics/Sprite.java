package worm.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;

import javax.imageio.ImageIO;

/**
 * Represents an image or a 2d array of images.
 * @see Sprite#getImages()
 */
public enum Sprite {
  WormHead("sprites/worm/head.png"), 
  WormSegment("sprites/worm/segment.png"), 
  WormTurn("sprites/worm/turn.png"), 
  WormTail("sprites/worm/tail.png"),
  Grass("sprites/grass.png"),
  GrassDecoration("sprites/grass_decorations.png"),
  Goal("sprites/goal.png"),
  Pear("sprites/pear.png"),
  Count("sprites/count.png"), // For testing purposes
  CaveBackground("sprites/cave_background.png"),
  SkyBackground("sprites/sky_background.png"),
  ;

  private final URL url;

  private static EnumMap<Sprite, BufferedImage> cached_full_images = new EnumMap<Sprite, BufferedImage>(Sprite.class);
  private static EnumMap<Sprite, BufferedImage[][]> cached_sprites = new EnumMap<Sprite, BufferedImage[][]>(Sprite.class);

  private Sprite(String file_path) {
    url = getClass().getClassLoader().getResource(file_path);
  }

  /**
   * Returns the sprite as an image.
  */
  public BufferedImage getFullImage() {
    BufferedImage cached_image = cached_full_images.get(this);
    if(cached_image != null) {
      return cached_image;
    }

    try {
      BufferedImage image = ImageIO.read(url);
      
      if(image == null) {
        System.err.println("Failed to load resource '" + url + "'");
      }

      cached_full_images.put(this, image);

      return image;
    } catch(IOException exception) {
      System.err.println("Failed to load resource '" + url + "': " + exception.toString());
      System.exit(1);
      return null; // UNREACHABLE because of exit above ^
    }
  }

  /**
   * Treats the sprite as a two dimensional array of images. This is useful for connectable sprites or sprites with
   * different variants.
   *
   * Some sprites may have multiple rows where a random row should be drawn every time the sprite is rendered.
   *
   * Additionally, some sprites may have multiple columns where the correct column number depends on the surrounding blocks. 
   * The typical column scheme is as follows:
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

    BufferedImage image = getFullImage();
    
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
  }
}
