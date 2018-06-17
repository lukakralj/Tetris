package gameTetris.Shapes;

/**
 * This class represents the main framework for all the shapes.
 *
 * @author Luka Kralj
 * @version 10 June 2018
 */
public abstract class Shape {
    /** The color of the shape in form: "-fx-background-color: #color". */
    protected String styleColour;
    /** Rectangular schema that represents the shape. */
    protected int[][] currentSchema;

    /**
     * Create new shape of certain colour.
     *
     * @param styleColour The color of the shape in form: "-fx-background-color: #color".
     */
    public Shape(String styleColour) {
        this.styleColour = styleColour;
    }

    /**
     *
     * @return The color of the shape in form: "-fx-background-color: #color".
     */
    public String getStyleColour() {
        return styleColour;
    }

    /**
     *
     * @return Current schema of the shape.
     */
    public int[][] getCurrentSchema() {
        return currentSchema;
    }

    /**
     *
     * @return Width of the shape.
     */
    public int getWidth() {
        return currentSchema[0].length;
    }

    /**
     *
     * @return Height of the shape.
     */
    public int getHeight() {
        return currentSchema.length;
    }

    /**
     * Set new schema to the shape. Must be one of the
     * schemas defined for that shape.
     *
     * @param newSchema New schema for the shape.
     */
    public void setCurrentSchema(int[][] newSchema) {
        currentSchema = newSchema;
    }

    /**
     * Rotate the shape clockwise by changing its
     * schema appropriately.
     */
    public abstract void rotate();


}
