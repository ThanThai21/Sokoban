import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by ThaiBK on 12/14/2016.
 */
public class GameGUI implements ActionListener{
    protected GameBoard gameBoard = new GameBoard();
    protected JButton newGame;
    protected JButton preGame;
    protected JButton nextGame;
    protected JButton restart;

    public GameGUI() throws IOException {
    }

    public Container creatGUI() {
        JPanel panel = new JPanel(new BorderLayout());
        restart = createButton(restart,"Restart",new ImageIcon("restart.png"));
        restart.setVisible(false);
        preGame = createButton(preGame,"Previous",new ImageIcon("pre.png"));
        preGame.setVisible(false);
        nextGame = createButton(nextGame,"Next",new ImageIcon("next.png"));
        nextGame.setVisible(false);
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        JPanel jPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("-ThaiBK-",JLabel.CENTER);
        panel.add(gameBoard,BorderLayout.CENTER);
        panel.add(jPanel,BorderLayout.SOUTH);
        jPanel.add(bottomPanel,BorderLayout.NORTH);
        jPanel.add(newGame,BorderLayout.SOUTH);
        bottomPanel.add(preGame);
        bottomPanel.add(restart);
        bottomPanel.add(nextGame);
        return panel;
    }

    public JButton createButton(JButton button, String str, Icon icon) {
        button = new JButton();
        button.setIcon(icon);
        button.setVisible(false);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        button.setVisible(true);
        return button;
    }

    public void restartGame() throws IOException {
        gameBoard.restartGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            gameBoard.start = true;
            try {
                this.restartGame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == newGame) {
            newGame.setVisible(false);
            gameBoard.start = true;
            preGame.setVisible(true);
            restart.setVisible(true);
            nextGame.setVisible(true);
            try {
                this.restartGame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == preGame && gameBoard.level > 1) {
            gameBoard.level -= 1;
            gameBoard.start = true;
            preGame.setVisible(true);
            restart.setVisible(true);
            nextGame.setVisible(true);
            try {
                this.restartGame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == nextGame && gameBoard.level < 3) {
            gameBoard.level += 1;
            gameBoard.start = true;
            preGame.setVisible(true);
            restart.setVisible(true);
            nextGame.setVisible(true);
            try {
                this.restartGame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return;
    }
}
