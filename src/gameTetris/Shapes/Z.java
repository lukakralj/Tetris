package gameTetris.Shapes;

import java.util.Random;

/**
 * This class represents the shape Z:
 *
 *            [][]
 *              [][]
 *
 * @author Luka Kralj
 * @version 17 June 2018
 */
public class Z extends Shape {
    private static final int[][] schema1 = {
            {1, 1, 0},
            {0, 1, 1},
    };

    private static final int[][] schema2 = {
            {0, 1},
            {1, 1},
            {1, 0}
    };

    /**
     * Create new shape Z.
     */
    public Z() {
        super("-fx-background-color: red;");
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
