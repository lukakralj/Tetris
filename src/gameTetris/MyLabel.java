package gameTetris;

import javafx.scene.control.Label;

public class MyLabel extends Label {
    private int col;
    private int row;

    public MyLabel(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
