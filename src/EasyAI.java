/**
 * @author Mark Wang
 * 2023-1-17
 *
 * This class will implement the simple AI that will use a randomized
 * attack strategy when incorporated.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class EasyAI {

    /**
     * random --> Random object that will be used to guess the coordinates and place ships
     * board --> the board that stores the IDs of all ships
     * vector --> the array responsible for moving vertically or horizontally in either direction
     * countHit --> array to count how many hits for each ship based on ID
     * alreadyGuessed --> array to keep track of already guessed coordinates
     */
    private Random random;
    private int board[][];
    private int vector[][];
    private int countHit[];
    boolean alreadyGuessed[][];

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
                {0, 0, 1, -1} // vector for moving horizontally (column vector)
        };

        // set values for countHit
        countHit = new int[6];

        // initialize alreadyGuessed
        alreadyGuessed = new boolean[11][11];
    }

    /**
     * This method will check if the current coordinate is valid and not out-of-bounds
     * @param row
     * @param col
     * @return
     */
    public boolean isValid(int row, int col) {
        if (row < 1 || col < 1 || row > 10 || col > 10) return false; // make sure the coordinate is not out of bounds
        return true;
    }

    /**
     * This method will place the five ships in valid positions and store their IDs on the board
     */
    public void placeShips() {
        int ID = 1; // current ID of the ship
        // for loop to place all ships based on their length
        // Destroyer: 2 holes
        // Submarine: 3 holes
        // Cruiser: 3 holes
        // Battleship: 4 holes
        // Carrier: 5 holes
        for (int length : new int[]{2, 3, 3, 4, 5}) {
            while (true) {
                int rowPlace = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
                int colPlace = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
                int idx = random.nextInt(4); // get a random integer in the range [0, 3]
                boolean works = true; // boolean that tracks whether the current configuration works or not
                int curRow = rowPlace; // set the current row to the row set by Random
                int curCol = colPlace; // set the current column to the column set by Random
                // Create a for loop that checks if the current position of the ship is valid or not
                for (int i = 1; i <= length; i++) {
                    // If it is out of bounds or there is already a ship on the current square, it is invalid
                    if (!isValid(curRow, curCol) || board[curRow][curCol] != 0) {
                        works = false;
                        break;
                    }
                    // go to the next cell by moving the coordinates via the vector array
                    curRow += vector[0][idx];
                    curCol += vector[1][idx];
                }
                if (!works) continue; // If it is invalid, then try another coordinate
                // If everything is valid, then loop again and place the ID on the set squares
                for (int i = 1; i <= length; i++) {
                    board[rowPlace][colPlace] = ID;
                    rowPlace += vector[0][idx];
                    colPlace += vector[1][idx];
                }
                break;
            }
            ID++;
        }

        storeBoard(); // store the board
    }

    /**
     * This method will store the board to a file
     */
    public void storeBoard() {
        // try-amd-catch statement for exceptions
        try {
            // declare file and PrintWriter object
            File file = new File("AIBoard.txt");
            PrintWriter writer = new PrintWriter(file);

            // print the current board to the file
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    writer.print(board[i][j]);
                    if (j < 10) writer.print(" ");
                    else writer.println();
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will return if the current ship is destroyed given the ID of the ship and
     * the amount of hits the ship took
     * @param countHit
     * @param ID
     * @return
     */
    public boolean destroyedShip(int countHit, int ID) {
        if (ID == 1 && countHit == 2) return true;
        if (ID == 2 && countHit == 3) return true;
        if (ID == 3 && countHit == 3) return true;
        if (ID == 4 && countHit == 4) return true;
        if (ID == 5 && countHit == 5) return true;
        return false;
    }

    /**
     * This method will be used in the game when the opponent guesses a coordinate, and will inform
     * them if they hit or missed
     * @param coordinate
     * @return
     */
    public HitOrMiss hitOrMiss(Coordinate coordinate) {
        if (board[coordinate.getX()][coordinate.getY()] == 0) {
            // If no ship is hit, inform that nothing is hit and set default values of false and -1
            return new HitOrMiss(false,-1, false);
        }
        // If it is a hit, then return the ID of the ship hit and the total hits this ship induced
        int currentID = board[coordinate.getX()][coordinate.getY()];
        countHit[currentID]++; // update countHit
        board[coordinate.getX()][coordinate.getY()] = 0; // reset the current block to be 0
        return new HitOrMiss(true, currentID, destroyedShip(countHit[currentID], currentID));
    }

    /**
     * This method will return which row and column to guess using Random object.
     * It will pass this onto the main game class in order to proceed with the game
     * @return
     */
    public Coordinate guessCoordinate() {
        int rowGuess;
        int colGuess;

        do {
            rowGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
            colGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        } while (alreadyGuessed[rowGuess][colGuess]); // keep guessing until we hit a coordinate we did not guess before

        alreadyGuessed[rowGuess][colGuess] = true;
        return new Coordinate(rowGuess, colGuess);
    }

    /**
     * This method will get the information on whether the guessed coordinates was a hit or miss.
     * It will use this information to guess the next optimal coordinate
     * @param info
     */
    public void getInformation(HitOrMiss info) {
        // do nothing
    }
}
