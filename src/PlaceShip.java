/**
 * @author Mark Wang, Daniel Guo, Nathanael You
 * 2023-1-21
 *
 * This class will act as the actual game that includes the gameboard GUI and all the
 * necessary logic to play the game by allowing the user to place ships
 * 
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlaceShip extends JFrame implements ActionListener {

    /**
     * vector --> vector array that will allow for movement based on direction
     * vectorIndex --> index of the vector to be used to determine direction
     * board --> the board for the player, it will store all the ship's locations along with their IDs
     * lengths --> lengths of ships based on their ID
     * isPlaced --> boolean array to track if certain ships have been placed or not based on their ID
     * placeGrid --> grid of buttons where user can select coordinate and place ships
     * shipNames --> list of ship names
     * shipPlaced --> array to store all ships that are placed
     * shipSelect --> drop-down box the select ships to place
     * shipDelete --> drop-down box to select ships to delete
     * placeButton --> button used to placing ships
     * deleteButton --> button used to delete ships
     * rotateButton --> button to rotate ships
     * readyButton --> button to proceed with the game
     * actionPanel --> panel to add all action buttons (Play, Rotate, Ready, Direction, etc.)
     * buttonPanel --> panel consisting of the button grid
     * currentDirection --> displays current direction
     * placeLabel --> label signalling which drop-down box is for placing
     * deleteLabel --> label signifying which drop-down box is for deleting
     * currentCoordinate --> stores current coordinate
     * locations --> stores locations of all ships that are placed based on ID
     */
    private int vector[][];
    private int vectorIndex;
    private int board[][];
    private int lengths[];
    private boolean isPlaced[];
    private JButton[][] placeGrid = new JButton[11][11];
    private String[] shipNames = {"Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};
    private ArrayList<String> shipPlaced = new ArrayList<String>();
    private JComboBox shipSelect;
    private JComboBox shipDelete;
    private JButton placeButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton rotateButton = new JButton();
    private JButton readyButton = new JButton();
    private JPanel actionPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel currentDirection = new JLabel();
    private JLabel placeLabel = new JLabel();
    private JLabel deleteLabel = new JLabel();
    private Coordinate currentCoordinate;
    private ShipLocation locations[];
    /**
     * this constructor will allow other classes to instantiate an object of this class, and it will
     * also implement the necessary logic for the game.
     * @param hard (whether the difficulty is hard or not, and will use the correct AI
     */
    public Battleship(boolean hard) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setTitle("Place Your Ships");
        this.setResizable(false);

        actionPanel.setLayout(null);
        actionPanel.setVisible(true);

        actionPanel.setBounds(700, 10, 1200, 700);

        buttonPanel.setLayout(new GridLayout(10, 10, 1, 1));
        buttonPanel.setBounds(10, 10, 650, 650);
        buttonPanel.setBackground(new Color(25, 255, 0));

        for(int y = 1; y <= 10; y++) {
            for(int x = 1; x <= 10; x++) {
                placeGrid[y][x] = new JButton();
                placeGrid[y][x].setFocusable(false);
                placeGrid[y][x].addActionListener(this);
                placeGrid[y][x].setBackground(Color.BLACK);
                buttonPanel.add(placeGrid[y][x]);
            }
        }

        shipSelect = new JComboBox(shipNames);
        shipSelect.setBounds(135, 0, 255, 75);
        shipSelect.addActionListener(this);
        actionPanel.add(shipSelect);

        shipDelete = new JComboBox(shipPlaced.toArray());
        shipDelete.setBounds(135, 100, 255, 75);
        shipDelete.addActionListener(this);
        actionPanel.add(shipDelete);

        rotateButton.setText("Rotate Ship");
        rotateButton.setBounds(135, 200, 255, 75);
        rotateButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        rotateButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        rotateButton.setBackground(Color.WHITE);
        rotateButton.addActionListener(this);
        actionPanel.add(rotateButton);

        placeButton.setText("Place Ship");
        placeButton.setBounds(135, 300, 255, 75);
        placeButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        placeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        placeButton.setBackground(Color.WHITE);
        placeButton.addActionListener(this);
        actionPanel.add(placeButton);

        deleteButton.setText("Delete Ship");
        deleteButton.setBounds(135, 400, 255, 75);
        deleteButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.addActionListener(this);
        actionPanel.add(deleteButton);

        readyButton.setText("Ready");
        readyButton.setBounds(135, 500, 255, 75);
        readyButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        readyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        readyButton.setBackground(Color.WHITE);
        readyButton.addActionListener(this);
        actionPanel.add(readyButton);

        placeLabel.setBounds(5, 20, 75, 30);
        placeLabel.setText("Place:");
        placeLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        actionPanel.add(placeLabel);

        deleteLabel.setBounds(5, 120, 100, 30);
        deleteLabel.setText("Remove:");
        deleteLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        actionPanel.add(deleteLabel);

        currentDirection.setBounds(125, 575, 300, 75);
        currentDirection.setText("Current Direction: Up");
        currentDirection.setFont(new Font("Monospaced", Font.BOLD, 20));
        actionPanel.add(currentDirection);

        this.add(buttonPanel);
        this.add(actionPanel);
        this.setVisible(true);

        // set values for vector
        vector = new int[][] {
                {-1, 0, 1, 0}, // vector for moving vertically (row vector)
                {0, 1, 0, -1} // vector for moving horizontally (column vector)
        };

        // set initial value for vectorIndex (facing north)
        vectorIndex = 0;

        // initialize board
        board = new int[11][11];

        // initialize lengths
        lengths = new int[]{-1, 2, 3, 3, 4, 5};

        // initialize isPlaced
        isPlaced = new boolean[6];

        // initialize default value for currentCoordinate
        currentCoordinate = new Coordinate(-1, -1);

        // initialize locations
        locations = new ShipLocation[6];
        for (int i = 0; i <= 5; i++) {
            locations[i] = new ShipLocation(new Coordinate(-1, -1), -1);
        }
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
        locations[ID] = new ShipLocation(currentCoordinate, vectorIndex); // mark location of ship

        // loop though all the positions of the ship and mark them as taken and color them green on the board
        for (int i = 1; i <= lengths[ID]; i++) {
            board[row][column] = ID;
            placeGrid[row][column].setBackground(new Color(144, 238, 144));
            row += vector[0][vectorIndex];
            column += vector[1][vectorIndex];
        }
    }

    /**
     * This method will delete a ship from the board based on its ID
     * @param ID
     */
    void deleteShip(int ID) {
        isPlaced[ID] = false; // mark the ship as unplaced
        int row = locations[ID].getRow();
        int column = locations[ID].getColumn();

        // loop through the position and mark them as unvisited and reset the color
        for (int i = 1; i <= lengths[ID]; i++) {
            board[row][column] = 0;
            placeGrid[row][column].setBackground(Color.BLACK);
            row += vector[0][locations[ID].getDirection()];
            column += vector[1][locations[ID].getDirection()];
        }

        // reset the location to default values
        locations[ID] = new ShipLocation(new Coordinate(-1, -1), -1);
    }

    /**
     * This method will search the shipNames array and return the index of a particular ship. If it is not
     * found, then it will return -1
     * @param name
     * @return
     */
    public int findShip(String name) {
        for (int i = 0; i < shipNames.length; i++) {
            if (shipNames[i].equals(name)) {
                return i + 1; // return found index +1 for 1-based indexing
            }
        }
        return -1; // not found
    }

    /**
     * This method will switch the direction by changing the vector index and changing the
     * currentDirection button
     */
    public void switchDirection() {
        vectorIndex = (vectorIndex + 1) % 4; // change index and mod 4
        // change display direction
        if (vectorIndex == 0) currentDirection.setText("Current Direction: Up");
        else if (vectorIndex == 1) currentDirection.setText("Current Direction: Right");
        else if (vectorIndex == 2) currentDirection.setText("Current Direction: Down");
        else currentDirection.setText("Current Direction: Left");
    }

    /**
     * The action performed method will do the appropriate actions when a button or combo box
     * has been selected
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // if any buttons on the grid is pressed, the update the current targeted coordinate
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (e.getSource() == placeGrid[i][j]) {
                    int x = currentCoordinate.getX();
                    int y = currentCoordinate.getY();

                    // if the previous square isn't occupied, reset the color to 0
                    if (x != -1 && y != -1 && board[x][y] == 0) {
                        placeGrid[x][y].setBackground(Color.BLACK);
                    } else if (x != -1 && y != -1) { // Otherwise, reset it to green
                        placeGrid[x][y].setBackground(new Color(144, 238, 144));
                    }

                    currentCoordinate = new Coordinate(i, j);
                    placeGrid[currentCoordinate.getX()][currentCoordinate.getY()].setBackground(new Color(255, 255, 255));
                    return; // break out of the method
                }
            }
        }

        if (e.getSource() == placeButton) {
            // if no ships are selected, then get the user to redo
            if (shipSelect.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No Ship Selected", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else if (currentCoordinate.getX() == -1 || currentCoordinate.getY() == -1) {
                JOptionPane.showMessageDialog(null, "No Coordinate Selected", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String ship = shipSelect.getSelectedItem().toString();
            int index = findShip(ship);

            // If, for some reason, the ship is not in the list, let the user redo
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "Invalid Ship", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // if the ship is in a valid position, then place it
            if (isValidSpot(currentCoordinate.getX(), currentCoordinate.getY(), index)) {
                placeShip(currentCoordinate.getX(), currentCoordinate.getY(), index);
                shipSelect.removeItem(ship);
                shipDelete.addItem(ship);
            } else { // otherwise, inform the user that they must retry
                JOptionPane.showMessageDialog(null, "Invalid Position", "Invalid", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        else if (e.getSource() == deleteButton) {
            // if no ships are selected, then get the user to redo
            if (shipDelete.getItemCount() == 0) {
                JOptionPane.showMessageDialog(null, "No Ship Selected", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String ship = shipDelete.getSelectedItem().toString();
            int index = findShip(ship);

            // If, for some reason, the ship is not in the list, let the user redo
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "Invalid Ship", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            deleteShip(index);
            shipDelete.removeItem(ship);
            shipSelect.addItem(ship);
        }

        else if (e.getSource() == rotateButton) {
            switchDirection(); // switch/rotate the direction if they want to rotate
        }

        else if (e.getSource() == readyButton) {
            // before going onto the game, first make sure everything is placed
            for (int i = 1; i <= 5; i++) {
                if (!isPlaced[i]) {
                    JOptionPane.showMessageDialog(null, "Cannot Proceed, Not All Ships Are Placed", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            // if all ships are placed, proceed to the game
            this.dispose();
            GameBoard board = new GameBoard();
        }
    }

    /**
     * Create a subclass called ShipLocation that will allow for easier accessing in terms of the
     * current placed ship's exact blocks. This is especially useful when deleting ships, as we can
     * easily delete all the blocks they are in
     */
    public class ShipLocation {
        /**
         * rootCoordinate --> stores the initial block the ship is on
         * direction --> stores the direction the ship is facing
         */
        Coordinate rootCoordinate;
        int direction;

        /**
         * this constructor will allow for the instantiation of an object for this class
         * @param coordinate
         * @param direction
         */
        public ShipLocation(Coordinate coordinate, int direction) {
            // set the current object's values
            this.rootCoordinate = coordinate;
            this.direction = direction;
        }

        /**
         * Getter for row
         * @return
         */
        public int getRow() {
            return this.rootCoordinate.getX();
        }

        /**
         * Getter for column
         * @return
         */
        public int getColumn() {
            return this.rootCoordinate.getY();
        }

        /**
         * Getter for direction
         * @return
         */
        public int getDirection() {
            return this.direction;
        }
    }
}
