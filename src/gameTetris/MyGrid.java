package gameTetris;

import gameTetris.Shapes.*;
import javafx.scene.layout.TilePane;
import java.util.Random;

/**
 * This class represents the main grid of the game.
 * The grid is composed of Tiles.
 *
 * @author Luka Kralj
 * @version 18 June 2018
 */
public class MyGrid extends TilePane {
    /** Size of one side of a square tile. */
    private static final int CELL_DIMENS = 15;
    private static final int NO_COL = 20;
    private static final int NO_ROW = 40;

    volatile private Controller controller;
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
    public MyGrid(Controller controller) {
        super();
        this.controller = controller;
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
        try {
            createTitle("src/gameTetris/title.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the initial title to be shown and creates the numerical
     * values of the gaps between the grid and the title in order to fit it
     * on the screen
     * @param path the path of the .txt file containing the title information
     */
    private void createTitle(String path) throws Exception {
        int leftGap, bottomGap;

        Title title = new Title(path, this);

        //calculate the values of the gaps based on the grid's size

        //the title's placed in the middle of the grid
        if((NO_COL - title.getWidth()) % 2 != 0 ){
            leftGap = (NO_COL + 1 - title.getWidth()) / 2;
        }
        else{
         leftGap = (NO_COL - title.getWidth()) / 2;
        }


        //the title's placed in the lower third of the grid
        if(NO_ROW % 3 != 0 ){
            bottomGap = NO_ROW;

            while(bottomGap % 3 != 0){
            bottomGap --;
              }
            bottomGap = bottomGap/ 3 * 2;
        }
        else{
            bottomGap = NO_ROW / 3 * 2;
        }


        //retrieve colors for all the tiles in the grid
        for (int row = 0; row < NO_ROW; row++) {
            for (int col = 0; col < NO_COL; col++) {
               String color = getColorFromPosition(row, col, leftGap, bottomGap, title);
                grid[row][col].setStyle(color);
            }
        }
    }


    /**
     * Retrieves the color to be assigned to the given pixel
     * @param row the row of the pixel
     * @param col the column of the pixel
     * @param leftGap the gap between the title and the vertical edges of the grid
     * @param bottomGap the gap between the title and the horizontal edges of the grid
     * @param title the Title
     * @return a string containing the .css color information for the given pixel
     */
    private String getColorFromPosition(int row, int col, int leftGap, int bottomGap, Title title) {

        String color = "-fx-background-color:";


        if(   col >= leftGap
           && col < NO_COL - leftGap
           && row > bottomGap
           && row <= bottomGap + title.getHeight()){

                switch(title.getValueAt(row - bottomGap, col-leftGap)){
                    case 0 : color += "white;"; break;
                    case 1:  color += "blue;"; break;
                    case 2 : color += "yellow;"; break;
                    case 3:  color += "red;"; break;
                    case 4 : color += "purple;"; break;
                    case 5:  color += "black;"; break;
                    case 6 : color += "green;"; break;
                    case 7:  color += "grey;"; break;
                }
        }
        else{color+="white;";}

        return color;
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
     * TODO: fix: "java.lang.IllegalStateException: Not on FX application thread"
     */
    private synchronized void checkFullRows() {
        for (int row = NO_ROW - 1; row >= 0; row--) {
            if (row == 0) {
                // TODO: End game.
                System.out.println("Game over");
                return;
            }
            int noOfFree = 0;
            for (int col = 0; col < NO_COL; col++) {
                if (grid[row][col].isEmpty()) {
                    noOfFree++;
                }
            }

            if (noOfFree == NO_COL) {
                // The whole line is empty. There cannot be anything above.
                break;
            } else if (noOfFree == 0) {
                removeRow(row);
                row++; // Check the same line again, after cascading.
            }
            // else continue scanning the rows
        }
    }

    /**
     * Removes the row specified and cascades all rows above.
     *
     * @param rowToRemove Index of the row to remove.
     */
    private synchronized void removeRow(int rowToRemove) {
        for (int row = rowToRemove; row > 0; row--) {
            for (int col = 0; col < NO_COL; col++) {
                grid[row][col].free();
                if (!grid[row - 1][col].isEmpty()) {
                    grid[row][col].occupy(grid[row - 1][col].getStyle());
                }
            }
        }
        controller.incrementScore(10);
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
