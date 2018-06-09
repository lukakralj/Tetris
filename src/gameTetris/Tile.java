package gameTetris;

import javafx.scene.control.Label;

public class Tile extends Label {
    private boolean empty;

    public Tile() {
        setStyle("-fx-background-color: white;");
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void occupy(String styleColour) {
        setStyle(styleColour);
        empty = false;
    }

    public void free() {
        setStyle("-fx-background-color: white;");
        empty = true;
    }
}
