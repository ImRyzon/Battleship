/**
 * This class will implement a custom object that allows for easier accessing
 * and setting of coordinates
 */

public class Coordinate {

    private int x; // variable that is associated with the row number (1, 2, ..., 10)
    private int y; // variable that is associated with the column number (1, 2, ..., 10)

    /**
     * This constructor method will instantiate a new instance of the Coordinate class.
     * It will take in the row number and column number and assign it to the current coordinate
     * @param rowNum
     * @param colNum
     */
    public Coordinate(int rowNum, int colNum) {
        this.x = rowNum;
        this.y = colNum;
    }

    /**
     * setter for x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter for x
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * setter for y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter for y
     * @return
     */
    public int getY() {
        return this.y;
    }
}
