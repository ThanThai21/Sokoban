import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ThaiBK on 12/13/2016.
 */
public class GameBoard extends Canvas implements KeyListener,MouseListener{
    private CellMatrix map = new CellMatrix();
    private ArrayList walls = new ArrayList();
    private ArrayList boxs = new ArrayList();
    private ArrayList targets = new ArrayList();
    private ArrayList emptys = new ArrayList();
    private Player player;
    private Image[] images = new Image[5]; //0. trống; 1. tường; 2. người; 3. hộp; 4. đích
    private String[] imageStr = {"empty.png","wall.png","player.png","box.png","target.png"};
    protected Icon pre, res, next;
    private boolean completed = false;
    private int number = 0;
    protected int flag = 0;
    protected int level = 1;
    protected boolean start = false;

    //Khởi tạo khung game
    public GameBoard() throws IOException {
        for (int i = 0; i < 5; i++) {
            images[i] = getToolkit().getImage(""+imageStr[i]);
        }
        map.setMap(level);
        init();
        addKeyListener(this);
        addMouseListener(this);
    }

    public void init() {
        number = 0;
        for (int i = 0; i < map.MAX_LINE; i++) {
            for (int j = 0; j < map.MAX_COL; j++) {
                if (map.matrix[i][j] == 0) {
                    Empty temp = new Empty(i,j);
                    emptys.add(temp);
                }
                if (map.matrix[i][j] == 1) {
                    Wall temp = new Wall(i,j);
                    walls.add(temp);
                } else if (map.matrix[i][j] == 2) {
                    player = new Player(i,j);
                    Empty temp = new Empty(i,j);
                    emptys.add(temp);
                } else if (map.matrix[i][j] == 3) {
                    Box temp = new Box(i,j);
                    boxs.add(temp);
                    Empty temp1 = new Empty(i,j);
                    emptys.add(temp1);
                } else if (map.matrix[i][j] == 4) {
                    Target temp = new Target(i,j);
                    targets.add(temp);
                    number++;
                    Empty temp1 = new Empty(i,j);
                    emptys.add(temp1);
                }
            }
        }
    }


    //Vẽ đối tượng
    public void paint(Graphics g) {
        if (!start) {
            drawBackground(g);
            drawWaitScreen(g,map.start);
        } else {
            drawCell(g);
            drawInfo(g);
        }
        if (completed) {
            drawBackground(g);
            drawWaitScreen(g,map.complete);
        }
    }

    //Vẽ màn hình chờ
    public void drawWaitScreen(Graphics g, String str) {
        int l = 10;
        int c = 6;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '#') {
                    g.drawImage(images[1],c*10,l*10,10,10,this);
                    c++;
                } else if (str.charAt(i) == '\n') {
                    l++;
                    c = 6;
                } else {
                    c++;
                }
            }
    }

    public void drawBackground(Graphics g) {
        Image bg = getToolkit().getImage("bg.jpg");
        g.drawImage(bg,0,0,605,440,this);
    }

    public void drawInfo(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Level " + level,Font.BOLD,14));
        g.drawString("Level: " + level + " | Score: " + flag, 10,350);

    }

    public void drawCell(Graphics g) {
        ArrayList cell = new ArrayList();
        cell.addAll(emptys);
        cell.addAll(walls);
        cell.addAll(targets);
        cell.add(player);
        cell.addAll(boxs);
        for (int i = 0; i < cell.size(); i++) {
            Sprite item = (Sprite) cell.get(i);
            if (item instanceof Wall) {
                int line = item.getLine();
                int column = item.getColumn();
                g.drawImage(images[1],30*column,30*line,30,30,this);
            } else if (item instanceof Player) {
                int line = item.getLine();
                int column = item.getColumn();
                g.drawImage(images[2],30*column,30*line,30,30,this);
            } else if (item instanceof Box) {
                int line = item.getLine();
                int column = item.getColumn();
                g.drawImage(images[3],30*column,30*line,30,30,this);
            } else if (item instanceof Target) {
                int line = item.getLine();
                int column = item.getColumn();
                g.drawImage(images[4],30*column,30*line,30,30,this);
            } else {
                int line = item.getLine();
                int column = item.getColumn();
                g.drawImage(images[0],30*column,30*line,30,30,this);
            }
        }
    }

    //Phương thức kiểm tra đối tượng có va chạm với tường hay không
    public boolean checkWallCollision(Sprite sprite, char type) {
        Wall temp;
        if (type == 'L') {
            for (int i = 0; i < walls.size(); i++) {
                temp = (Wall) walls.get(i);
                if (sprite.isCollision(temp,'L')) {  //Nếu tường ở bên trái sprite
                    return true;
                }
            }
            return false;
        } else if (type == 'R') {
            for (int i = 0; i < walls.size(); i++) {
                temp = (Wall) walls.get(i);
                if (sprite.isCollision(temp,'R')) {
                    return true;
                }
            }
            return false;
        } else if (type == 'T') {
            for (int i = 0; i < walls.size(); i++) {
                temp = (Wall) walls.get(i);
                if (sprite.isCollision(temp,'T')) {
                    return true;
                }
            }
            return false;
        } else if (type == 'B') {
            for (int i = 0; i < walls.size(); i++) {
                temp = (Wall) walls.get(i);
                if (sprite.isCollision(temp,'B')) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    //Phương thức kiểm tra xem người có va chạm với hộp hay không
    public boolean checkBoxCollision(char type) {
        if (type == 'L' || type == 'R') {
            for (int i = 0; i < boxs.size(); i++) {
                Box boxTemp = (Box) boxs.get(i);
                if (player.isCollision(boxTemp,type)) {
                    for (int j = 0; j < boxs.size(); j++) {
                        Box temp = (Box) boxs.get(j);
                        if (!boxTemp.equals(temp)) {
                            if (( boxTemp).isCollision(temp,type)) {
                                return true;
                            }
                        }
                    }
                    if (checkWallCollision(boxTemp,type)) {
                        return true;
                    }
                    boxTemp.move(boxTemp.getLine(),boxTemp.getColumn()+convert(type));
                }
                isComplete();
            }
        }
        if (type == 'B' || type == 'T') {
            for (int i = 0; i < boxs.size(); i++) {
                Box boxTemp = (Box) boxs.get(i);
                if (player.isCollision(boxTemp,type)) {
                    for (int j = 0; j < boxs.size(); j++) {
                        Box temp = (Box) boxs.get(j);
                        if (!boxTemp.equals(temp)) {
                            if (( boxTemp).isCollision(temp,type)) {
                                return true;
                            }
                        }
                    }
                    if (checkWallCollision(boxTemp,type)) {
                        return true;
                    }
                    boxTemp.move(boxTemp.getLine()+convert(type),boxTemp.getColumn());
                }
                isComplete();
            }
        }
        return false;
    }

    public int convert(char type) {
        if (type == 'L' || type == 'T') {
            return -1;
        } else if (type == 'R' || type == 'B') {
            return 1;
        }
        return 0;
    }

    public char convertKey(int key) {
        switch (key) {
            case KeyEvent.VK_LEFT: {
                return 'L';
            }
            case KeyEvent.VK_RIGHT: {
                return 'R';
            }
            case KeyEvent.VK_UP: {
                return 'T';
            }
            case KeyEvent.VK_DOWN: {
                return 'B';
            }
        }
        return 0;
    }

    //Phương thức kiểm tra hoàn thành nhiệm vụ
    public void isComplete() {
        flag = 0;
        for (int i = 0; i < boxs.size(); i++) {
            for (int j = 0; j < targets.size(); j++) {
                Box box = (Box) boxs.get(i);
                Target target = (Target) targets.get(j);
                if (box.getLine() == target.getLine() && box.getColumn() == target.getColumn()) {
                    flag++;
                }
            }
        }
        if (flag < number) {
            completed = false;
        }
        else {
            completed = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (completed) {
            return;
        }

        int key = e.getKeyCode();
        System.out.println(convertKey(key));
        if (checkWallCollision(player,convertKey(key))) {
            System.out.println("Wall");
            return;
        }
        if (checkBoxCollision(convertKey(key))) {
            System.out.println("Box");
            return;
        }
        if (convertKey(key) == 'L' || convertKey(key) == 'R') { //Di chuyển cùng hàng
            player.move(player.getLine(),player.getColumn()+convert(convertKey(key)));
            System.out.println("Line = " + player.getLine() + ". Column = " + player.getColumn());
        } else if (convertKey(key) == 'B' || convertKey(key) == 'T') { //Di chuyển cùng cột
            player.move(player.getLine()+convert(convertKey(key)),player.getColumn());
            System.out.println("Line = " + player.getLine() + ". Column = " + player.getColumn());
        }
        repaint();
    }

    public void restartGame() throws IOException {
        map.resetMatrix(level);
        walls.clear();
        targets.clear();
        emptys.clear();
        boxs.clear();
        init();
        if (completed) {
            completed = false;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}