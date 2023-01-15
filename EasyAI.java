/**
 * @author Mark Wang
 * 1/14/2023
 *
 * This class will implement the simple AI that will use a randomized
 * attack strategy when incorporated.
 */

import java.util.Random;

public class EasyAI {

    private Random random;
    private int rowNum;
    private int colNum;

    /**
     * This constructor will enable the initialization for the EasyAI class, and it
     * will initialize and set the appropriate values and objects
     */
    public EasyAI() {
        random = new Random(); // initialize random object
        // set default values for row and column number
        rowNum = 0;
        colNum = 0;
    }

    /**
     * This method will return which row and column to guess using Random object.
     * It will pass this onto the main game class in order to proceed with the game
     * @return
     */
    private Coordinate guessCoordinate() {
        rowNum = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        colNum = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        return new Coordinate(rowNum, colNum);
    }

    /**
     * This method will get the information on whether the guessed coordinates was a hit or miss.
     * However, since this is an Easy AI, it will do nothing about this since it has no strategy
     * based on if it's a hit or miss, so nothing will be implemented here.
     * @param hit
     */
    private void getInformation(boolean hit) {

    }
}
