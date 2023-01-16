/**
 * This class will implement the expert AI and will implement the optimal strategy
 * to place ships as well as guessing coordinates
 */

import java.util.ArrayList;
import java.util.Random;

public class ExpertAI {

    /**
     * random --> Random object that will be used to place ships
     * board --> the board that stores the IDs of all ships
     * vector --> the array responsible for moving vertically or horizontally in either direction
     * countHit --> array to count how many hits for each ship based on ID
     * trackCount --> track the number of times each enemy ship is hit
     * alreadyGuessed --> an array that stores all blocks that are already guessed to prevent from guessing again
     * rowGuess --> stores the value of the current row guess
     * colGuess --> stores the value of the current column guess
     * vectorIndex --> stores the direction to guess when currently on a ship
     * trackHits --> track hits in order to guess the optimal coordinates
     * trackID --> tracks the ID of the ship corresponding to the trackHits array when a ship is hit
     */
    private Random random;
    private int board[][];
    private int vector[][];
    private int countHit[];
    private int trackCount[];
    private boolean alreadyGuessed[][];
    private int rowGuess;
    private int colGuess;
    private int vectorIndex;
    ArrayList<Coordinate> trackHits;
    ArrayList<Integer> trackID;

    /**
     * This constructor will enable the initialization for the EasyAI class, and it
     * will initialize and set the appropriate values and objects
     */
    public ExpertAI() {
        // initialize random object
        random = new Random();

        // set the bounds for the board, notice it is 11 since we use 1-based indexing
        board = new int[11][11];

        // set values for vector
        vector = new int[][] {
                {-1, 0, 1, 0}, // vector for moving vertically (row vector)
                {0, 1, 0, -1} // vector for moving horizontally (column vector)
        };

        // initialize for countHit
        countHit = new int[6];

        // initialize trackCount
        trackCount = new int[6];

        // initialize alreadyGuessed
        alreadyGuessed = new boolean[11][11];

        // initialize rowGuess, colGuess, and vectorIndex
        rowGuess = 0;
        colGuess = 0;
        vectorIndex = -1;

        // intiialize trackHits
        trackHits = new ArrayList<Coordinate>();
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
     * This method will check if there are any surrounding ships given a coordinate.
     * If there is, then it is not optimal to place a ship here as it is easier for the user
     * to hit two ships in consecutive guesses
     * @param row
     * @param col
     * @return
     */
    public boolean hasSurroundingShip(int row, int col) {
        if (board[row][col] != 0) return true;
        for (int i = 0; i < 4; i++) {
            int adjacentRow = row + vector[0][i];
            int adjacentColumn = col + vector[1][i];
            if (isValid(adjacentRow, adjacentColumn) && board[adjacentRow][adjacentColumn] != 0) return true;
        }
        return false;
    }

    /**
     * This method will place thw five ships in valid positions and store their IDs on the board
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
                    // If it is out of bounds or there is any ships surrounding, it is invalid
                    if (!isValid(curRow, curCol) || hasSurroundingShip(curRow, curCol)) {
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
            return new HitOrMiss(false, -1, -1, false);
        }
        // If it is a hit, then return the ID of the ship hit and the total hits this ship induced
        int currentID = board[coordinate.getX()][coordinate.getY()];
        countHit[currentID]++; // update countHit
        board[coordinate.getX()][coordinate.getY()] = 0; // reset the current block to be 0
        return new HitOrMiss(true, currentID, countHit[currentID], destroyedShip(countHit[currentID], currentID));
    }

    /**
     * This method will return which row and column to guess using an optimal
     * strategy
     * @return
     */
    public Coordinate guessCoordinate() {
        // If we currently don't have info on any ships, just make a random guess not guessed before
        if (trackHits.isEmpty()) {
            do {
                rowGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
                colGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
            } while (alreadyGuessed[rowGuess][colGuess]);
        } else { // Otherwise, make the optimal guess
            if (vectorIndex == -1) { // If it's the first time hitting a ship, guess a direction
                vectorIndex = random.nextInt(4);
                rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
            } else { // Otherwise, follow the current direction or switch directions if needed
                rowGuess += vector[0][vectorIndex];
                colGuess += vector[1][vectorIndex];
                if (alreadyGuessed[rowGuess][colGuess]) {
                    if (trackCount[trackID.get(0)] > 1) {
                        vectorIndex = changeDirection(vectorIndex);
                        rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                        colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
                    } else {
                        do {
                            vectorIndex++;
                            if (vectorIndex == 4) vectorIndex = 0;
                            rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                            colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
                        } while (alreadyGuessed[rowGuess][colGuess]);
                    }
                }
            }
        }
        alreadyGuessed[rowGuess][colGuess] = true;
        return new Coordinate(rowGuess, colGuess);
    }

    /**
     * This method will switch the direction of the vector.
     *
     * For ex. if currently going up, then go down
     *         if currently going right, then go left
     *         etc.
     * @param currentIndex
     */
    public int changeDirection(int currentIndex) {
        if (currentIndex == 0) return 2;
        else if (currentIndex == 1) return 3;
        else if (currentIndex == 2) return 0;
        return 1;
    }

    /**
     * This method will get the information on whether the guessed coordinates was a hit or miss.
     * It will use this information to guess the next optimal coordinate
     * @param hit
     * @param ID
     * @param destroyed
     */
    public void getInformation(boolean hit, int ID, boolean destroyed) {
        if (!hit) {
            if (vectorIndex != -1) {
                if (trackCount[ID] > 1) {
                    // If we are currently on the ship, switch direction to guarantee to hit the ship again
                    vectorIndex = changeDirection(vectorIndex);
                    rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                    colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
                } else {
                    // Otherwise, we are on a ship but chose the wrong direction to go next, so switch directions
                    ++vectorIndex;
                    if (vectorIndex == 4) vectorIndex = 0;
                    rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                    colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
                }
            }
        } else {
            ++trackCount[ID]; // add 1 hit to the current ship ID
            // If the ship is destroyed, remove the ship from the ArrayLists and reset the vectorIndex
            if (destroyed) {
                vectorIndex = -1;
                trackHits.remove(0);
                trackID.remove(0);
            } else {
                // If we either hit a new ship or stumble upon a new ship while currently tracked on a ship.
                // Then we add this initial coordinate to our trackHits and trackID arraylist
                if (trackHits.isEmpty()) {
                    trackHits.add(new Coordinate(rowGuess, colGuess));
                    trackID.add(ID);
                    vectorIndex = -1;
                } else if (trackID.get(0) != ID) {
                    trackHits.add(new Coordinate(rowGuess, colGuess));
                    trackID.add(ID);
                    // If the ID is different from our target ship, we still need to switch directions
                    do {
                        ++vectorIndex;
                        if (vectorIndex == 4) vectorIndex = 0;
                        rowGuess = trackHits.get(0).getX() + vector[0][vectorIndex];
                        colGuess = trackHits.get(0).getY() + vector[1][vectorIndex];
                    } while (alreadyGuessed[rowGuess][colGuess]);
                }
            }
        }
    }

    /**
     * Getter for the board, note that it is not necessary to make a setter for this
     * @return
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Getter for the countHit array, note that it is not necessary to make a setter for this
     * @return
     */
    public int[] getCountHit() {
        return this.countHit;
    }
}
