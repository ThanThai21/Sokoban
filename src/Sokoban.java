import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by ThaiBK on 12/13/2016.
 */
public class Sokoban extends JFrame {
    public static void main(String args[]) throws IOException {
       JFrame frame = new JFrame("Sokoban----Made by ThaiTv");
        GameGUI gameGUI = null;
        try {
            gameGUI = new GameGUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setContentPane(gameGUI.creatGUI());
        frame.setSize(605,440);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
//        frame.pack();
    }
}
