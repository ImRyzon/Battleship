import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class GamePage implements ActionListener {

    /**
     * frame: the window for the login page, all the other components are added here
     *
     * buttonPanel: a container used to store the easyButton, hardButton, multiplayerButton, menuButton. Added to the frame
     *
     * titleText: a JLabel object that displays a title
     *
     * easyButton: a JButton that deletes the current frame and instantiates a gameBoard class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * hardButton: a JButton that deletes the current frame and instantiates a gameBoard class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * multiplayerButton: a JButton that deletes the current frame and instantiates a gameMultiplayer class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * menuButton: a JButton that deletes the current frame and instantiates a Menu class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     */

    JFrame frame = new JFrame();

    JPanel buttonPanel = new JPanel();
    JPanel titlePanel = new JPanel();

    JLabel titleImage = new JLabel();
    ImageIcon titleIcon = new ImageIcon("GamePageTitleImage.png");

    JButton easyButton = new JButton("Easy");
    JButton hardButton = new JButton("Hard");
    JButton multiplayerButton = new JButton("Multiplayer");
    JButton menuButton = new JButton("Menu");

    //constructor
    GamePage() throws Exception {

        //setting up and customizing the JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("Game Page");
        frame.setResizable(false);

        //setting up and customizing the easyButton object
        easyButton.setFocusable(false); //set the focusable to false
        easyButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        easyButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        easyButton.addActionListener(this); //adding an ActionListener
        easyButton.setBackground(Color.WHITE); //set the color of the button
        easyButton.setForeground(Color.BLACK); //set the color of the text
        easyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the balanceButton object
        hardButton.setFocusable(false); //set the focusable to false
        hardButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        hardButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        hardButton.addActionListener(this); //adding an ActionListener
        hardButton.setBackground(Color.WHITE); //set the color of the button
        hardButton.setForeground(Color.BLACK); //set the color of the text
        hardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the easyButton object
        multiplayerButton.setFocusable(false); //set the focusable to false
        multiplayerButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        multiplayerButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        multiplayerButton.addActionListener(this); //adding an ActionListener
        multiplayerButton.setBackground(Color.WHITE); //set the color of the button
        multiplayerButton.setForeground(Color.BLACK); //set the color of the text
        multiplayerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the easyButton object
        menuButton.setFocusable(false); //set the focusable to false
        menuButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        menuButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        menuButton.addActionListener(this); //adding an ActionListener
        menuButton.setBackground(Color.WHITE); //set the color of the button
        menuButton.setForeground(Color.BLACK); //set the color of the text
        menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting the layout for the buttonPanel object
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //changing the color of the 2 panel
        buttonPanel.setBackground(Color.GRAY);

        //adding the components to the titlePanel object
        titleImage.setIcon(titleIcon);
        titlePanel.add(titleImage);

        //adding the components to the buttonPanel object
        buttonPanel.add(easyButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(multiplayerButton);
        buttonPanel.add(menuButton);

        //adding the panels to the frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * actionPerformed method will perform certain actions based on what the user did
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //if easyButton is clicked
        if(e.getSource() == easyButton) {
            frame.dispose(); //delete the current frame

            //use try and catch to instantiate an object for Game class
            try {
                GameBoard game = new GameBoard();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if hardButton is clicked
        if(e.getSource() == hardButton) {
            frame.dispose();

            //use try and catch to instantiate an object for Profile class
            try {
                GameBoard game = new GameBoard();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if the multiplayerButton is clicked, print out the rules
        if(e.getSource() == multiplayerButton) {
            frame.dispose();

            //use try and catch to instantiate an object for Profile class
            try {
                GameMultiplayer game = new GameMultiplayer();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if the exit button is clicked
        if(e.getSource() == menuButton) {
            frame.dispose();

            //use try and catch to instantiate an object for Profile class
            try {
                Menu menu = new Menu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}