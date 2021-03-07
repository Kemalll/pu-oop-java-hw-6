import javax.swing.*;
import java.awt.*;

import java.util.Random;


public class GamePanel extends JPanel{

    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 57;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int fed;
    int bodyParts = 1;
    int applesEaten;
    int appleX;
    int appleY;
    int wallX;
    int wallY;
    char direction = 'R';
    boolean isWin;
    boolean running = false;
    Timer timer;
    Random random;


    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(2, 13, 50));
        this.setFocusable(true);
        startGame();
    }


    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g) {

        for (int i = 0; i < bodyParts; i++) {
        }

        public void newApple () {
        }

        public void move () {
        }

        public void checkApple () {

        }

        public void checkCollisions () {
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                    }
                }
        }
                public void winCondition () {
                    if (applesEaten == fed) {
                        isWin = true;
                        running = false;
                        timer.stop();
                    }
                }
                public void win(){}

                public void gameOver (){
        }
    }
}

