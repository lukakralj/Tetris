package gameTetris.Shapes;

import java.util.Random;

/**
 * This class represents the shape J:
 *
 *              []
 *              []
 *            [][]
 *
 * @author Luka Kralj
 * @version 17 June 2018
 */
public class J extends Shape {
    private static final int[][] schema1 = {
            {1, 0, 0},
            {1, 1, 1}
    };

    private static final int[][] schema2 = {
            {1, 1},
            {1, 0},
            {1, 0}
    };

    private static final int[][] schema3 = {
            {1, 1, 1},
            {0, 0, 1}
    };

    private static final int[][] schema4 = {
            {0, 1},
            {0, 1},
            {1, 1}
    };

    /**
     * Create new shape J.
     */
    public J() {
        super("-fx-background-color: blue;");
        Random rand = new Random();
        switch (rand.nextInt(4)){
            case 0: currentSchema = schema1; break;
            case 1: currentSchema = schema2; break;
            case 2: currentSchema = schema3; break;
            case 3: currentSchema = schema4; break;
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
        else if (currentSchema == schema2) {
            currentSchema = schema3;
        }
        else if (currentSchema == schema3) {
            currentSchema = schema4;
        }
        else {
            currentSchema = schema1;
        }
    }
}
