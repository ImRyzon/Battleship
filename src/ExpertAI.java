/**
 * @author Mark Wang
 * 2023-1-19
 *
 * This class will implement the expert AI and will implement the optimal strategy
 * to place ships as well as guessing coordinates
 */

import java.util.Random;

public class ExpertAI {

    /**
     * random --> Random object that will be used to place ships
     * currentGuess --> Stores the coordinate of the most recent guess
     * board --> the board that stores the IDs of all ships
     * vector --> the array responsible for moving vertically or horizontally in either direction
     * countHit --> array to count how many hits for each ship based on ID
     * shipLocation --> array to keep track of ship locations based on their ID. if it is confirmed that there
     *                  exists no ship in a location, mark it as 0. Default value is -1
     * destroyed --> array to check if the ship with each ID has been destroyed or not
     * initialHit --> the initial hit coordinate for every ship ID, default value is -1
     * isHorizontal --> tracks if each ship based on their ID is horizontal or vertical. Default is -1, meaning
     *                  that we initially don't know if it's horizontal or vertical
     */
    private Random random;
    private Coordinate currentGuess;
    private int board[][];
    private int vector[][];
    private int countHit[];
    private int shipLocation[][];
    private boolean destroyed[];
    Coordinate initialHit[];
    int isHorizontal[];

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

        // initialize destroyed
        destroyed = new boolean[6];

        // initialize alreadyGuessed
        shipLocation = new int[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 1; j++) {
                shipLocation[i][j] = -1;
            }
        }

        // initialize initialHit
        initialHit = new Coordinate[6];
        for (int i = 0; i < 6; i++) initialHit[i] = new Coordinate(-1, -1);

        // initialize isHorizontal
        isHorizontal = new int[6];
        for (int i = 0; i < 6; i++) isHorizontal[i] = -1;
    }

    /**
     * This method will check if the current coordinate is valid and in-bounds
     * @param row
     * @param col
     * @return
     */
    public boolean isValid(int row, int col) {
        if (row < 1 || col < 1 || row > 10 || col > 10) return false; // make sure the coordinate is not out of bounds
        return true;
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
            return new HitOrMiss(false, -1, false);
        }
        // If it is a hit, then return the ID of the ship hit and the total hits this ship induced
        int currentID = board[coordinate.getX()][coordinate.getY()];
        countHit[currentID]++; // update countHit
        board[coordinate.getX()][coordinate.getY()] = 0; // reset the current block to be 0
        return new HitOrMiss(true, currentID, destroyedShip(countHit[currentID], currentID));
    }

    /**
     * This method will return which row and column to guess using an optimal
     * strategy
     * @return
     */
    public Coordinate guessCoordinate() {
        for (int i = 1; i <= 5; i++) {
            // if the current ship is not destroyed and the initial coordinate is known, then target it
            if (!destroyed[i] && initialHit[i].getX() != -1 && initialHit[i].getY() != -1) {
                int x = initialHit[i].getX();
                int y = initialHit[i].getY();

                // if we don't know the configuration of the current ship, guess a direction
                if (isHorizontal[i] == -1) {
                    for (int index = 0; index < 4; index++) {
                        int adjacentX = x + vector[0][index];
                        int adjacentY = y + vector[1][index];

                        // If the coordinate is out of bounds, then guess another coordinate
                        if (!isValid(adjacentX, adjacentY)) {
                            continue;
                        }

                        // if the adjacent square is never guessed before, then we can guess it
                        if (shipLocation[adjacentX][adjacentY] == -1) {
                            currentGuess = new Coordinate(adjacentX, adjacentY);
                            return currentGuess;
                        }
                    }
                } else {
                    // store the appropriate indices of the vector knowing the direction
                    int indices[];
                    if (isHorizontal[i] == 1) indices = new int[]{1, 3};
                    else indices = new int[]{0, 2};

                    for (int index : indices) {
                        int adjacentX = x + vector[0][index];
                        int adjacentY = y + vector[1][index];

                        // if the adjacent square is out of bounds, then guess another direction
                        if (!isValid(adjacentX, adjacentY)) {
                            continue;
                        }

                        // Create an infinite loop that only ends when we want it to
                        while (true) {
                            // if the current square is not guessed, then guess it
                            if (shipLocation[adjacentX][adjacentY] == -1) {
                                currentGuess = new Coordinate(adjacentX, adjacentY);
                                return currentGuess;
                            } else if (shipLocation[adjacentX][adjacentY] == i) {
                                // if this coordinate is part of the same ship, then
                                // we don't give up just yet, and we try another coordinate
                                adjacentX += vector[0][index];
                                adjacentY += vector[1][index];
                            } else {
                                break; // otherwise, the coordinate is part of another ship or none at all, and we stop
                            }
                        }
                    }
                }
            }
        }
        // if no ship already has been guessed, then we must guess a coordinate, as we have no info
        // on any of the current ships on the board
        int rowGuess;
        int colGuess;

        do {
            rowGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
            colGuess = random.nextInt(10) + 1; // get a random integer in the range [1, 10]
        } while (shipLocation[rowGuess][colGuess] != -1); // keep guessing until we get a coordinate we never guessed before

        currentGuess = new Coordinate(rowGuess, colGuess);
        return currentGuess;
    }

    /**
     * This method will get the information on whether the guessed coordinates was a hit or miss.
     * It will use this information to guess the next optimal coordinate
     * @param info
     */
    public void getInformation(HitOrMiss info) {
        // If we missed, then mark the current location to have no ships
        if (!info.getHit()) {
            shipLocation[currentGuess.getX()][currentGuess.getY()] = 0;
        } else if (info.getDestroyedShip()) { // if the ship is destroyed, then mark it as destroyed
            destroyed[info.getID()] = true;
            shipLocation[currentGuess.getX()][currentGuess.getY()] = info.getID();
        } else {
            // if this ship has never been hit before, then mark the initial hit coordinate
            if (initialHit[info.getID()].getX() == -1 || initialHit[info.getID()].getY() == -1) {
                initialHit[info.getID()] = currentGuess;
            } else {
                // Otherwise, we can determine if the ship is horizontal or vertical
                if (currentGuess.getY() == initialHit[info.getID()].getY()) {
                    // if the y-coordinate is the same, then the ship is vertical
                    isHorizontal[info.getID()] = 0;
                } else {
                    // Otherwise, the ship is horizontal
                    isHorizontal[info.getID()] = 1;
                }
            }
            shipLocation[currentGuess.getX()][currentGuess.getY()] = info.getID(); // mark the current coordinate
        }
    }
}
