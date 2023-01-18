/**
 * This class will act as the menu page of the Break A Plate game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame implements ActionListener {

    /**
     * These are the objects needed to make the menu
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the menu page
     * title --> the title of the game "Break A Plate"
     * icon --> the image of a broken plate for the icon of the game
     * playGame --> the button in which the user presses if they wish to start the game
     * rules --> the button in which the user presses if they wish to see the rules
     * profile --> the button in which the user presses if they wish to see their profile
     * exit --> the button in which the user presses if they want to exit the game
     * brokenPlate --> the actual image file of the broken plate
     * menu --> the image icon used for this frame
     */
    JPanel panel = new JPanel();
    JLabel title = new JLabel("Welcome To Break A Plate!");
    JLabel icon = new JLabel();
    JButton playGame = new JButton();
    JButton rules = new JButton();
    JButton profile = new JButton();
    JButton exit = new JButton();
    ImageIcon brokenPlate = new ImageIcon("brokenplate.png");
    ImageIcon menu = new ImageIcon("menu.png");

    /**
     * This constructor enables other classes to create an object of this class.
     *
     * It will also implement the needed logic and steps in order to help make the menu
     * page work and function properly.
     */
    Menu() {
        // Set the frame size, close operation, visibility, title, background, and icon, and add the panel
        this.setSize(560, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Main Menu");
        this.setIconImage(menu.getImage());
        this.setBackground(Color.WHITE);

        // Set the mode of the panel to absolute positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(229, 223, 223));

        // Customize the title text field and add it to the panel
        title.setBounds(50, 10, 420, 50);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setBorder(BorderFactory.createLineBorder(new Color(211, 211, 211), 0));
        title.setBackground(new Color(211, 211, 211));
        title.setVisible(true);
        panel.add(title);

        // Set the icon to the broken plate image and add it to the panel
        icon.setIcon(brokenPlate);
        icon.setBounds(425, 90, 100, 100);
        icon.setVisible(true);
        panel.add(icon);

        // Customize the playGame button and add it to the panel
        playGame.setText("Play");
        playGame.setBounds(160, 100, 175, 75);
        playGame.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        playGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        playGame.setBackground(Color.WHITE);
        playGame.addActionListener(this);
        panel.add(playGame);

        // Customize the rules button and add it to the panel
        rules.setText("Rules");
        rules.setBounds(160, 200, 175, 75);
        rules.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        rules.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        rules.setBackground(Color.WHITE);
        rules.addActionListener(this);
        panel.add(rules);

        // Customize the profile button and add it to the panel
        profile.setText("Profile");
        profile.setBounds(160, 300, 175, 75);
        profile.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        profile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        profile.setBackground(Color.WHITE);
        profile.addActionListener(this);
        panel.add(profile);

        // Customize the exit button and add it to the panel
        exit.setText("Exit");
        exit.setBounds(160, 400, 175, 75);
        exit.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        exit.setBackground(Color.WHITE);
        exit.addActionListener(this);
        panel.add(exit);

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
        if (event.getSource() == playGame) { // Redirect user to game screen if they want to play
            this.dispose();
            try {
                GamePage page = new GamePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == rules) { // Redirect user to rules screen if they want to see the rules
            this.dispose();
            Rules rules = new Rules();
        } else if (event.getSource() == profile) { // Redirect user to profile screen if they wish
            this.dispose();
            try {
                Profile profile = new Profile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == exit) { // If user wants to exit, then terminate the program
            this.dispose();
            System.exit(0);
        }
    }
}
