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
        grid = new Tile[NO_ROW][NO_COL];
        currentShape = null;
        shapeStart = new int[2];

        setVgap(1);
        setHgap(1);
        setPrefColumns(NO_COL);
        setPrefRows(NO_ROW);
        for (int row = 0; row < NO_ROW; row++) {
            for (int col = 0; col < NO_COL; col++) {
                Tile tile = new Tile();
                tile.setMinSize(CELL_DIMENS, CELL_DIMENS);
                tile.setMaxSize(CELL_DIMENS, CELL_DIMENS);
                getChildren().add(tile);
                grid[row][col] = tile;
            }
        }

    }

    /**
     * Free all the cells in the grid.
     */
    public void reset() {
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < NO_ROW; row++) {
                grid[row][col].free();
            }
        }
    }

    public void step() {
        if (currentShape == null) {
            currentShape = getRandomShape();
            Random rand = new Random();
            int col = rand.nextInt(NO_COL - currentShape.getWidth());
            shapeStart[0] = - currentShape.getHeight();
            shapeStart[1] = col;
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
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            if (!grid[shapeStart[0] + currentShape.getHeight()][col].isEmpty()) {
                if (currentShape.getCurrentSchema()[currentShape.getHeight() - 1][col - shapeStart[1]] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void performDrop() {
        for (int i = 0; i < currentShape.getHeight(); i++) {
            int row = shapeStart[0] + currentShape.getHeight() - i;
            if (row >= 0) {
                for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
                    if (currentShape.getCurrentSchema()[row - shapeStart[0] - 1][col - shapeStart[1]] == 1) {
                        grid[row][col].occupy(currentShape.getStyleColour());
                        if (row > 0) {
                            grid[row - 1][col].free();
                        }
                    }
                }
            }
        }
        shapeStart[0]++;
    }

    private void checkFullRows() {
        System.out.println("Checked full rows.");
    }

    public void moveOneLeft() {

    }

    public void moveOneRight() {

    }


}
