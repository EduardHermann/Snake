package pack;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {

  public void restart() {
    GamePanel.x = new int[GamePanel.GAME_UNITS];
    GamePanel.y = new int[GamePanel.GAME_UNITS];
    GamePanel.bodyParts = 6;
    for (int i = 0; i <= GamePanel.bodyParts; i++) {
      GamePanel.x[i] = GamePanel.UNIT_SIZE;
      GamePanel.y[i] = GamePanel.UNIT_SIZE;
    }
    GamePanel.applesEaten = 0;
    GamePanel.direction = 'R';
    GamePanel.running = true;
    GamePanel.newApple();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A:
        if (GamePanel.direction != 'R') {
          GamePanel.direction = 'L';
          break;
        }
      case KeyEvent.VK_D:
        if (GamePanel.direction != 'L') {
          GamePanel.direction = 'R';
          break;
        }
      case KeyEvent.VK_W:
        if (GamePanel.direction != 'D') {
          GamePanel.direction = 'U';
          break;
        }
      case KeyEvent.VK_S:
        if (GamePanel.direction != 'U') {
          GamePanel.direction = 'D';
          break;
        }
      case KeyEvent.VK_R:
        restart();
    }
  }
}
