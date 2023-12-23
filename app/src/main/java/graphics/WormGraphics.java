package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

/**
 * A wrapper for drawing things for the game 'worm'.
 * The drawSprite method can be used to draw sprites to the window.
*/
public class WormGraphics {
  private final Graphics2D g2d;

  public WormGraphics(Graphics2D g2d) {
    this.g2d = g2d;
  }

  /**
   * Draws the sprite with the specified, width, height, and rotation.
   *
   * @param sprite the sprite to draw
   * @param x the distance from the left of the window to the center of the image in pixels
   * @param y the distance from the top of the window to the center of the image in pixels
   * @param width the desired width of the sprite in pixels
   * @param height the desired height of the sprite in pixels
   * @param rotation the clockwise rotation of the sprite in radians
   */
  public void drawSprite(Sprite sprite, double x, double y, int width, int height, double rotation) {
    BufferedImage image = sprite.getImage();

    double scale_x = width / ((double) image.getWidth());
    double scale_y = height / ((double) image.getHeight());

    AffineTransform transform = new AffineTransform();
    transform.translate(x, y);
    transform.rotate(rotation);
    transform.translate(-((double) width)/2, -((double) height)/2);
    transform.scale(scale_x, scale_y);

    g2d.drawImage(image, transform, null);
  }
}
