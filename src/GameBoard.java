/**
 * @author Nathanael You, Mark Wang, Daniel Guo
 * 2023-1-23
 *
 * This class will act as the game board when playing the game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class GameBoard extends JFrame implements ActionListener {

    /**
     * attackGrid --> 2d array that will act as the grid when attacking
     * attackPanel --> JPanel used for attacking and will implement the attack grid
     * defenseGrid --> grid that displays your own ships (defense grid)
     * defensePanel --> panel that displays the defense grid
     * gameIcon --> the icon for this page
     * friendlyShipsDestroyed --> counts how many of own ships are destroyed
     * enemyShipsDestroyed --> counts how many of enemy ships are destroyed
     * friendlyLabel --> label that displays how many friendly ships have been destroyed
     * enemyLabel --> label that displays how many enemy ships have been destroyed
     * yourHit --> JLabel for your hit
     * aiHit --> JLabel indicating AI hit
     * explosionImage --> image for explosions when ships are hit
     * cloudImage --> image for clouds (default image when square isn't guessed)
     * oceanImage --> image for oceans when the square contains no ships
     * oceanLabel --> label for ocean
     * infoPanel --> panel to store information and statistics
     * counterLabel --> label that keeps track of time
     * timer --> actual timer with time
     * second --> displays seconds
     * minute --> displays minutes
     * ddSecond --> decimal format for second
     * ddMinute --> decimal format for minute
     * dFormat --> decimal format for formatting
     */
    private JButton[][] attackGrid = new JButton[11][11];
    private JPanel attackPanel = new JPanel();
    private JButton[][] defenseGrid = new JButton[11][11];
    private JPanel defensePanel = new JPanel();
    private ImageIcon gameIcon = new ImageIcon("GameIcon.png");
    private int friendlyShipsDestroyed = 0;
    private int enemyShipsDestroyed = 0;
    private JLabel friendlyLabel = new JLabel();
    private JLabel enemyLabel = new JLabel();
    private JLabel yourHit = new JLabel();
    private JLabel aiHit = new JLabel();
    private ImageIcon explosionImage = new ImageIcon("explosion.png");
    private ImageIcon cloudImage = new ImageIcon("clouds.png");
    private ImageIcon oceanImage = new ImageIcon("ocean.png");
    private JLabel oceanLabel = new JLabel();
    private JPanel infoPanel = new JPanel();
    private JLabel counterLabel = new JLabel();
    private Timer timer;
    private int second;
    private int minute;
    private String ddSecond;
    private String ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");

    /**
     * This constructor will allow for instantiation of this class while also implementing the necessary
     * logic to make the game board function whe playing the game
     */
    public GameBoard() {
        // customize attackPanel
        attackPanel.setLayout(new GridLayout(11, 11, 0, 0));
        attackPanel.setBounds(10, 10, 650, 650);
        attackPanel.setBackground(Color.BLUE);

        // customise infoPanel
        infoPanel.setBounds(700, 470, 450, 191);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // create the attack grid and customize it
        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                // customize current button
                attackGrid[x][y] = new JButton();
                attackGrid[x][y].setOpaque(false);
                attackGrid[x][y].setFocusable(false);
                attackGrid[x][y].setBorderPainted(false);
                attackGrid[x][y].addActionListener(this);
                attackGrid[x][y].setBackground(Color.BLACK);
                attackGrid[x][y].setIcon(cloudImage);
                attackPanel.add(attackGrid[x][y]);

                // set numbers and characters as the coordinates
                if(x == 0){
                    attackGrid[x][y].setEnabled(false);
                    if(y != 0){
                        attackGrid[x][y].setText(String.valueOf(y));
                        attackGrid[x][y].setOpaque(true);
                        attackGrid[x][y].setBorderPainted(true);
                        attackGrid[x][y].setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                        attackGrid[x][y].setIcon(null);
                    }
                }

                if(y == 0){
                    attackGrid[x][y].setEnabled(false);
                    if(x != 0){
                        attackGrid[x][y].setText(String.valueOf((char)(x + 'A' - 1)));
                        attackGrid[x][y].setOpaque(true);
                        attackGrid[x][y].setBorderPainted(true);
                        attackGrid[x][y].setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                        attackGrid[x][y].setIcon(null);
                    }
                }
            }
        }

        // customize top left cell
        attackGrid[0][0].setBorderPainted(true);
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
                // customize current defense grid button
                defenseGrid[x][y] = new JButton();
                defenseGrid[x][y].setFocusable(false);
                defenseGrid[x][y].setEnabled(false);
                defenseGrid[x][y].setBackground(Color.BLACK);
                defensePanel.add(defenseGrid[x][y]);

                // set numbers and letters for the coordinates
                if(x == 0){
                    defenseGrid[x][y].setEnabled(false);
                    if(y != 0){
                        defenseGrid[x][y].setText(String.valueOf(y));
                        defenseGrid[x][y].setFont(new Font("Comic Sans MS", Font.BOLD, 5));
                    }
                }

                if(y == 0){
                    defenseGrid[x][y].setEnabled(false);
                    if(x != 0) {
                        defenseGrid[x][y].setText(String.valueOf((char)(x + 'A' - 1)));
                        defenseGrid[x][y].setFont(new Font("Comic Sans MS", Font.BOLD, 5));
                    }
                }
            }
        }

        // customize the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1170, 700);
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
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true);
    }

    /**
     * This method will update both labels when ships have been destroyed in the game
     */
    public void updateLabel() {
        friendlyLabel.setText("Friendly Ships Destroyed: " + friendlyShipsDestroyed);

        enemyLabel.setText("Enemy Ships Destroyed: " + enemyShipsDestroyed);
    }

    /**
     * This method will keep track of time elapsed
     */
    public void normalTimer() {

        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                second++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);

                counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);

                if(second == 60) {
                    second = 0;
                    minute++;
                    counterLabel.setText("Time Elapsed: " + ddMinute + ":" + ddSecond);
                }
            }
        });
    }

    /**
     * This method will execute appropriate actions when a certain button or other structure is clicked
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if(event.getSource() == attackGrid[1][1]) {

            attackGrid[1][1].setIcon(explosionImage);
            attackGrid[1][1].setOpaque(false);
        }

        if(event.getSource() == attackGrid[1][2]) {

            attackGrid[1][2].setIcon(null);
            attackGrid[1][2].setIcon(oceanImage);
        }
    }
}
