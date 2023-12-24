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
    return new Dimension(640, 640);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    WormGraphics wrg = new WormGraphics((Graphics2D) graphics);

    super.paintComponent(graphics);

    ConnectionState c1 = new ConnectionState(true, true, false, true);
    ConnectionState c2 = new ConnectionState(true, false, false, true);
    ConnectionState c3 = new ConnectionState(true, false, true, true);
    ConnectionState c4 = new ConnectionState(false, false, true, true);
    ConnectionState c5 = new ConnectionState(false, false, true, false);
    ConnectionState c6 = new ConnectionState(true, true, true, false);
    ConnectionState c7 = new ConnectionState(true, true, true, true);

    wrg.drawImage(Sprite.Grass.getImages()[0][c1.getColumn()], 64*1 + 32, 64*1 + 32, 64, 64, c1.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[1][c1.getColumn()], 64*2 + 32, 64*1 + 32, 64, 64, c1.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[2][c2.getColumn()], 64*3 + 32, 64*1 + 32, 64, 64, c2.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[0][c3.getColumn()], 64*3 + 32, 64*2 + 32, 64, 64, c3.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[1][c4.getColumn()], 64*3 + 32, 64*3 + 32, 64, 64, c4.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[2][c5.getColumn()], 64*3 + 32, 64*4 + 32, 64, 64, c5.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[0][c6.getColumn()], 64*2 + 32, 64*2 + 32, 64, 64, c6.getRotation());
    wrg.drawImage(Sprite.Grass.getImages()[1][c7.getColumn()], 64*1 + 32, 64*2 + 32, 64, 64, c7.getRotation());

  }
}
