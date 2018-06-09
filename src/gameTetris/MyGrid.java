package gameTetris;

import javafx.scene.layout.TilePane;

public class MyGrid extends TilePane {
    private static final int CELL_DIMENS = 15;
    private static final int NO_COL = 20;
    private static final int NO_ROW = 40;

    private Tile[][] grid;

    public MyGrid() {
        grid = new Tile[NO_COL][NO_ROW];
        setVgap(1);
        setHgap(1);
        setPrefColumns(NO_COL);
        setPrefRows(NO_ROW);
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < NO_ROW; row++) {
                Tile tile = new Tile();
                tile.setMinSize(CELL_DIMENS, CELL_DIMENS);
                tile.setMaxSize(CELL_DIMENS, CELL_DIMENS);
                getChildren().add(tile);
                grid[col][row] = tile;
            }
        }
    }

    public void reset() {
        for (int col = 0; col < NO_COL; col++) {
            for (int row = 0; row < NO_ROW; row++) {
                grid[col][row].free();
            }
        }
    }
}
