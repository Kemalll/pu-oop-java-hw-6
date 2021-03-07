import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 87;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int fed = 5;
    int bodyParts = 1;
    int applesEaten;
    int appleX;
    int appleY;
    int wallSize = 10;
    int wallX;
    int wallY;
    char direction = 'R';
    boolean isWin;
    boolean running = false;
    Timer timer;
    Random random;

    /**
     * Sets the game panel
     */
    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(2, 13, 50));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    //Starts the game
    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        Walls();
        newApple();
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    /**
     * Rendering the graphics of the game
     */
    public void draw(Graphics g) {

        if (running) {
            g.setColor(Color.RED);//Food colors
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.MAGENTA);
            g.fillRect(wallX, wallY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.RED);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(165, 35, 35));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Text", Font.PLAIN, 100));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize() / 2);
        }
        else if(isWin){win(g);}
        else gameOver(g);
    }

    /**
     * Creates food
     */
    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move() {
        for (int i = bodyParts; i > 0; i--) {   //Shifts the body of the snake
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }

    /**
     * Checks if you get the food
     */
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    /**
     * Check does the snake go heads on to wall or itself
     */
    public void checkCollisions() {
        //The head of the snake
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            if ((x[0] == wallX) && (y[0] == wallY)){
                running=false;
            }
        }
        if (x[0] < 0) {
            running = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
    
    public void Walls(){
        wallX=random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        wallY=random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;

    }
    public void winCondition(){       // Checks if you won the game
        if(applesEaten==fed){
            isWin=true;
            running=false;
            timer.stop();
        }
    }
    //Simple win message
    public void win(Graphics g){
        g.setColor(Color.CYAN);
        g.setFont(new Font("Text",Font.ITALIC,250));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("You Won!",
                (SCREEN_WIDTH - metrics.stringWidth("You Won!!!"))/2,
                SCREEN_HEIGHT/2);
    }
    //Gives message for lose and follows the score
    public void gameOver(Graphics g) {
        //Score follower
        g.setColor(Color.green);
        g.setFont(new Font("Text1", Font.PLAIN, 100));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten,
                (SCREEN_WIDTH - metrics1.stringWidth("Score:" + applesEaten)) / 2,
                g.getFont().getSize() / 2);

        //Game Over Text
        g.setColor(Color.RED);
        g.setFont(new Font("Text2",Font.BOLD,150));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
            winCondition();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                //Define which keys respones for move
                case KeyEvent.VK_LEFT:
                    //Correcting a move if it is impossible
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                        break;
                    }
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                        break;

                    }
            }
        }
    }
}
