import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    public static final int UNIT_SIZE = 30;
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    public static final int DELAY = 87;
    public final int[] x = new int[GAME_UNITS];
    public final int[] y = new int[GAME_UNITS];
    public int fed = 5;
    public int bodyParts = 1;
    public int applesEaten;
    public int appleX;
    public int appleY;
    public int wallsize = 4;
    public int wallX;
    public int wallY;
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
        this.setBackground(new Color(4, 10, 66, 252));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    //Starts the game
    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        getWalls();
        newApple();
//        movingApple();
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
            g.setColor(new Color(101, 15, 7));//Food colors
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i< wallsize; i++) {
                g.setColor(new Color(132, 74, 153));
                g.fill3DRect(wallX, wallY, UNIT_SIZE, UNIT_SIZE,true);
            }
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 42, 70));    //Head Color
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(0, 45, 65));  //Body Color
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Text", Font.PLAIN, 100));
            FontMetrics metrics = getFontMetrics(g.getFont());

            g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize() / 2);
            g.setFont(new Font("Helper", Font.ROMAN_BASELINE, 100));
            g.drawString(">>", SCREEN_WIDTH - 200, g.getFont().getSize() / 2);
            g.setFont(new Font("Helper", Font.ROMAN_BASELINE, 100));
            g.drawString("|/", SCREEN_WIDTH / 2, g.getFont().getSize() + 650);

        } else if (isWin) {
            win(g);
        } else gameOver(g);
    }

    /**
     * Creates food
     */
    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void movingApple() {
//        for ()
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
//            movingApple();
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
        }
        if ((x[0] == wallX) && (y[0] == wallY)) {
            running = false;
        }
        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH;
            running = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            x[0] = 0;
//            running = false;
        }
        if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT;
            running = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            y[0] = 0;
        }
        if (!running) {
            timer.stop();
        }
    }

    public int[] Walls() {                             //Creates the walls
        random=new Random();
        int[]coordinates=new int[2];
        wallX  = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        wallY  = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        coordinates[0]=wallX;
        coordinates[1]=wallY;
        return coordinates;
    }
    public void getWalls() {                 
       for (int i =0;i< wallsize;i++){
         Walls();
       }
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
