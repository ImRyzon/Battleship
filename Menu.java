import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Menu implements ActionListener {

    /**
     * frame: the window for the login page, all the other components are added here
     *
     * titlePanel: a container used to store the titleImage and titleText. Added to the frame
     *
     * buttonPanel: a container used to store the playButton, profileButton, ruleButton, exitButton. Added to the frame
     *
     * titleImage: a JLabel object that displays an image
     *
     * titleText: a JLabel object that displays a title
     *
     * playButton: a JButton that deletes the current frame and instantiates a Game class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * profileButton: a JButton that deletes the current frame and instantiates a Profile class object. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * ruleButton: a JButton that prints out the rules in the terminal. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * exitButton: a JButton that deletes the current frame. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * icon: an ImageIcon object used to paint icons from images, in this case, from hammer.jpg
     *
     * currentUser: the text file that stores which user is currently logged in
     *
     * input: a Scanner object used to retrieve information on currentUser text file
     *
     * name: String variable used to store the String retrieved by input
     *
     * userStat: a File object with the file name as the name of the current logged-in user
     *
     * nullOutput: PrintWriter object used to write to the userStat file if it is empty
     *
     * br: BufferReader object that is used to read the file and check if it is empty
     */

    JFrame frame = new JFrame();

    JPanel titlePanel = new JPanel();

    JPanel buttonPanel = new JPanel();

    JLabel titleImage = new JLabel();
    JLabel titleText = new JLabel();

    JButton playButton = new JButton("Play");
    JButton profileButton = new JButton("Profile");
    JButton ruleButton = new JButton("Rules");
    JButton exitButton = new JButton("Exit");

    static String name = "Test";

    //constructor
    Menu() throws Exception {

        //setting up and customizing the JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("Menu");
        //frame.setResizable(false);

        //setting up and customizing the titleText object
        titleText.setText("Welcome " + name + "!");
        titleText.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        titleText.setForeground(Color.BLACK);
        titleText.setBounds(0, 0, 100, 50);

        //setting up and customizing the playButton object
        playButton.setFocusable(false); //set the focusable to false
        playButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        playButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        playButton.addActionListener(this); //adding an ActionListener
        playButton.setBackground(Color.WHITE); //set the color of the button
        playButton.setForeground(Color.BLACK); //set the color of the text
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the balanceButton object
        profileButton.setFocusable(false); //set the focusable to false
        profileButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        profileButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        profileButton.addActionListener(this); //adding an ActionListener
        profileButton.setBackground(Color.WHITE); //set the color of the button
        profileButton.setForeground(Color.BLACK); //set the color of the text
        profileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the playButton object
        ruleButton.setFocusable(false); //set the focusable to false
        ruleButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        ruleButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        ruleButton.addActionListener(this); //adding an ActionListener
        ruleButton.setBackground(Color.WHITE); //set the color of the button
        ruleButton.setForeground(Color.BLACK); //set the color of the text
        ruleButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting up and customizing the playButton object
        exitButton.setFocusable(false); //set the focusable to false
        exitButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        exitButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        exitButton.addActionListener(this); //adding an ActionListener
        exitButton.setBackground(Color.WHITE); //set the color of the button
        exitButton.setForeground(Color.BLACK); //set the color of the text
        exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        //setting the layout for the buttonPanel object
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //changing the color of the 2 panel
        titlePanel.setBackground(Color.GRAY);
        buttonPanel.setBackground(Color.GRAY);

        //adding the components to the titlePanel object
        titlePanel.add(titleImage);
        titlePanel.add(titleText);

        //adding the components to the buttonPanel object
        buttonPanel.add(playButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(ruleButton);
        buttonPanel.add(exitButton);

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

        //if playButton is clicked
        if(e.getSource() == playButton) {
            frame.dispose(); //delete the current frame

            //use try and catch to instantiate an object for Game class
            try {
                GamePage game = new GamePage();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if profileButton is clicked
        if(e.getSource() == profileButton) {
            frame.dispose();

            //use try and catch to instantiate an object for Profile class
            try {
                Profile profile = new Profile();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if the ruleButton is clicked, print out the rules
        if(e.getSource() == ruleButton) {
            frame.dispose();

            //use try and catch to instantiate an object for Profile class
            try {
                Rules rulePage = new Rules();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        //if the exit button is clicked
        if(e.getSource() == exitButton) {
            System.out.println("Goodbye, see you next time!"); //print out a message on screen

            frame.dispose(); //delete the frame
        }
    }
}