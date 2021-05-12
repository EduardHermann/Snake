package pack;

import java.awt.Toolkit;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  public GameFrame() {
    this.add(new GamePanel());
    this.setTitle("Snake by Eduard Hermann");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/snake_icon.png")));
    // this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setVisible(true);
    this.pack();
  }
}