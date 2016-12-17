/**
 * Created by ThaiBK on 12/13/2016.
 */
public class Player extends Sprite {
    public Player(int line, int column) {
        super(line, column);
    }

    //di chuyển người chơi đến vị trí mới
    public void move(int desLine, int desColumn) {
        this.setLine(desLine);
        this.setColumn(desColumn);
    }
}
