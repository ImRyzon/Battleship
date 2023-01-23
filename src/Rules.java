/**
 * @author Mark Wang
 * 2023-1-18
 *
 * This class will display the rules of Battleship
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Rules extends JFrame implements ActionListener {

    /**
     * These are the necessary objects that help make the Rules page function properly
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the rules page
     * title --> The title of this page, which is "Rules"
     * rules --> the array containing all the rules to be outputted
     * backToMenu --> the button that redirects the user back to the menu screen
     * rulesIcon --> icon for the current page
     * rulesFile --> file to store all the rules
     */
    JPanel panel = new JPanel();
    JLabel title = new JLabel("Rules");
    JLabel[] rules = new JLabel[13];
    JButton backToMenu = new JButton("Menu");
    ImageIcon rulesIcon = new ImageIcon("rules.png");
    File rulesFile = new File("Rules.txt");

    /**
     * This constructor will allow other classes to instantiate an object of the menu class
     * as well as implement the necessary logic to make the menu function as intended
     */
    Rules() throws Exception {
        // Set the frame size, close operation, visibility, title, background, icon, and add the panel
        this.setSize(960, 950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Rules");
        this.setBackground(Color.WHITE);
        this.setIconImage(rulesIcon.getImage());

        // Set the mode of the panel to absolute positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(173, 216, 230));

        // Customize the title text field and add it to the panel
        title.setBounds(400, 10, 420, 50);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        title.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 203), 0));
        title.setBackground(new Color(255, 204, 203));
        title.setVisible(true);
        panel.add(title);

        getRules(); // get all the rules

        // Loop though the positions of all the rules
        int y = 95;
        for (int i = 0; i < rules.length; i++) {
            rules[i].setBounds(65, y, 900, 55);
            rules[i].setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            rules[i].setBorder(BorderFactory.createLineBorder(new Color(255, 204, 203), 0));
            rules[i].setBackground(new Color(255, 204, 203));
            rules[i].setVisible(true);
            panel.add(rules[i]);
            y += 50;
        }

        // Customize the menu button and add it to the panel
        backToMenu.setText("Menu");
        backToMenu.setBounds(405, 765, 125, 75);
        backToMenu.setFont(new Font("Monospaced", Font.BOLD, 30));
        backToMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        backToMenu.setBackground(Color.WHITE);
        backToMenu.addActionListener(this);
        panel.add(backToMenu);

        // Make the panel visible to the user
        panel.setVisible(true);
    }

    /**
     * this method will get all the rules from the rules file and store them into the
     * JLabel array to be outputted on the screen
     * @throws Exception
     */
    void getRules() throws Exception {
        Scanner readRules = new Scanner(rulesFile); // create scanner to read rules

        // get all the rules from the file via scanner
        int index = 0;
        while (readRules.hasNextLine()) {
            rules[index] = new JLabel(readRules.nextLine());
            index++;
        }

        readRules.close(); // close scanner
    }

    /**
     * This method will take the user back to the menu after they pressed the menu button
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == backToMenu) { // bring user back to menu if they wish
            this.dispose();
            Menu menu = new Menu();
        }
    }
}
