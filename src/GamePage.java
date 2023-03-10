/**
 * @author Daniel Guo
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

public class GamePage extends JFrame implements ActionListener {

    /**
     * These are the objects needed to make the game page
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the menu page
     * titleLabel --> the title of the game page
     * backgroundLabel --> the background of the game page
     * easyButton --> the button in which the user presses if they wish to play an easy game
     * hardButton --> the button in which the user presses if they wish to play a hard game
     * menuButton --> the button in which the user presses if they want to return to the menu
     * titleImage --> the actual image file of the title text
     * backgroundImage --> the actual image file of the background for game page
     * menuIcon --> the image icon used for this frame
     * backgroundClip --> clip for background music
     * buttonClip --> clip for button sound
     */
    JPanel panel = new JPanel();
    JLabel titleLabel = new JLabel();
    JLabel backgroundLabel = new JLabel();
    JButton easyButton = new JButton();
    JButton hardButton = new JButton();
    JButton menuButton = new JButton();
    ImageIcon titleImage = new ImageIcon("GamePageTitleImg.png");
    ImageIcon backgroundImage = new ImageIcon("GamePageBackground.png");
    ImageIcon menuIcon = new ImageIcon("gamepageIcon.png");
    Clip backgroundClip = AudioSystem.getClip();
    AudioInputStream audioInputStreamA;
    Clip buttonClip = AudioSystem.getClip();
    AudioInputStream audioInputStreamB;

    /**
     * This constructor enables other classes to create an object of this class.
     *
     * It will also implement the needed logic and steps in order to help make the game
     * page work and function properly.
     */
    GamePage() throws LineUnavailableException {

        try {
            playBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Customizing the title image and adding it to the panel
        titleLabel.setBounds(75, 20, 800, 100);
        titleLabel.setIcon(titleImage);
        panel.add(titleLabel);

        //Customize frame
        this.setSize(540, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Game Page");
        this.setBackground(Color.WHITE);
        this.setIconImage(menuIcon.getImage());
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
        hardButton.setBounds(135, 300, 255, 75);
        hardButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        hardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        hardButton.setBackground(Color.WHITE);
        hardButton.addActionListener(this);
        panel.add(hardButton);

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

        if (event.getSource() == easyButton) { //Redirect user to a gameboard

            try {
                backgroundClip.stop();
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for GameBoard class
            try {
                PlaceShips game = new PlaceShips(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() == hardButton) { //Redirect user to a gameboard

            try {
                backgroundClip.stop();
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for GameBoard class
            try {
                PlaceShips game = new PlaceShips(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() == menuButton) { //Redirect the user to the menu page

            try {
                backgroundClip.stop();
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.dispose(); //Deleting the current frame

            //use try and catch to instantiate an object for Menu class
            try {
                Menu menu = new Menu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
