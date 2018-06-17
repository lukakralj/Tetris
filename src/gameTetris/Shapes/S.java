package gameTetris.Shapes;

import java.util.Random;

/**
 * This class represents the shape S:
 *
 *              [][]
 *            [][]
 *
 * @author Luka Kralj
 * @version 17 June 2018
 */
public class S extends Shape {
    private static final int[][] schema1 = {
            {0, 1, 1},
            {1, 1, 0},
    };

    private static final int[][] schema2 = {
            {1, 0},
            {1, 1},
            {0, 1}
    };

    /**
     * Create new shape S.
     */
    public S() {
        super("-fx-background-color: green;");
        Random rand = new Random();
        if (rand.nextBoolean()) {
            currentSchema = schema1;
        }
        else {
            currentSchema = schema2;
        }
    }

    /**
     * @see Shape
     */
    @Override
    public void rotate() {
        if (currentSchema == schema1) {
            currentSchema = schema2;
        }
        else {
            currentSchema = schema1;
        }
    }
}
