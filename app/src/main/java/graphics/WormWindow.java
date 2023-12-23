package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WormWindow extends JPanel {
  public WormWindow() {
    super();
    JFrame frame = new JFrame("Worm");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(250, 200);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics);

    super.paintComponent(graphics);

    wrg.drawSprite(Sprite.WormHead, 225, 125, 50, 50, Math.PI/2);
    wrg.drawSprite(Sprite.WormSegment, 225, 75, 50, 50, Math.PI/2);
    wrg.drawSprite(Sprite.WormTurn, 225, 25, 50, 50, -Math.PI/2);
    wrg.drawSprite(Sprite.WormSegment, 175, 25, 50, 50, 0);
    wrg.drawSprite(Sprite.WormTurn, 125, 25, 50, 50, Math.PI);
    wrg.drawSprite(Sprite.WormTurn, 125, 75, 50, 50, 0);
    wrg.drawSprite(Sprite.WormSegment, 75, 75, 50, 50, 0);
    wrg.drawSprite(Sprite.WormTail, 25, 75, 50, 50, 0);
  }
}
