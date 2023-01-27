/**
 * @author Mark Wang, Henry Lee
 * 2023-1-18
 *
 * This class will display the rules of Battleship
 */

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Rules extends JFrame implements ActionListener {

    /**
     * These are the necessary objects that help make the Rules page function properly
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the rules page
     * background --> The label to store the background image
     * title --> The title of this page, which is "Rules"
     * rules --> the array containing all the rules to be outputted
     * backToMenu --> the button that redirects the user back to the menu screen
     * rulesIcon --> icon for the current page
     * rulesFile --> file to store all the rules
     * rules images --> Images for the rules background and title
     */
    JPanel panel = new JPanel();
    JLabel background = new JLabel("Background");
    JLabel title = new JLabel("Rules");
    JLabel[] rules = new JLabel[13];
    JButton backToMenu = new JButton("Menu");
    ImageIcon rulesIcon = new ImageIcon("rules.png");
    File rulesFile = new File("Rules.txt");
    ImageIcon rulesBackground = new ImageIcon("RulesBackground.png");
    ImageIcon rulesTitle = new ImageIcon("RulesTitle.png");

    /**
     * This constructor will allow other classes to instantiate an object of the menu class
     * as well as implement the necessary logic to make the menu function as intended
     */
    Rules() throws Exception {
        // Set the frame size, close operation, visibility, title, background, icon, and add the panel
        this.setSize(960, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Rules");
        this.setBackground(Color.WHITE);
        this.setIconImage(rulesIcon.getImage());
        this.setResizable(false);

        // Set the mode of the panel to absolute positioning
        panel.setLayout(null);

        // Customize the title label and add it to the panel
        title.setBounds(355, -25, 250, 160);
        title.setIcon(rulesTitle);
        title.setVisible(true);
        panel.add(title);

        // Customize the background label and add it to the panel
        background.setBounds(0, 0, 960, 800);
        background.setIcon(rulesBackground);
        background.setVisible(true);
        panel.add(background);

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
            y += 40;
        }

        // Customize the menu button and add it to the panel
        backToMenu.setText("Menu");
        backToMenu.setBounds(405, 650, 125, 75);
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

    public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("buttonsound.wav"));

        // create clip reference
        Clip clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.start();
    }

    /**
     * This method will take the user back to the menu after they pressed the menu button
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == backToMenu) { // bring user back to menu if they wish

            try {
                playSound();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose();

            Menu menu = new Menu();
        }
    }
}
