/**
 * @author Nathanael You, Mark Wang, Daniel Guo
 * 2023-1-18
 *
 * This class will act as the login page for the Battleship game
 */

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class LoginPage extends JFrame implements ActionListener {

    private JLayeredPane layeredPane = new JLayeredPane(); //Creates layered pane for Login page
    private JLabel backgroundLabel = new JLabel(); //Creates the label for the background image
    private JLabel titleLabel = new JLabel(); //Creates the label for the title of the game
    private JLabel loginLabel = new JLabel(); //Creates the label for the login text fields and buttons
    private Label usernameLabel = new JLabel(); //Creates label to display the word "username"
    private JLabel passwordLabel = new JLabel(); //Creates label to display the word "password"
    private JButton loginButton = new JButton("Login"); //Creates the login button
    private JButton registerButton = new JButton("Register"); //Creates the register button
    private JTextField usernameText = new JTextField();  //Create password field for user to enter their username
    private JPasswordField passwordText = new JPasswordField(); //Create password field for user to enter their password
    static File database = new File("Database.txt"); // file for database
    static File userID = new File("UserID.txt"); // file for current user ID
    static File statistics = new File("Statistics.txt"); // file for statistics
    private ImageIcon gameIcon = new ImageIcon("GameIcon.png");//Create the game icon image
    private ImageIcon backgroundIcon = new ImageIcon("LoginBackground.png");//Create the login background image
    private ImageIcon titleIcon = new ImageIcon("TitleIcon.png");//Create the game title image
    private ImageIcon usernameIcon = new ImageIcon("UsernameIcon.png");//Create the username image
    private ImageIcon passwordIcon = new ImageIcon("PasswordIcon.png");//Create the username image

    private Clip buttonClip; //create Clip object
    private AudioInputStream audioInputStream; //create AudioInputStream object

    /**
     * This constructor of the class will allow for other classes to instantiate an object of this class. This
     * will be used in the LaunchGame class when first launching the game, as it redirects the user to the
     * login page.
     *
     * Apart from that, this constructor will also hold the necessary logic and operations needed in order to
     * create an effective and comprehensive login page for the user.
     */
    LoginPage() {
        //Layered pane configuration
        layeredPane.setBounds(0, 0, 960, 540); //Set size and location of the layered pane

        //Background label configuration
        backgroundLabel.setIcon(backgroundIcon); //Add the background image to the background label
        backgroundLabel.setBounds(0, 0, 960, 540); //Set the size of the label and location

        //Welcome title label configuration
        titleLabel.setIcon(titleIcon); //Add image to the icon
        titleLabel.setBounds(60, 25, 500, 194); //Set the size of the label and location

        //loginLabel label configuration
        loginLabel.setOpaque(true); //Set the label visible to the user
        loginLabel.setBackground(new Color(0, 0, 0, 0)); //Set the label background as opaque
        loginLabel.setBounds(225, 225, 350, 175); //Set the size of the label

        //Login button configuration
        loginButton.setBounds(0, 125, 125, 40); //Set the size and location of the button
        loginButton.setFocusable(false); //Set the button as non-focusable
        loginButton.addActionListener(this); //Add an action listener to the button
        loginButton.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        loginButton.setBackground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        loginLabel.add(loginButton); //Add the login button to the label

        //Register button configuration
        registerButton.setBounds(200, 125, 150, 40); //Set the size and location of the button
        registerButton.setFocusable(false); //Set the button as non-focusable
        registerButton.addActionListener(this); //Add an action listener to the button
        registerButton.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        registerButton.setBackground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        loginLabel.add(registerButton); //Add the register button to the label

        //Username text field configuration
        usernameText.setBounds(0, 0, 350, 40); //Set size and location of text field
        usernameText.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        loginLabel.add(usernameText); //Add the username text field to the label

        //Password text field configuration
        passwordText.setBounds(0, 50, 350, 40); //Set size and location of text field
        passwordText.setFont(new Font("Arial", Font.PLAIN, 20)); //Set font and font size
        loginLabel.add(passwordText); //Add the password text field to the label

        //Username text label configuration
        usernameLabel.setBounds(60, 215, 150, 50); //Set size and location of text label
        usernameLabel.setIcon(usernameIcon); //Add username icon to username label

        //Password text label configuration
        passwordLabel.setBounds(60, 270, 150, 50); //Set size and location of text label
        passwordLabel.setIcon(passwordIcon); //Add password icon to password label

        //Frame configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the program when frame is closed
        this.setSize(960, 540); //Create size of the frame
        this.setResizable(false); //Do not let user resize the frame
        this.setLayout(null); //Turn of the built-in layout manager
        this.setTitle("Battleship Login"); //Sets title of frame

        //Components added to the frame
        layeredPane.add(backgroundLabel, Integer.valueOf(0)); //Set the background label as the furthest back
        layeredPane.add(titleLabel, Integer.valueOf(1)); //Set the title label at position 1
        layeredPane.add(loginLabel, Integer.valueOf(2)); //Set the welcome label at position 2
        layeredPane.add(usernameLabel, Integer.valueOf(3)); //Set the username label at position 3
        layeredPane.add(passwordLabel, Integer.valueOf(4)); //Set the password label as the furthest forward
        this.add(layeredPane); //Add the layered pane to the frame
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true); //Set the frame visible to user
    }

    
    public void playButton() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //set AudioInputStream to the audio file
        audioInputStream = AudioSystem.getAudioInputStream(new File("buttonsound.wav"));

        // create clip reference
        buttonClip = AudioSystem.getClip();

        // open audioInputStream to the clip
        buttonClip.open(audioInputStream);

        buttonClip.start(); //start the audio clip
    }

    /**
     * This method is the actionPerformed() method that indicates the procedures and steps to take after
     * a certain button is pressed.
     *
     * If the login button is pressed, then search the database for the credentials. If it is found, then
     * the user may proceed with the actual game. If not, then tell the user to try again
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Retrieve the username and password the user has entered into the text boxes when they pressed the login button
        String username = usernameText.getText();
        String password = passwordText.getText();

        if (event.getSource() == loginButton) {
            
            try {
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // check if credentials are empty
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Login Failed, Credentials Cannot be Empty", "Failed", JOptionPane.INFORMATION_MESSAGE);
            } 
            
            else {
                // use a try-catch method to handle IOExceptions when retrieving data from the database file
                try {
                    // call the checkValidity() method to see whether the entered credentials are valid or not
                    if (checkValidity(username, password)) {
                        // If the credentials are valid, then the user may proceed to the actual game
                        int result = JOptionPane.showConfirmDialog(null, "Login Successful, Press OK To Proceed", "Success", JOptionPane.OK_CANCEL_OPTION);

                        // Check is the user pressed "OK" in the JOptionPane before playing, then create object of menu
                        if (result == JOptionPane.OK_OPTION) {
                            appendID(username, password);
                            this.dispose();
                            Menu menu = new Menu();
                        }
                    } 
                    
                    else {
                        // Otherwise, inform the user they entered invalid credentials and let them try again
                        JOptionPane.showMessageDialog(null, "Login Failed, No Such Name Exists.", "Failed", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception exception) {
                    exception.printStackTrace(); // If an error occurred, then print the error
                }
            }
        } 
        
        else if (event.getSource() == registerButton) {
            
            //play the button sound
            try {
                playButton();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Check if the password or the username is empty
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Register Failed, Credentials Cannot Be Empty", "Failed", JOptionPane.INFORMATION_MESSAGE);
            } 
            
            else {
                // use a try-catch method to handle IOExceptions when retrieving data from the database file
                try {
                    // call the checkValidity() method to see whether the entered credentials are valid or not
                    if (checkValidity(username, password)) {
                        // If the name is found, then the registration failed since the credentials already exist
                        JOptionPane.showMessageDialog(null, "Register Failed, This User Already Exists.", "Failed", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Otherwise, call the appendDatabase() method to store the credentials in the database
                        appendDatabase(username, password);
                        JOptionPane.showMessageDialog(null, "Register Successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace(); // If an error occurred, then print the error
                }
            }
        }

        // Reset both the user text and password text after this operation for better integrity
        usernameText.setText("");
        passwordText.setText("");
    }

    /**
     * This method will search the database using a Scanner to check whether a certain username and password
     * combination exists. If it does, then it will return true, else return false.
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    boolean checkValidity(String username, String password) throws Exception {
        Scanner readDatabase = new Scanner(database); // Declare scanner to read file

        // read the contents of the file
        while (readDatabase.hasNextLine()) {
            String currentUsername = readDatabase.nextLine();
            String currentPassword = readDatabase.nextLine();
            readDatabase.nextLine(); // to account for line breaks

            // If the current pair of username and password equals the one given in parameters, then it is found - return true
            if (currentUsername.equals(username) && currentPassword.equals(password)) {
                return true;
            }
        }

        // If the given credentials is not found in the database, then return false
        return false;
    }

    /**
     * This method will append credentials in the database using PrintWriter to append to a file
     * Furthermore, it will also append to the statistics database
     * @param username
     * @param password
     * @throws Exception
     */
    void appendDatabase(String username, String password) throws Exception {
        // Initialize a PrintWriter object to append to the file
        PrintWriter writeDatabase = new PrintWriter(new FileWriter(database, true));
        PrintWriter writeStatistics = new PrintWriter(new FileWriter(statistics, true));

        // Write the name and password on separate lines, and then insert a linebreak
        writeDatabase.println(username);
        writeDatabase.println(password);
        writeDatabase.println();
        writeDatabase.close();

        for (int i = 0; i < 5; i++) writeStatistics.println(0);
        writeStatistics.println();
        writeStatistics.close();
    }

    /**
     * This method will append the ID of the current logged-in user to another file to be used in profile page
     * @param username
     * @param password
     */
    void appendID(String username, String password) throws Exception {
        PrintWriter writeUserID = new PrintWriter(userID); // write to UserID file
        Scanner readDatabase = new Scanner(database); // Declare scanner to read database file
        int indexUser = 0;

        // check which index the user is in the database
        while (readDatabase.hasNextLine()) {
            String currentUser = readDatabase.nextLine();
            String currentPassword = readDatabase.nextLine();
            readDatabase.nextLine();

            // If user is found, then write the indices on the UserID file
            if (currentUser.equals(username) && currentPassword.equals(password)) {
                writeUserID.println(indexUser);
                writeUserID.close();
                break;
            }

            ++indexUser;
        }
    }
}
