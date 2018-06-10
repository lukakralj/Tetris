package gameTetris;

import javafx.scene.control.Label;

/**
 * This class represents one cell in the main game grid.
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public class Tile extends Label {
    private boolean empty;

    /**
     * Create new empty tile.
     */
    public Tile() {
        setStyle("-fx-background-color: white;");
        empty = true;
    }

    /**
     * The empty tile is shown as white on the GUI.
     *
     * @return True if tile is empty, false if not.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Occupy tile. This tile is now
     * @param styleColour
     */
    public void occupy(String styleColour) {
        if (!empty) {
            throw new RuntimeException("Tile is not empty!");
        }
        setStyle(styleColour);
        empty = false;
    }

    /**
     * Free the tile to be available for occupation again.
     */
    public void free() {
        setStyle("-fx-background-color: white;");
        empty = true;
    }
}
