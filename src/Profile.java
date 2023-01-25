/**
 * @author Mark Wang, Nathanael You
 * 2023-1-18
 *
 * This class will display the Profile of the current logged-in user
 */

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Profile extends JFrame implements ActionListener {

    /**
     * These are the necessary objects for the profile page
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the profile page
     * currentStats --> statistics holder of the current user
     * title --> the title of this window ("Profile")
     * userID --> the userID filepath to get the current user ID
     * statistics --> the statistics filepath to retrieve and fill the currentStats array
     * backToMenu --> the button that will re-direct the user back to the main menu when they wish
     * profileBackground --> image to display profile background
     * profile --> the image icon used for this frame
     * profileTitle --> image to display profile image
     */

    JLayeredPane panel = new JLayeredPane();
    JLabel background = new JLabel();
    JLabel[] currentStats = new JLabel[4];
    JLabel title = new JLabel("Profile");
    File userID = new File("UserID.txt");
    File statistics = new File("Statistics.txt");
    JButton backToMenu = new JButton("Menu");
    ImageIcon profileBackground = new ImageIcon("ProfileBackground.png");
    ImageIcon profile = new ImageIcon("profile.png");
    ImageIcon profileTitle = new ImageIcon("profileTitle.png");

    Clip backgroundClip = AudioSystem.getClip();
    AudioInputStream audioInputStreamA;
    Clip buttonClip = AudioSystem.getClip();
    AudioInputStream audioInputStreamB;

    /**
     * This constructor will allow other classes to create an object of this class and will also
     * implement the  necessary logic to build the profile page
     * @throws Exception
     */
    Profile() throws Exception {

        try {
            playBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the frame size, close operation, visibility, title, background, and icon, and add the panel
        this.setSize(525, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Profile");
        this.setBackground(Color.WHITE);
        this.setIconImage(profile.getImage());
        this.setResizable(false);

        // Set the mode of the panel to absolutely positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(0, 0, 0, 0));

        // Customize the title panel
        background.setBounds(0, 0, 525, 550);
        background.setIcon(profileBackground);
        background.setVisible(true);
        panel.add(background, Integer.valueOf(0));

        // Customize the title panel
        title.setBounds(142, 10, 240, 136);
        title.setIcon(profileTitle);
        title.setVisible(true);
        panel.add(title, Integer.valueOf(1));

        // Fill and customize the numBroken array, then add it to the panel
        fillStatistics();
        int y = 140;
        for (int i = 0; i < 4; i++) {
            currentStats[i].setBounds(125, y, 420, 55);
            currentStats[i].setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            currentStats[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
            currentStats[i].setVisible(true);
            panel.add(currentStats[i], Integer.valueOf(i + 2));
            y += 50;
        }

        // Customize the menu button and add it to the panel
        backToMenu.setText("Menu");
        backToMenu.setBounds(195, 375, 100, 50);
        backToMenu.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        backToMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        backToMenu.setBackground(Color.WHITE);
        backToMenu.addActionListener(this);
        panel.add(backToMenu);

        // Set the panel to be visible
        panel.setVisible(true);
    }

    public void playBackground() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStreamA = AudioSystem.getAudioInputStream(new File("background_music1.wav"));

        // create clip reference
        backgroundClip = AudioSystem.getClip();

        // open audioInputStream to the clip
        backgroundClip.open(audioInputStreamA);

        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

        backgroundClip.start();
    }

    public void playButton() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStreamB = AudioSystem.getAudioInputStream(new File("buttonsound.wav"));

        // create clip reference
        backgroundClip = AudioSystem.getClip();

        // open audioInputStream to the clip
        buttonClip.open(audioInputStreamB);

        buttonClip.start();
    }

    /**
     * This method will fill the statistics array so that it can output all the statistics of the user.
     * @throws Exception
     */
    public void fillStatistics() throws Exception {
        // Declare scanner to get the user ID index and a scanner to get all the statistics numbers
        Scanner readUserID = new Scanner(userID);
        Scanner readStatistics = new Scanner(statistics);

        // Get the index of the current logged-in user
        int index = Integer.parseInt(readUserID.nextLine());
        readUserID.close();

        // Skip the correct number of lines until the right set of statistics are next in line
        while (index --> 0) {
            for (int i = 0; i < 6; i++) {
                readStatistics.nextLine();
            }
        }

        // Fill the wins, losses, and ships sunk accordingly
        currentStats[0] = new JLabel("Wins: " + readStatistics.nextLine());
        currentStats[1] = new JLabel("Losses: " + readStatistics.nextLine());
        currentStats[2] = new JLabel("Ships Sunk: " + readStatistics.nextLine());

        // Calculate the hit percentage, round it to the nearest whole percent, and then fill it
        double totalHits = Double.parseDouble(readStatistics.nextLine());
        double totalMisses = Double.parseDouble(readStatistics.nextLine());
        currentStats[3] = new JLabel( "Hit Percentage: " + Math.round((totalHits / (totalHits + totalMisses)) * 100.0) + "%");

        // set the background color for all the stats
        for (int i = 0; i < 3; i++) {
            currentStats[i].setBackground(new Color(255, 213, 128));
        }

        readStatistics.close();
    }

    /**
     * The action performed method will re-direct the user to the menu when they wish
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == backToMenu) {

            try {
                backgroundClip.stop();
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose();
            Menu menu = new Menu();
        }
    }
}
