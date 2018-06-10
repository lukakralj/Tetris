package gameTetris.Shapes;

import java.util.Random;

/**
 * This class represents the shape I:
 *
 *              []
 *              []
 *              []
 *              []
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public class I extends Shape {
    private static final int[][] schema1 = {
            {1, 1, 1, 1}
    };

    private static final int[][] schema2 = {
            {1},
            {1},
            {1},
            {1}
    };

    /**
     * Create new shape I.
     */
    public I() {
        super("-fx-background-color: cyan;");
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
