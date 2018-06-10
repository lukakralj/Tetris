package gameTetris.Shapes;

public class O extends Shape {
    private static final int[][] schema = {
            {1, 1},
            {1, 1}
    };

    public O() {
        super("-fx-background-color: yellow;");
        currentSchema = schema;
    }
    @Override
    public void rotate() {
        // Schema does not change.
    }
}
