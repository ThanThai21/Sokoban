import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by ThaiBK on 12/13/2016.
 */
public class CellMatrix {
    protected final int MAX_COL = 20;
    protected final int MAX_LINE = 11;
    protected int[][] matrix = new int[MAX_LINE][MAX_COL];
    String start = "##### ###### #   # ###### #####    ###   #     #\n"
                +  "#     #    # #  #  #    # #    #  #   #  ##    #\n"
                +  "#     #    # # #   #    # #    # #     # # #   #\n"
                +  "##### #    # ##    #    # ###### #     # #  #  #\n"
            +      "    # #    # # #   #    # #    # ####### #   # #\n"
            +      "    # #    # #  #  #    # #    # #     # #    ##\n"
            +      "##### ###### #   # ###### ###### #     # #     #\n";
    String complete = "#           #           # ###### #     #\n"
                     +" #         # #         #  #    # ##    #\n"
                     +"  #       #   #       #   #    # # #   #\n"
                     +"   #     #     #     #    #    # #  #  #\n"
                     +"    #   #       #   #     #    # #   # #\n"
                     +"     # #         # #      #    # #    ##\n"
                     +"      #           #       ###### #     #\n";



    public void resetMatrix(int level) throws IOException {
        setMap(level);
    }

    public void setMap(int level) throws IOException {

        Reader map = null;
        try {
            map = new FileReader("map.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int temp = -1;
        int tmp;
        int i = 0;
        int j = 0;
        do {
            temp = map.read();
            System.out.println(temp);
        } while (temp != (char)(level+48));
        while ((temp = map.read()) != -1) {
            if (temp == ' ') {
                matrix[i][j] = 9;
                j++;
            } else if (temp == '.') {
                matrix[i][j] = 0;
                j++;
            } else if (temp == '#') {
                matrix[i][j] = 1;
                j++;
            } else if (temp == '@') {
                matrix[i][j] = 2;
                j++;
            } else if (temp == '$') {
                matrix[i][j] = 3;
                j++;
            } else if (temp == '*') {
                matrix[i][j] = 4;
                j++;
            } else if (temp == '/') {
                for (int k = j; k < MAX_COL; k++) {
                    matrix[i][k] = 9;
                }
                i++;
                j = 0;
            } else if (temp == '!') {
                break;
            }
            if (i == MAX_LINE-1 && j == MAX_COL-1) {
                break;
            }
        }
//        for (int i1 = 0; i1 < MAX_LINE; i1++) {
//            for (int j1 = 0; j1 < MAX_COL; j1++) {
//                System.out.print(matrix[i1][j1]);
//            }
//            System.out.println();
//        }
        map.close();
    }
}
