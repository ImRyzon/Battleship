/**
 * @author Nathanael You
 * 2023-1-23
 *
 * This class will act as the game board when playing the game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JFrame implements ActionListener {

    /**
     * attackGrid --> 2d array that will act as the grid when attacking
     * attackPanel --> JPanel used for attacking and will implement the attack grid
     * defenseGrid --> grid that displays your own ships (defense grid)
     * defensePanel --> panel that displays the defense grid
     * gameIcon --> the icon for this page
     */
    JButton[][] attackGrid = new JButton[11][11];
    JPanel attackPanel = new JPanel();
    JButton[][] defenseGrid = new JButton[11][11];
    JPanel defensePanel = new JPanel();
    ImageIcon gameIcon = new ImageIcon("GameIcon.png");

    /**
     * This constructor will allow for instantiation of this class while also implementing the necessary
     * logic to make the game board function whe playing the game
     */
    GameBoard() {
        // customize attackPanel
        attackPanel.setLayout(new GridLayout(11, 11, 0, 0));
        attackPanel.setBounds(10, 10, 650, 650);
        attackPanel.setBackground(new Color(52, 152, 235));

        // create the attack grid and customize it
        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                attackGrid[x][y] = new JButton();
                attackGrid[x][y].setFocusable(false);
                attackGrid[x][y].addActionListener(this);
                attackGrid[x][y].setBackground(Color.BLACK);
                attackPanel.add(attackGrid[x][y]);
                if(x == 0){
                    attackGrid[x][y].setEnabled(false);
                    if(y != 0){
                        attackGrid[x][y].setText(String.valueOf(y));
                    }
                }
                if(y == 0){
                    attackGrid[x][y].setEnabled(false);
                }
            }
        }

        // customize defensePanel
        defensePanel.setLayout(new GridLayout(11, 11, 2, 2));
        defensePanel.setBounds(700, 10, 300, 300);
        defensePanel.setBackground(new Color(52, 152, 235));

        // create defense grid and customize it
        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                defenseGrid[x][y] = new JButton();
                defenseGrid[x][y].setFocusable(false);
                defenseGrid[x][y].addActionListener(this);
                defenseGrid[x][y].setBackground(Color.BLACK);
                defensePanel.add(defenseGrid[x][y]);
                if(x == 0){
                    defenseGrid[x][y].setEnabled(false);
                    if(y != 0){
                        defenseGrid[x][y].setText(String.valueOf(y));
                    }
                }
                if(y == 0){
                    defenseGrid[x][y].setEnabled(false);
                }
            }
        }

        // customize the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040, 700);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setTitle("Game Board");
        this.setResizable(false);

        // add the panels and gameIcon to the frame
        this.add(attackPanel);
        this.add(defensePanel);
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }
}
