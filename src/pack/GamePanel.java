package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;
  static final int SCREEN_WIDTH = 800;
  static final int SCREEN_HIGHT = 800;
  static final int UNIT_SIZE = 50;
  static final int FIELD_WIDTH = SCREEN_WIDTH - UNIT_SIZE;
  static final int FIELD_HIGHT = SCREEN_HIGHT - UNIT_SIZE;
  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HIGHT) / UNIT_SIZE;
  static final int DELAY = 100;
  static int x[] = new int[GAME_UNITS];
  static int y[] = new int[GAME_UNITS];
  static int bodyParts = 6;
  static int applesEaten;
  static int appleX;
  static int appleY;
  static char direction = 'R';
  static boolean running = false;
  Timer timer;
  static Random random;

  public GamePanel() {
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGHT));
    this.addKeyListener(new MyKeyAdapter());
    this.setBackground(Color.BLACK);
    this.setFocusable(true);

    for (int i = 0; i <= bodyParts; i++) {
      x[i] = UNIT_SIZE;
      y[i] = UNIT_SIZE;
    }

    startGame();
  }

  public void startGame() {
    newApple();
    running = true;
    timer = new Timer(DELAY, this);
    timer.start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawBorder(g);
    draw(g);
    drawLines(g);
  }

  public void draw(Graphics g) {
    if (running == true) {
      g.setColor(Color.RED);
      g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

      for (int i = 0; i < bodyParts; i++) {
        if (i == 0) {
          g.setColor(Color.GREEN);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        } else {
          g.setColor(new Color(45, 180, 0));
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }
      g.setColor(Color.RED);
      g.setFont(new Font("Ink Free", Font.BOLD, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Score: " + applesEaten,
          (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    } else {
      gameOver(g);
    }
  }

  public void drawBorder(Graphics g) {
    if (running == true) {
      g.setColor(new Color(102, 102, 102));
      for (int i = 0; i <= (SCREEN_WIDTH / UNIT_SIZE) * 2; i++) {
        g.fillRect(i * UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
      }
      for (int i = 0; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
        g.fillRect(i * UNIT_SIZE, SCREEN_HIGHT - UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
      }
      for (int i = 0; i <= SCREEN_HIGHT / UNIT_SIZE; i++) {
        g.fillRect(0, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
      }
      for (int i = 0; i <= SCREEN_HIGHT / UNIT_SIZE; i++) {
        g.fillRect(SCREEN_WIDTH - UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
      }
    }
  }

  public void drawLines(Graphics g) {
    if (running == true) {
      g.setColor(Color.GRAY);
      for (int i = 0; i < SCREEN_HIGHT / UNIT_SIZE; i++) {
        g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HIGHT);
        g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
      }
    }
  }

  public static void newApple() {
    int tempX = random.nextInt(FIELD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
    int tempY = random.nextInt(FIELD_HIGHT / UNIT_SIZE) * UNIT_SIZE;
    if (tempX == 0) {
      appleX = UNIT_SIZE;
    } else {
      appleX = tempX;
    }
    if (tempY == 0) {
      appleY = UNIT_SIZE;
    } else {
      appleY = tempY;
    }
  }

  public void move() {
    for (int i = bodyParts; i > 0; i--) {
      x[i] = x[i - 1];
      y[i] = y[i - 1];
    }

    switch (direction) {
      case ('U'):
        y[0] = y[0] - UNIT_SIZE;
        break;
      case ('D'):
        y[0] = y[0] + UNIT_SIZE;
        break;
      case ('R'):
        x[0] = x[0] + UNIT_SIZE;
        break;
      case ('L'):
        x[0] = x[0] - UNIT_SIZE;
        break;
    }
  }

  public void checkApple() {
    if (x[0] == appleX && y[0] == appleY) {
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }

  public void checkCollisions() {
    for (int i = bodyParts; i > 0; i--) {
      if (x[0] == x[i] && y[0] == y[i]) {
        running = false;
      }
    }
    if (x[0] < UNIT_SIZE || x[0] > FIELD_WIDTH - UNIT_SIZE) {
      running = false;
    }
    if (y[0] < UNIT_SIZE || y[0] > FIELD_HIGHT - UNIT_SIZE) {
      running = false;
    }
  }

  public void gameOver(Graphics g) {
    g.setColor(Color.RED);
    g.setFont(new Font("Ink Free", Font.BOLD, 40));
    FontMetrics metrics1 = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten,
        (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
        (SCREEN_HIGHT / 2) + 50);

    g.setColor(Color.RED);
    g.setFont(new Font("Ink Free", Font.BOLD, 75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());
    g.drawString("Game Over!", (SCREEN_WIDTH - metrics2.stringWidth("Game Over!")) / 2,
        SCREEN_HIGHT / 2);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (running == true) {
      move();
      checkApple();
      checkCollisions();
    }
    repaint();
  }
}
