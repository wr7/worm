import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;

import javax.imageio.ImageIO;

public enum Sprite {
  WormHead("sprites/worm/head.png"), 
  WormSegment("sprites/worm/segment.png"), 
  WormTurn("sprites/worm/turn.png"), 
  WormTail("sprites/worm/tail.png");

  private final URL url;
  private static EnumMap<Sprite, BufferedImage> cached_images = new EnumMap<Sprite, BufferedImage>(Sprite.class);

  private Sprite(String file_path) {
    url = getClass().getResource(file_path);
  }

  public BufferedImage getImage() {
    BufferedImage cached_image = cached_images.get(this);

    if(cached_image != null) {
      return cached_image;
    }

    try {
      BufferedImage image = ImageIO.read(url);
      
      if(image == null) {
        System.err.println("Failed to load resource '" + url + "'");
      }
      
      cached_images.put(this, image);

      return image;
    } catch(IOException exception) {
      System.err.println("Failed to load resource '" + url + "': " + exception.toString());
      System.exit(1);
      return null; // UNREACHABLE because of exit above ^
    }
  }
}
