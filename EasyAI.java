/**
 * @author Mark Wang
 * 1/14/2023
 *
 * This class will implement the simple AI that will use a randomized
 * attack strategy when incorporated.
 */

import java.util.Random;

public class EasyAI {

    /**
     * random --> Random object that will be used to guess the coordinates and place ships
     * board --> the board that stores the IDs of all ships
     * vector --> the array responsible for moving vertically or horizontally in either direction
     * countHit --> array to count how many hits for each ship based on ID
     */
    private Random random;
    private int board[][];
    private int vector[][];
    private int countHit[];

    /**
     * This constructor will enable the initialization for the EasyAI class, and it
     * will initialize and set the appropriate values and objects
     */
    public EasyAI() {
        // initialize random object
        random = new Random();

        // set the bounds for the board, notice it is 11 since we use 1-based indexing
        board = new int[11][11];

        // set values for vector
        vector = new int[][] {
                {1, -1, 0, 0}, // vector for moving vertically (row vector)
                {0, 0, 1, -1} // vector for moving horizontally column vector)
        };

        // set values for countHit
        countHit = new int[6];
    }

    /**
     * This method will check if the current coordinate is valid
     * @param row
     * @param col
     * @return
     */
    public boolean isValid(int row, int col) {
        if (row < 1 || col < 1 || row > 10 || col > 10) return false; // make sure the coordinate is not out of bounds
        return board[row][col] == 0; // make sure no other ship is in the coordinate
    }

    /**
     * This method will place thw five ships in valid positions and store their IDs on the board
     */
    public void placeShips() {
        int ID = 1; // current ID of the ship
        // for loop to place all ships from destroyer (2 holes) to carrier (5 holes)
        for (int length : new int[]{2, 3, 3, 4, 5}) {
            while (true) {
                int rowPlace = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
                int colPlace = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
                int idx = random.nextInt(4); // get a random integer in the range [0, 3]
                boolean works = true;
                for (int i = 1, curRow = rowPlace, curCol = colPlace; i <= length; i++) {
                    if (!isValid(curRow, curCol)) {
                        works = false;
                        break;
                    }
                    curRow += vector[0][idx];
                    curCol += vector[1][idx];
                }
                if (!works) continue;
                for (int i = 1; i <= length; i++) {
                    board[rowPlace][colPlace] = ID;
                    rowPlace += vector[0][idx];
                    colPlace += vector[1][idx];
                }
                break;
            }
            ID++;
        }
    }

    /**
     * This method will be used in the game when the opponent guesses a coordinate, and will inform
     * them if they hit or missed
     * @param coordinate
     * @return
     */
    public HitOrMiss hitOrMiss(Coordinate coordinate) {
        if (board[coordinate.getX()][coordinate.getY()] == 0) {
            // If no ship is hit, inform that nothing is hit
            return new HitOrMiss(false, -1, -1);
        }
        int currentID = board[coordinate.getX()][coordinate.getY()];
        countHit[currentID]++;
        return new HitOrMiss(true, currentID, countHit[currentID]);
    }

    /**
     * This method will return which row and column to guess using Random object.
     * It will pass this onto the main game class in order to proceed with the game
     * @return
     */
    public Coordinate guessCoordinate() {
        int rowGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        int colGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        return new Coordinate(rowGuess, colGuess);
    }

    /**
     * This method will get the information on whether the guessed coordinates was a hit or miss.
     * However, since this is an Easy AI, it will do nothing about this since it has no strategy
     * based on if it's a hit or miss, so nothing will be implemented here.
     * @param hit
     */
    public void getInformation(boolean hit) {

    }
}
