/**
 * Created by ThaiBK on 12/13/2016.
 */
public class Box extends Sprite {
    public Box(int line, int column) {
        super(line, column);
    }

    //Di chuyển hộp đến vị trí với
    public void move(int desLine, int desColumn) {
        this.setLine(desLine);
        this.setColumn(desColumn);
    }
}
