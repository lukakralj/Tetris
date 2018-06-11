package gameTetris;

import gameTetris.Shapes.*;
import javafx.scene.layout.TilePane;

import java.util.Random;

/**
 * This class represents the main grid of the game.
 * The grid is composed of Tiles.
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public class MyGrid extends TilePane {
    /** Size of one side of a square tile. */
    private static final int CELL_DIMENS = 15;
    private static final int NO_COL = 20;
    private static final int NO_ROW = 40;

    private Tile[][] grid;
    private Shape currentShape;
    /** Top left corner of the shape's schema. */
    private int[] shapeStart;

    /**
     * Create new grid of Tiles.
     *
     * @see TilePane
     */
    public MyGrid() {
        super();
        grid = new Tile[NO_COL][NO_ROW];
        currentShape = null;
        shapeStart = new int[2];

        setVgap(1);
        setHgap(1);
        setPrefColumns(NO_COL);
        setPrefRows(NO_ROW);
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < 1; row++) {
                Tile tile = new Tile();
                tile.setMinSize(CELL_DIMENS, CELL_DIMENS);
                tile.setMaxSize(CELL_DIMENS, CELL_DIMENS);
                getChildren().add(tile);
                grid[col][row] = tile;
            }
        }
    }

    /**
     * Free all the cells in the grid.
     */
    public void reset() {
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < NO_ROW; row++) {
                grid[col][row].free();
            }
        }
    }

    public void step() {
        if (currentShape == null) {
            currentShape = getRandomShape();
            Random rand = new Random();
            int col = rand.nextInt(NO_COL - currentShape.getWidth());
            shapeStart[0] = col;
            shapeStart[1] = - currentShape.getHeight();
        }

        moveOneDown();

    }

    /**
     *
     * @return One of seven possible shapes.
     */
    private Shape getRandomShape() {
        // TODO: Update method when more shapes are added.
        Random rand = new Random();
        int next = rand.nextInt(2);

        if (next == 0) {
            return new O();
        }
        else {
            return new I();
        }
    }

    public void moveOneDown() {
        if (canDrop()) {
            performDrop();
        }
        else {
            currentShape = null;
            checkFullRows();
        }
    }

    /**
     * Check if the shape can move down one row.
     *
     * @return True if the shape can be moved down, false otherwise.
     */
    private boolean canDrop() {
        for (int col = shapeStart[0]; col < shapeStart[0] + currentShape.getWidth(); col++) {
            if (!grid[col][shapeStart[1] + currentShape.getHeight()].isEmpty()) {
                if (currentShape.getCurrentSchema()[col - shapeStart[0]][currentShape.getHeight() - 1] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void performDrop() {
        for (int row = shapeStart[1] + currentShape.getHeight() - 1; row >= 0; row--) {
            for (int col = shapeStart[0]; col < shapeStart[0] + currentShape.getWidth(); col++) {
                if (currentShape.getCurrentSchema()[col - shapeStart[0]][row - shapeStart[1]] == 1) {
                    grid[col][row].occupy(currentShape.getStyleColour());
                    grid[col][row].free();
                }
            }
        }
        if (shapeStart[1] < 0) {
            for (int col = shapeStart[0]; col < shapeStart[0] + currentShape.getWidth(); col++) {
                if (currentShape.getCurrentSchema()[col - shapeStart[0]][- shapeStart[1] - 1] == 1) {
                    grid[col][0].occupy(currentShape.getStyleColour());
                }
            }
        }
        shapeStart[1]++;
    }

    private void checkFullRows() {
        System.out.println("Checked full rows.");
    }

    public void moveOneLeft() {

    }

    public void moveOneRight() {

    }


}
