/**
 * @author Daniel Guo
 * 2023-1-18
 *
 * This class will act as the menu page for the Battleship Game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePage extends JFrame implements ActionListener {

    /**
     * These are the objects needed to make the game page
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the menu page
     * titleLabel --> the title of the game page
     * backgroundLabel --> the background of the game page
     * easyButton --> the button in which the user presses if they wish to play an easy game
     * hardButton --> the button in which the user presses if they wish to play a hard game
     * multiplayerButton --> the button in which the user presses if they wish to play against another player using shared computer
     * menuButton --> the button in which the user presses if they want to return to the menu
     * titleImage --> the actual image file of the title text
     * backgroundImage --> the actual image file of the background for game page
     * menu --> the image icon used for this frame
     */
    JPanel panel = new JPanel();
    JLabel titleLabel = new JLabel();
    JLabel backgroundLabel = new JLabel();
    JButton easyButton = new JButton();
    JButton hardButton = new JButton();
    JButton multiplayerButton = new JButton();
    JButton menuButton = new JButton();
    ImageIcon titleImage = new ImageIcon("GamePageTitleImg.png");
    ImageIcon backgroundImage = new ImageIcon("GamePageBackground.png");

    /**
     * This constructor enables other classes to create an object of this class.
     *
     * It will also implement the needed logic and steps in order to help make the game
     * page work and function properly.
     */
    GamePage() {

        //Customizing the title image and adding it to the panel
        titleLabel.setBounds(75, 20, 800, 100);
        titleLabel.setIcon(titleImage);
        panel.add(titleLabel);
        
        //Set the frame size, close operation, visibility, title, background, and icon, and add the panel
        this.setSize(540, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Game Page");
        this.setBackground(Color.WHITE);
        this.setResizable(false);

        //Set the layout of the panel to be null
        panel.setLayout(null);

        //Customize the easyButton button and add it to the panel
        easyButton.setText("Easy");
        easyButton.setBounds(135, 150, 255, 75);
        easyButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        easyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        easyButton.setBackground(Color.WHITE);
        easyButton.addActionListener(this);
        panel.add(easyButton);

        //Customize the hardButton button and add it to the panel
        hardButton.setText("Hard");
        hardButton.setBounds(135, 250, 255, 75);
        hardButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        hardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        hardButton.setBackground(Color.WHITE);
        hardButton.addActionListener(this);
        panel.add(hardButton);

        //Customize the multiplayerButton button and add it to the panel
        multiplayerButton.setText("Multiplayer");
        multiplayerButton.setBounds(135, 350, 255, 75);
        multiplayerButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        multiplayerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        multiplayerButton.setBackground(Color.WHITE);
        multiplayerButton.addActionListener(this);
        panel.add(multiplayerButton);

        //Customize the menuButton and add it to the panel
        menuButton.setText("Menu");
        menuButton.setBounds(135, 450, 255, 75);
        menuButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        menuButton.setBackground(Color.WHITE);
        menuButton.addActionListener(this);
        panel.add(menuButton);

        //Customize the background
        backgroundLabel.setBounds(0, 0, 540, 700);
        backgroundLabel.setIcon(backgroundImage);
        panel.add(backgroundLabel);

        // Set the panel to be visible
        panel.setVisible(true);
    }

    /**
     * This method will redirect the user to the corresponding page based on which button
     * they pressed
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == easyButton) { //Redirect user to a gameboard
            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for GameBoard class
            try {
                new GamePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        
        else if (event.getSource() == hardButton) { //Redirect user to a gameboard
            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for GameBoard class
            try {
                new GameBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        
        else if (event.getSource() == multiplayerButton) { //Redirect user to a gameboard
            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for GameBoard class
            try {
                new GameBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        
        else if (event.getSource() == menuButton) { //Redirect the user to the menu page
            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for Menu class
            try {
                new Menu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
