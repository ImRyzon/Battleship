/**
 * @author Mark Wang, Henry Lee
 * 2023-1-18
 *
 * This class will act as the menu page for the Battleship Game
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

public class Menu extends JFrame implements ActionListener {

    /**
     * These are the objects needed to make the menu
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the menu page
     * title --> the title of the menu
     * playGame --> the button in which the user presses if they wish to start the game
     * rules --> the button in which the user presses if they wish to see the rules
     * profile --> the button in which the user presses if they wish to see their profile
     * logout --> the button in which the user presses if they want to logout the game
     * brokenPlate --> the actual image file of the broken plate
     * menuBackground --> the image icon used for this frame/background/title
     * menuTitle --> the image icon used for title
     * backgroundClip --> clip for background music
     * buttonClip --> clip for button sound
     * backgroundCLip --> clip for background
     * buttonClip --> clip for button
     */
    JPanel panel = new JPanel();
    JLabel title = new JLabel("Menu");
    JLabel backgroundLabel = new JLabel();
    JButton playGame = new JButton();
    JButton rules = new JButton();
    JButton profile = new JButton();
    JButton logout = new JButton();
    ImageIcon menu = new ImageIcon("menu.png");
    ImageIcon menuBackground = new ImageIcon("MenuBackground.png");
    ImageIcon menuTitle = new ImageIcon("MenuTitle.png");
    Clip backgroundClip;
    AudioInputStream audioInputStreamA;
    Clip buttonClip;
    AudioInputStream audioInputStreamB;

    /**
     * This constructor enables other classes to create an object of this class.
     *
     * It will also implement the needed logic and steps in order to help make the menu
     * page work and function properly.
     */
    Menu() {
        try {
            backgroundClip = AudioSystem.getClip();
            backgroundClip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        // Set the frame size, close operation, visibility, title, background, and icon, and add the panel
        this.setSize(540, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Main Menu");
        this.setIconImage(menu.getImage());
        this.setBackground(Color.WHITE);

        // set absolute positioning
        panel.setLayout(null);

        // Customize the title text field and add it to the panel
        title.setBounds(145, 0, 250, 162);
        title.setIcon(menuTitle);
        panel.add(title);

        // Customize the playGame button and add it to the panel
        playGame.setText("Play");
        playGame.setBounds(183, 150, 175, 75);
        playGame.setFont(new Font("Monospaced", Font.BOLD, 30));
        playGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        playGame.setBackground(Color.WHITE);
        playGame.addActionListener(this);
        panel.add(playGame);

        // Customize the rules button and add it to the panel
        rules.setText("Rules");
        rules.setBounds(183, 250, 175, 75);
        rules.setFont(new Font("Monospaced", Font.BOLD, 30));
        rules.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        rules.setBackground(Color.WHITE);
        rules.addActionListener(this);
        panel.add(rules);

        // Customize the profile button and add it to the panel
        profile.setText("Profile");
        profile.setBounds(183, 350, 175, 75);
        profile.setFont(new Font("Monospaced", Font.BOLD, 30));
        profile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        profile.setBackground(Color.WHITE);
        profile.addActionListener(this);
        panel.add(profile);

        // Customize the logout button and add it to the panel
        logout.setText("Logout");
        logout.setBounds(183, 450, 175, 75);
        logout.setFont(new Font("Monospaced", Font.BOLD, 30));
        logout.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        logout.setBackground(Color.WHITE);
        logout.addActionListener(this);
        panel.add(logout);

        // Customize the background
        backgroundLabel.setBounds(0, 0, 540, 700);
        backgroundLabel.setIcon(menuBackground);
        panel.add(backgroundLabel);

        // Set the panel to be visible
        panel.setVisible(true);
    }

    /**
     * this method will play background music
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void playBackground() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStreamA = AudioSystem.getAudioInputStream(new File("background_music1.wav"));

        // create clip reference
        backgroundClip = AudioSystem.getClip();

        // open audioInputStream to the clip
        backgroundClip.open(audioInputStreamA);

        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

        backgroundClip.start();
    }

    /**
     * this method will play button sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void playButton() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStreamB = AudioSystem.getAudioInputStream(new File("buttonsound.wav"));

        // create clip reference
        buttonClip = AudioSystem.getClip();

        // open audioInputStream to the clip
        buttonClip.open(audioInputStreamB);

        buttonClip.start();
    }

    /**
     * This method will redirect the user to the corresponding page based on which button
     * they pressed
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == playGame) { // Redirect user to game screen if they want to play

            try {
                playButton();
                backgroundClip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose();

            try {
                GamePage game = new GamePage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() == rules) { // Redirect user to rules screen if they want to see the rules

            try {
                playButton();
                backgroundClip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose();

            try {
                Rules rules = new Rules();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() == profile) { // Redirect user to profile screen if they wish

            try {
                playButton();
                backgroundClip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose();

            try {
                Profile profile = new Profile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() == logout) { // If user wants to logout, put on the login page

            try {
                playButton();
                backgroundClip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,
                    "You have been successfully logged out", "Logout Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new LoginPage();
        }
    }
}
