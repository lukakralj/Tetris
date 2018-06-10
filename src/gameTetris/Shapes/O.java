package gameTetris.Shapes;

/**
 * This class represents the shape O:
 *
 *              [][]
 *              [][]
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public class O extends Shape {
    private static final int[][] schema = {
            {1, 1},
            {1, 1}
    };

    /**
     * Create new shape O.
     */
    public O() {
        super("-fx-background-color: yellow;");
        currentSchema = schema;
    }

    /**
     * @see Shape
     */
    @Override
    public void rotate() {
        // Schema does not change.
    }
}
