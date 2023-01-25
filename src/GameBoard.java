import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard extends JFrame implements ActionListener {

    /**
     * playerBoard --> the actual board of the player
     * userBoard --> file containing the board of the user
     * readBoard --> PrintWriter to read board from file
     * alreadyGuessed --> track user's guesses to avoid duplicates
     * easyAI --> easy version of AI (read small note in class documentation)
     * expertAI --> hard version of AI (read small note in class documentation)
     * attackGrid --> 2d array that will act as the grid when attacking
     * attackPanel --> JPanel used for attacking and will implement the attack grid
     * defenseGrid --> grid that displays your own ships (defense grid)
     * defensePanel --> panel that displays the defense grid
     * gameIcon --> the icon for this page
     * countHits --> counts the hits for user's ships
     * friendlyShipsDestroyed --> counts how many of own ships are destroyed
     * enemyShipsDestroyed --> counts how many of enemy ships are destroyed
     * friendlyLabel --> label that displays how many friendly ships have been destroyed
     * enemyLabel --> label that displays how many enemy ships have been destroyed
     * yourHit --> JLabel for your hit
     * aiHit --> JLabel indicating AI hit
     * explosionImage --> image for explosions when ships are hit
     * cloudImage --> image for clouds (default image when square isn't guessed)
     * oceanImage --> image for oceans when the square contains no ships
     * infoPanel --> panel to store information and statistics
     * counterLabel --> label that keeps track of time
     * timer --> actual timer with time
     * second --> displays seconds
     * minute --> displays minutes
     * ddSecond --> decimal format for second
     * ddMinute --> decimal format for minute
     * dFormat --> decimal format for formatting
     * isHard --> determines if difficult is hard or not
     * shipNames --> array to keep track of ship names based on ID
     * statistics --> statistics file
     * userID --> userID file
     * firstTurn --> stores whether it is the first turn or not to do a coin flip who goes first
     * aiFirst --> stores whether the AI went first or not
     */
    private int playerBoard[][] = new int[11][11];
    File userBoard = new File("UserBoard.txt");
    Scanner readBoard;
    private boolean alreadyGuessed[][] = new boolean[11][11];
    EasyAI easyAI = new EasyAI();
    ExpertAI expertAI = new ExpertAI();
    private JButton[][] attackGrid = new JButton[11][11];
    private JPanel attackPanel = new JPanel();
    private JButton[][] defenseGrid = new JButton[11][11];
    private JPanel defensePanel = new JPanel();
    private ImageIcon gameIcon = new ImageIcon("GameIcon.png");
    private int countHits[];
    private int friendlyShipsDestroyed = 0;
    private int enemyShipsDestroyed = 0;
    private JLabel gameBackground = new JLabel();
    private JLabel friendlyLabel = new JLabel();
    private JLabel enemyLabel = new JLabel();
    private JLabel yourHit = new JLabel();
    private JLabel aiHit = new JLabel();
    private ImageIcon backgroundImage = new ImageIcon("GameboardBackground.png");
    private ImageIcon explosionImage = new ImageIcon("explosion.png");
    private ImageIcon cloudImage = new ImageIcon("clouds.png");
    private ImageIcon oceanImage = new ImageIcon("ocean.png");
    private JPanel infoPanel = new JPanel();
    private JLabel counterLabel = new JLabel();
    private Timer timer;
    private int second;
    private int minute;
    private String ddSecond;
    private String ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");
    private boolean isHard;
    private String shipNames[] = new String[]{null, "Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};
    private File statistics = new File("Statistics.txt");
    private File userID = new File("UserID.txt");
    private boolean firstTurn = true;
    private boolean aiTurn;

    /**
     * This constructor will allow for instantiation of this class while also implementing the necessary
     * logic to make the game board function whe playing the game
     */
    public GameBoard(boolean hard) throws Exception {
        // set isHard
        this.isHard = hard;

        // place AI's ships
        if (isHard) expertAI.placeShips();
        else easyAI.placeShips();

        // get the user's board
        getBoard();

        // initialize countHits
        countHits = new int[6];

        //Customize Background
        gameBackground.setBounds(0, 0, 1170, 750);
        gameBackground.setIcon(backgroundImage);

        // customize attackPanel
        attackPanel.setLayout(new GridLayout(11, 11, 0, 0));
        attackPanel.setBounds(10, 10, 650, 650);
        attackPanel.setBackground(Color.BLUE);

        // customise infoPanel
        infoPanel.setBounds(700, 470, 450, 225);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // create the attack grid and customize it
        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                // customize current button
                attackGrid[y][x] = new JButton();
                attackGrid[y][x].setOpaque(false);
                attackGrid[y][x].setFocusable(false);
                attackGrid[y][x].addActionListener(this);
                attackGrid[y][x].setBackground(Color.BLACK);
                attackGrid[y][x].setIcon(cloudImage);
                attackPanel.add(attackGrid[y][x]);

                // set numbers and characters as the coordinates
                if(x == 0){
                    attackGrid[y][x].setEnabled(false);
                    if(y != 0){
                        attackGrid[y][x].setText(String.valueOf(y));
                        attackGrid[y][x].setOpaque(true);
                        attackGrid[y][x].setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                        attackGrid[y][x].setIcon(null);
                    }
                }

                if(y == 0){
                    attackGrid[y][x].setEnabled(false);
                    if(x != 0){
                        attackGrid[y][x].setText(String.valueOf((char)(x + 'A' - 1)));
                        attackGrid[y][x].setOpaque(true);
                        attackGrid[y][x].setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                        attackGrid[y][x].setIcon(null);
                    }
                }
            }
        }

        // customize top left cell for attackGrid
        attackGrid[0][0].setOpaque(true);
        attackGrid[0][0].setIcon(null);
        attackGrid[0][0].setBackground(Color.BLACK);

        // customize defensePanel
        defensePanel.setLayout(new GridLayout(11, 11));
        defensePanel.setBounds(700, 10, 450, 450);
        defensePanel.setBackground(new Color(52, 152, 235));

        // create defense grid and customize it
        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                // customize current button
                defenseGrid[y][x] = new JButton();
                defenseGrid[y][x].setFocusable(false);
                defenseGrid[y][x].addActionListener(this);
                defenseGrid[y][x].setBackground(Color.BLACK);
                if (playerBoard[y][x] == 0) defenseGrid[y][x].setIcon(cloudImage);
                else defenseGrid[y][x].setBackground(new Color(25, 255, 0));
                defensePanel.add(defenseGrid[y][x]);

                // set numbers and letters for the coordinates
                if (x == 0){
                    defenseGrid[y][x].setEnabled(false);
                    if(y != 0){
                        defenseGrid[y][x].setText(String.valueOf(y));
                        defenseGrid[y][x].setOpaque(true);
                        defenseGrid[y][x].setFont(new Font("Comic Sans MS", Font.BOLD, 5));
                        defenseGrid[y][x].setIcon(null);
                    }
                }

                if (y == 0){
                    defenseGrid[y][x].setEnabled(false);
                    if(x != 0) {
                        defenseGrid[y][x].setText(String.valueOf((char)(x + 'A' - 1)));
                        defenseGrid[y][x].setOpaque(true);
                        defenseGrid[y][x].setFont(new Font("Comic Sans MS", Font.BOLD, 5));
                        defenseGrid[y][x].setIcon(null);
                    }
                }
            }
        }

        // customize top left cell for defenseGrid
        defenseGrid[0][0].setOpaque(true);
        defenseGrid[0][0].setIcon(null);
        defenseGrid[0][0].setBackground(Color.BLACK);

        // customize the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1170, 750);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setTitle("Game Board");
        this.setResizable(false);

        // customize friendlyLabel
        friendlyLabel.setText("Friendly Ships Destroyed: " + friendlyShipsDestroyed);
        friendlyLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        infoPanel.add(friendlyLabel);

        // customize enemyLabel
        enemyLabel.setText("Enemy Ships Destroyed: " + enemyShipsDestroyed);
        enemyLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        infoPanel.add(enemyLabel);

        // customize yourHit
        yourHit.setText("Your latest hit: " + "Placeholder");
        yourHit.setFont(new Font("Monospaced", Font.BOLD, 25));
        infoPanel.add(yourHit);

        // customize aiHIT
        aiHit.setText("AI's latest hit: " + "Placeholder");
        aiHit.setFont(new Font("Monospaced", Font.BOLD, 25));
        infoPanel.add(aiHit);

        // customize time
        second = 0;
        minute = 0;
        counterLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        normalTimer();
        timer.start();
        infoPanel.add(counterLabel);

        // add the panels and gameIcon to the frame
        this.add(attackPanel);
        this.add(defensePanel);
        this.add(infoPanel);
        this.add(gameBackground);
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true);

        coinFlip(); // do a coin flip to see who goes first
        if (aiTurn) aiTurn();
    }

    /**
     * This method will raed the board from the file and store it inside the user's board
     */
    public void getBoard() throws Exception {
        // declare scanner
        readBoard = new Scanner(userBoard);

        // read file line-by-line
        for (int i = 1; i <= 10; i++) {
            String currentLine[] = readBoard.nextLine().split(" ");
            for (int j = 1; j <= 10; j++) {
                playerBoard[i][j] = Integer.parseInt(currentLine[j-1]);
            }
        }

        // close scanner
        readBoard.close();
    }

    /**
     * This method will keep track of time elapsed
     */
    public void normalTimer() {
        // create action listener using lambda function
        timer = new Timer(1000, e -> {

            second++;
            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);

            counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);

            if(second == 60) {
                second = 0;
                minute++;
                counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);
            }
        });
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
        if (playerBoard[coordinate.getX()][coordinate.getY()] == 0) {
            // If no ship is hit, inform that nothing is hit and set default values of false and -1
            return new HitOrMiss(false, -1, false);
        }
        // If it is a hit, then return the ID of the ship hit and the total hits this ship induced
        int currentID = playerBoard[coordinate.getX()][coordinate.getY()];
        countHits[currentID]++; // update countHit
        playerBoard[coordinate.getX()][coordinate.getY()] = 0; // reset the current block to be 0
        return new HitOrMiss(true, currentID, destroyedShip(countHits[currentID], currentID));
    }

    /**
     * This method will check if there is a winner and redirect user to menu if there is
     * @return
     */
    public boolean checkWinner() {
        if (enemyShipsDestroyed == 5) {
            int result = JOptionPane.showConfirmDialog(null,
                    "You Win!", "Congratulations", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

            try {
                updateStatistics(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (result == JOptionPane.OK_OPTION) {
                // dispose frame and redirect to menu
                this.dispose();
                Menu menu = new Menu();
            }
            return true;
        } else if (friendlyShipsDestroyed == 5) {
            int result = JOptionPane.showConfirmDialog(null,
                    "AI Wins", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

            try {
                updateStatistics(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (result == JOptionPane.OK_OPTION) {
                // dispose frame and redirect to menu
                this.dispose();
                Menu menu = new Menu();
            }
            return true;
        }
        return false;
    }

    /**
     * This method updates labels
     */
    public void updateLabels() {
        friendlyLabel.setText("Friendly Ships Destroyed: " + friendlyShipsDestroyed);
        enemyLabel.setText("Enemy Ships Destroyed: " + enemyShipsDestroyed);
    }

    /**
     * This method will update statistics based on type
     *
     * 1: Wins
     * 2: Losses
     * 3: Ship sunk
     * 4: Hit
     * 5: Miss
     *
     * @param type
     */
    public void updateStatistics(int type) throws Exception {
        // Get all lines of the file, store it into an ArrayList, and use scanner to get index of user ID
        ArrayList<String> statContent = new ArrayList<>(Files.readAllLines(statistics.toPath(), Charset.defaultCharset()));
        Scanner getUserID = new Scanner(userID);

        // Multiple index by 6 since each set of statistics take up 5 lines
        int index = Integer.parseInt(getUserID.nextLine()) * 6;
        getUserID.close();

        // Update the value to +1
        if (type == 1) {
            statContent.set(index, String.valueOf(Integer.parseInt(statContent.get(index)) + 1));
        } else if (type == 2) {
            statContent.set(index + 1, String.valueOf(Integer.parseInt(statContent.get(index + 1)) + 1));
        } else if (type == 3) {
            statContent.set(index + 2, String.valueOf(Integer.parseInt(statContent.get(index + 2)) + 1));
        } else if (type == 4) {
            statContent.set(index + 3, String.valueOf(Integer.parseInt(statContent.get(index + 3)) + 1));
        } else {
            statContent.set(index + 4, String.valueOf(Integer.parseInt(statContent.get(index + 4)) + 1));
        }

        // Re-write to the file using Files.write() method
        Files.write(statistics.toPath(), statContent, Charset.defaultCharset());
    }

    /**
     * This method will do a random coin flip to see who goes first
     */
    public void coinFlip() {
        int rand = ThreadLocalRandom.current().nextInt(0, 2);
        if (rand == 0) aiTurn = false;
        else aiTurn = true;
        firstTurn = false;
    }

    /**
     * This method will simular the player's turn
     * @param i
     * @param j
     */
    public void playerTurn(int i, int j) {

        String displayCoordinate = (char)(j + 'A' - 1) + " - " + i;
        /*
                    User's turn
                     */
        HitOrMiss currentGuess;

        // get info from AI
        if (isHard) currentGuess = expertAI.hitOrMiss(new Coordinate(i, j));
        else currentGuess = easyAI.hitOrMiss(new Coordinate(i, j));

        // show appropriate message and update statistics
        if (!currentGuess.getHit()) {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Miss", "Your Turn", JOptionPane.INFORMATION_MESSAGE);
            attackGrid[i][j].setIcon(oceanImage);
            try {
                updateStatistics(5);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (!currentGuess.getDestroyedShip()) {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Hit " + shipNames[currentGuess.getID()], "Your Turn", JOptionPane.INFORMATION_MESSAGE);
            attackGrid[i][j].setIcon(explosionImage);
            yourHit.setText("Your latest hit: " + displayCoordinate); // update yourHit
            try {
                updateStatistics(4);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Destroyed " + shipNames[currentGuess.getID()], "Your Turn", JOptionPane.INFORMATION_MESSAGE);
            attackGrid[i][j].setIcon(explosionImage);
            yourHit.setText("Your latest hit: " + displayCoordinate); // update yourHit
            ++enemyShipsDestroyed;
            try {
                updateStatistics(4);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                updateStatistics(3);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // check for winner
        boolean checkWinner = checkWinner();
        if (checkWinner) return;

        updateLabels(); // update labels
    }

    /**
     * This method will simulate AI's turn
     */
    public void aiTurn() {
        Coordinate aiGuess;

        // get info from AI
        if (isHard) aiGuess = expertAI.guessCoordinate();
        else aiGuess = easyAI.guessCoordinate();

        // update coordinate
        String displayCoordinate = (char)(aiGuess.getY() + 'A' - 1) + " - " + aiGuess.getX();

        // give AI information
        HitOrMiss check = hitOrMiss(aiGuess);
        if (isHard) expertAI.getInformation(check);
        else easyAI.getInformation(check);

        // show appropriate information regarding the guess
        if (!check.getHit()) {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Miss", "AI Turn", JOptionPane.INFORMATION_MESSAGE);
            defenseGrid[aiGuess.getX()][aiGuess.getY()].setIcon(oceanImage);
        } else if (!check.getDestroyedShip()) {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Hit " + shipNames[check.getID()], "AI Turn", JOptionPane.INFORMATION_MESSAGE);
            defenseGrid[aiGuess.getX()][aiGuess.getY()].setIcon(explosionImage);
            aiHit.setText("AI's latest hit: " + displayCoordinate); // update aiHit
        } else {
            JOptionPane.showMessageDialog(null,
                    displayCoordinate + ": Destroyed " + shipNames[check.getID()], "AI Turn", JOptionPane.INFORMATION_MESSAGE);
            defenseGrid[aiGuess.getX()][aiGuess.getY()].setIcon(explosionImage);
            aiHit.setText("AI's latest hit: " + displayCoordinate); // update aiHit
            ++friendlyShipsDestroyed;
        }

        // check for winner
        boolean checkWinner = checkWinner();
        if (checkWinner) return;

        updateLabels(); // update labels
    }


    /**
     * This method will execute appropriate actions when a certain button or other structure is clicked
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // loop through all buttons on the board
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (e.getSource() == attackGrid[i][j]) {
                    // check if alreadyGuessed
                    if (alreadyGuessed[i][j]) {
                        JOptionPane.showMessageDialog(null, "Already Guessed This Spot", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // mark current spot as guessed and get display coordinate
                    alreadyGuessed[i][j] = true;

                    // take turns
                    playerTurn(i, j);
                    aiTurn();
                }
            }
        }
    }
}
