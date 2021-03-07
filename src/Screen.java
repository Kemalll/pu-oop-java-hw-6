import javax.swing.*;

public class Screen extends JFrame {

    public Screen(){

        this.add(new GamePanel());
        this.setTitle("Смок");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
