/**
 * This class will implement a custom object that allows for easier accessing
 * and setting of coordinates
 */

public class Coordinate {

    private int rowNum; // variable that is associated with the  (1, 2, ..., 10)
    private int colNum; // variable that is associated with the column number (1, 2, ..., 10)

    /**
     * This constructor method will instantiate a new instance of the Coordinate class.
     * It will take in the row number and column number and assign it to the current coordinate
     * @param rowNum
     * @param colNum
     */
    public Coordinate(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
}
