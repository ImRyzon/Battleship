/**
 * @author Mark Wang, Daniel Guo, Nathanael You
 * 2023-1-21
 *
 * This class will act as the actual game that includes the gameboard GUI and all the
 * necessary logic to play the game
 *
 * Small note: There is another way to only use the appropriate AI depending on the difficulty instead of
 * just creating an object of both AI at the same time. However, one has to know interfaces and class abstraction.
 * Due to interfaces not being explicitly taught in the ICS4U curriculum and our semester, it is for this reason
 * why we decided to avoid this approach.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Battleship extends JFrame implements ActionListener {

    /**
     * easyAI --> an object of the easy version AI (see small note above)
     * expertAI --> an object for the expert version AI (see small note above)
     * vector --> vector array that will allow for movement based on direction
     * vectorIndex --> index of the vector to be used to determine direction
     * board --> the board for the player, it will store all the ship's locations along with their IDs
     * lengths --> lengths of ships based on their ID
     * countHit --> array to count how many hits for each ship based on ID
     * isPlaced --> boolean array to track if certain ships have been placed or not based on their ID
     */
    private EasyAI easyAI;
    private ExpertAI expertAI;
    private int vector[][];
    private int vectorIndex;
    private int board[][];
    private int lengths[];
    private int countHit[];
    private boolean isPlaced[];

    /**
     * this constructor will allow other classes to instantiate an object of this class, and it will
     * also implement the necessary logic for the game.
     * @param hard (whether the difficulty is hard or not, and will use the correct AI
     */
    public Battleship(boolean hard) {
        // initialize both AIs
        easyAI = new EasyAI();
        expertAI = new ExpertAI();

        // set values for vector
        vector = new int[][] {
                {-1, 0, 1, 0}, // vector for moving vertically (row vector)
                {0, 1, 0, -1} // vector for moving horizontally (column vector)
        };

        // set initial value for vectorIndex (facing north)
        vectorIndex = 0;

        // initialize taken
        board = new int[11][11];

        // initialize lengths
        lengths = new int[]{-1, 2, 3, 3, 4, 5};

        // initialize isPlaced
        isPlaced = new boolean[6];
    }

    /**
     * This method will check whether the current placement of a ship is valid or not
     * given the coordinates and the ship's ID
     * @param row
     * @param column
     * @param ID
     * @return
     */
    boolean isValidSpot(int row, int column, int ID) {
        // loop through all positions the ship will take and determine if valid or not
        for (int i = 1; i <= lengths[ID]; i++) {
            // if the position is out of bounds or already taken by another ship, return false
            if (row < 1 || column < 1 || row > 10 || column > 10 || board[row][column] != 0) {
                return false;
            }

            // check next position
            row += vector[0][vectorIndex];
            column += vector[1][vectorIndex];
        }

        // If non are invalid, return true
        return true;
    }

    /**
     * This method will place a ship on its position based on its ID and will mark the spot as placed
     * @param row
     * @param column
     * @param ID
     */
    void placeShip(int row, int column, int ID) {
        isPlaced[ID] = true; //  mark this ship as placed

        // loop though all the positions of the ship and mark them as taken
        for (int i = 1; i <= lengths[ID]; i++) {
            board[row][column] = ID;
            row += vector[0][vectorIndex];
            column += vector[1][vectorIndex];
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
