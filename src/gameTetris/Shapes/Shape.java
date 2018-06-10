package gameTetris.Shapes;

public abstract class Shape {
    protected String styleColour;
    protected int[][] currentSchema;

    public Shape(String styleColour) {
        this.styleColour = styleColour;
    }

    public String getStyleColour() {
        return styleColour;
    }

    public int[][] getCurrentSchema() {
        return currentSchema;
    }

    public int getWidth() {
        return currentSchema.length;
    }

    public int getHeight() {
        return currentSchema[0].length;
    }

    public abstract void rotate();


}
