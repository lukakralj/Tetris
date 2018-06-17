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

    private int countSZ;

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
    public void reset() {
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
    public void moveOneDown() {
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
    private boolean canDrop() {
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            if (shapeStart[0] + currentShape.getHeight() == NO_ROW) {
                return false;
            }
            if (currentShape.getCurrentSchema()[currentShape.getHeight() - 1][col - shapeStart[1]] == 1) {
                if (!grid[shapeStart[0] + currentShape.getHeight()][col].isEmpty()) {
                    {
                        return false;
                    }
                }
            }
            else {
                if (shapeStart[0] + currentShape.getHeight() - 1 == -1) {
                    continue;
                }
                else if (!grid[shapeStart[0] + currentShape.getHeight() - 1][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Moves the shape one row down and updates the tiles accordingly.
     */
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
            else {
                break;
            }
        }
        shapeStart[0]++;
    }

    /**
     * Check if there are any new rows and remove them.
     */
    private void checkFullRows() {
        System.out.println("Checked full rows.");
    }

    /**
     * Moves the shape one column to the left, if possible.
     */
    public void moveOneLeft() {
        if (currentShape == null) {
            return;
        }
        System.out.println("LEFT pressed");
    }

    /**
     * Moves the shape one column to the right, if possible.
     */
    public void moveOneRight() {
        if (currentShape == null) {
            return;
        }
        System.out.println("RIGHT pressed");
    }

    /**
     * Rotate the shape.
     */
    public void rotate() {
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
    private void clearOldSchema() {
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
    private void drawShape() {
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
    private boolean canBeDrawn() {
        for (int col = shapeStart[1]; col < shapeStart[1] + currentShape.getWidth(); col++) {
            for (int row = shapeStart[0]; row < shapeStart[0] + currentShape.getHeight(); row++) {
                if (currentShape.getCurrentSchema()[row - shapeStart[0]][col - shapeStart[1]] == 1) {
                    if (row < 0) {
                        continue;
                    }
                    if (!grid[row][col].isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
