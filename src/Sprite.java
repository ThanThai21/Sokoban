/**
 * Created by ThaiBK on 12/13/2016.
 */
public abstract class Sprite {
    private int line;
    private int column;

    //Constructor khởi tạo vị trí của dối tượng
    public Sprite(int line, int column) {
        this.line = line;
        this.column = column;
    }

    //Phương thức setter hàng, cột của đối tượng
    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    //Phương thức getter hàng, cột của đối tượng
    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }

    //Phương thức kiểm tra xem khi di chuyển có xảy ra va chạm hay không
    public char checkCollision(Sprite sprite) {
        if (this.line == sprite.getLine()) {
            if (this.column == sprite.getColumn()+1) {
                return 'L';
            } else if (this.column == sprite.getColumn()-1) {
                return 'R';
            }
        } else if (this.column == sprite.getColumn()) {
            if (this.line == sprite.getLine()+1) {
                return 'T';
            } else if (this.line == sprite.getLine()-1) {
                return 'B';
            }
        }
        return 0;
    }

    public boolean isCollision(Sprite sprite, char type) {
        if (type == checkCollision(sprite)) {
            return true;
        } else {
            return false;
        }
    }
    public abstract void move(int desLine, int desColumn);
}
