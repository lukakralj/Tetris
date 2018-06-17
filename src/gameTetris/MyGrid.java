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

    volatile private Tile[][] grid;
    volatile private Shape currentShape;
    /** Top left corner of the shape's schema. */
    volatile private int[] shapeStart;

    volatile private int countSZ;

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
        countSZ = 0;

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
    public synchronized void reset() {
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < NO_ROW; row++) {
                grid[row][col].free();
                currentShape = null;
            }
        }
    }

    /**
     * Performs one step in the game. Organizes drop,
     * shape creation, rows elimination etc..
     */
    public synchronized void step() {
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
    private synchronized Shape getRandomShape() {
        Random rand = new Random();
        int next;

        if (countSZ == 4) {
            next = rand.nextInt(5);
        }
        else {
            next = rand.nextInt(7);
        }

        Shape toReturn = null;
        switch (next) {
            case 0:
                countSZ = 0;
                toReturn = new O();
                break;
            case 1:
                countSZ = 0;
                toReturn = new I();
                break;
            case 2:
                countSZ = 0;
                toReturn = new J();
                break;
            case 3:
                countSZ = 0;
                toReturn = new L();
                break;
            case 4:
                countSZ = 0;
                toReturn = new T();
                break;
            case 5:
                countSZ++;
                toReturn = new S();
                break;
            case 6:
                countSZ++;
                toReturn = new Z();
                break;
        }
        return toReturn;
    }

    /**
     * Moves the shape one rows down, or freezes it in place
     * if it reaches the end. Does not actually perform the drop of
     * the shape.
     */
    public synchronized void moveOneDown() {
        if (currentShape == null) {
            return;
        }
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
    private synchronized boolean canDrop() {
        clearOldSchema();
        shapeStart[0]++;
        boolean canDrop = canBeDrawn();
        shapeStart[0]--;
        drawShape();
        return canDrop;
    }

    /**
     * Moves the shape one row down and updates the tiles accordingly.
     */
    private synchronized void performDrop() {
        clearOldSchema();
        shapeStart[0]++;
        drawShape();
    }

    /**
     * Check if there are any new rows and remove them.
     */
    private synchronized void checkFullRows() {
        System.out.println("Checked full rows.");
    }

    /**
     * Rotate the shape.
     */
    public synchronized void rotate() {
        if (currentShape == null || currentShape instanceof O) {
            return;
        }
        //TODO: try to fix to work with all negative rows
        if (shapeStart[0] < 0) {
            return;
        }

        clearOldSchema();

        int[][] old = currentShape.getCurrentSchema();
        int oldW = currentShape.getWidth();

        currentShape.rotate();

        if (oldW >= currentShape.getWidth()) {
            for (int i = 0; i < currentShape.getHeight(); i++){
                if (canBeDrawn()) {
                    drawShape();
                    return;
                }
                else {
                    shapeStart[0]--;
                }
            }
        }
        else {
            for (int i = 0; i < currentShape.getWidth(); i++){
                if (NO_COL - shapeStart[1] < currentShape.getWidth()) {
                    if (shapeStart[1] == 0) {
                        break;
                    }
                    else {
                        shapeStart[1]--;
                    }
                }
                if (canBeDrawn()) {
                    drawShape();
                    return;
                }
            }
        }
        currentShape.setCurrentSchema(old);
        drawShape();
    }

    /**
     * Clear old shape to free the cells where the new schema might be drawn.
     */
    private synchronized void clearOldSchema() {
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            for (int row = shapeStart[0]; row < shapeStart[0] + currentShape.getHeight(); row++) {
                if (row < 0) {
                    continue;
                }
                if (currentShape.getCurrentSchema()[row - shapeStart[0]][col - shapeStart[1]] == 1) {
                    grid[row][col].free();
                }
            }
        }
    }

    /**
     * Draw the shape according to the new schema and position.
     */
    private synchronized void drawShape() {
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            for (int row = shapeStart[0]; row < shapeStart[0] + currentShape.getHeight(); row++) {
                if (row < 0) {
                    continue;
                }
                if (currentShape.getCurrentSchema()[row - shapeStart[0]][col - shapeStart[1]] == 1) {
                    grid[row][col].occupy(currentShape.getStyleColour());
                }
            }
        }
    }

    /**
     * Check if the shape can be drawn with the current schema and position.
     *
     * @return True if the shape can be drawn.
     */
    private synchronized boolean canBeDrawn() {
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            for (int row = shapeStart[0]; row < shapeStart[0] + currentShape.getHeight(); row++) {
                if (currentShape.getCurrentSchema()[row - shapeStart[0]][col - shapeStart[1]] == 1) {
                    if (row < 0) {
                        continue;
                    }
                    if (row >= NO_ROW) {
                        return false;
                    }
                    if (!grid[row][col].isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Moves the shape one column to the left, if possible.
     */
    public synchronized void moveOneLeft() {
        if (currentShape == null || shapeStart[1] == 0) {
            return;
        }
        clearOldSchema();
        shapeStart[1]--;
        if (!canBeDrawn()) {
            shapeStart[1]++;
        }
        drawShape();
    }

    /**
     * Moves the shape one column to the right, if possible.
     */
    public synchronized void moveOneRight() {
        if (currentShape == null || shapeStart[1] + currentShape.getWidth() == NO_COL) {
            return;
        }
        clearOldSchema();
        shapeStart[1]++;
        if (!canBeDrawn()) {
            shapeStart[1]--;
        }
        drawShape();
    }
}
