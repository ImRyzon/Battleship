/* Name: BCSC
 * Date: January 16th, 2023
 * File Name: LoginPage.java
 * Description: Create an introductory frame that will introduce the application and provide interface
 * for a user to enter their name and password and an option pane window that will give the user feedback
 * if they have logged in properly or not.
 */

//Imports all needed GUI classes
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*; //Imports java file input and output
import java.util.Scanner; //Imports Scanner

public class LoginPage extends JFrame implements ActionListener { //Make JFrame and add an action listener
    JLayeredPane layeredPane = new JLayeredPane(); //Creates layered pane for Login page

    JLabel backgroundLabel = new JLabel(); //Creates the label for the background image
    JLabel titleLabel = new JLabel(); //Creates the label for the title of the game
    JLabel welcomeLabel = new JLabel(); //Creates the label for the login text fields and buttons
    JLabel usernameLabel = new JLabel("Username"); //Creates label to display the word "username"
    JLabel passwordLabel = new JLabel("Password"); //Creates label to display the word "password"

    JButton loginButton = new JButton("Login"); //Creates the login button
    JButton registerButton = new JButton("Register"); //Creates the register button

    JTextField usernameText = new JTextField();  //Create password field for user to enter their username
    JPasswordField passwordText = new JPasswordField(); //Create password field for user to enter their password

    static File accounts = new File("src/ics4u/gui/BreakThePlates/accounts.txt");
    //Creates new file to store username and password

    ImageIcon gameIcon = new ImageIcon("src/ics4u/gui/BreakThePlates/gameIcon.png");
    //Create the game icon image
    ImageIcon backgroundIcon = new ImageIcon("src/ics4u/gui/BreakThePlates/welcomeBackground.png");
    //Create the game background image
    ImageIcon titleIcon = new ImageIcon("src/ics4u/gui/BreakThePlates/titleIcon.png");
    //Create the game title image
    ImageIcon usernameIcon = new ImageIcon("src/ics4u/gui/BreakThePlates/usernameIcon.png");
    //Create the username image
    ImageIcon passwordIcon = new ImageIcon("src/ics4u/gui/BreakThePlates/passwordIcon.png");
    //Create the username image

    LoginPage() {
        //Layered pane configuration
        layeredPane.setBounds(0, 0, 1000, 500); //Set size and location of the layered pane

        //Background label configuration
        backgroundLabel.setIcon(backgroundIcon); //Add the background image to the background label
        backgroundLabel.setBounds(0, 0, 1000, 500); //Set the size of the label and location

        //Welcome title label configuration
        titleLabel.setIcon(titleIcon); //Add image to the icon
        titleLabel.setBounds(200, 50, 600, 125); //Set the size of the label and location

        //Welcome label configuration
        welcomeLabel.setOpaque(true); //Set the label visible to the user
        welcomeLabel.setBackground(new Color(0, 0, 0, 0)); //Set the label background as opaque
        welcomeLabel.setBounds(400, 225, 350, 175); //Set the size of the label

        //Login button configuration
        loginButton.setBounds(0, 125, 125, 40); //Set the size and location of the button
        loginButton.setFocusable(false); //Set the button as non-focusable
        loginButton.addActionListener(this); //Add an action listener to the button
        loginButton.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        welcomeLabel.add(loginButton); //Add the login button to the label

        //Register button configuration
        registerButton.setBounds(200, 125, 150, 40); //Set the size and location of the button
        registerButton.setFocusable(false); //Set the button as non-focusable
        registerButton.addActionListener(this); //Add an action listener to the button
        registerButton.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        welcomeLabel.add(registerButton); //Add the register button to the label

        //Username text field configuration
        usernameText.setBounds(0, 0, 350, 40); //Set size and location of text field
        usernameText.setFont(new Font("Monospaced", Font.BOLD, 20)); //Set font and font size
        welcomeLabel.add(usernameText); //Add the username text field to the label

        //Password text field configuration
        passwordText.setBounds(0, 50, 350, 40); //Set size and location of text field
        passwordText.setFont(new Font("Arial", Font.PLAIN, 20)); //Set font and font size
        welcomeLabel.add(passwordText); //Add the password text field to the label

        //Username text label configuration
        usernameLabel.setBounds(285, 225, 100, 40); //Set size and location of text label
        usernameLabel.setIcon(usernameIcon); //Add username icon to username label

        //Password text label configuration
        passwordLabel.setBounds(285, 275, 100, 40); //Set size and location of text label
        passwordLabel.setIcon(passwordIcon); //Add password icon to password label

        //Frame configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the program when frame is closed
        this.setSize(1000, 500); //Create size of the frame
        this.setResizable(false); //Do not let user resize the frame
        this.setLayout(null); //Turn of the built-in layout manager
        this.setTitle("Break The Plates"); //Sets title of frame

        //Components added to the frame
        layeredPane.add(backgroundLabel, Integer.valueOf(0)); //Set the background label as the furthest back
        layeredPane.add(titleLabel, Integer.valueOf(1)); //Set the title label at position 1
        layeredPane.add(welcomeLabel, Integer.valueOf(2)); //Set the welcome label at position 2
        layeredPane.add(usernameLabel, Integer.valueOf(3)); //Set the username label at position 3
        layeredPane.add(passwordLabel, Integer.valueOf(4)); //Set the password label as the furthest forward
        this.add(layeredPane); //Add the layered pane to the frame
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true); //Set the frame visible to user
    }

    //Method to check if buttons were pressed and preform actions based on what was input
    @Override
    public void actionPerformed(ActionEvent e) {
        //Try and catch with the print writer
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(accounts, true));
            //Create output to print to the username and password file
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Store the user inputs
        String username = usernameText.getText(); //Store the username input
        String password = passwordText.getText(); //Store the password output

        //Button inputs
        if (e.getSource() == loginButton) { //If user clicks on login button
            //Try and catch to see if cridentials are correct
            try {
                if(loginCredentials(username, password)){ //Check if cridentials are correct
                    this.dispose(); //Dispose the current frame
                    new HomePage(); //Create new frame for the home page
                    //Create option pane to tell user the login is successful
                    JOptionPane.showMessageDialog(null,
                            "Hi " + username + ", you have successfully logged in",
                            //Sets the message user will see on option pane
                            "Login Successful",  //Set option pane title
                            JOptionPane.INFORMATION_MESSAGE); //Use information message template
                }
                else{ //If the login is unsuccessful
                    //Create option pane to tell user the cridentials are incorrect
                    JOptionPane.showMessageDialog(null,
                            "The input username or password is incorrect",
                            //Sets the message user will see on option pane
                            "Login Unsuccessful", //Set option pane title
                            JOptionPane.ERROR_MESSAGE); //Use error message template
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == registerButton) { //If user clicks on register button
            //Try and catch to see if account exists
            try {
                if (accountExists(username, password)) { //Check if account already exists
                    if (output != null) { //Make sure file doesn't try to use null in a case where an object is required
                        output.println(username + ";" + password); //Add username and password to account file
                        output.close(); //Close the output to update the file with username with password
                    }
                    //Create option pane to tell user the registration is successful
                    JOptionPane.showMessageDialog(null,
                            "Hi " + username + ", your registration has been successful. You can now login",
                            //Sets the message user will see on option pane
                            "Registration Successful",  //Set option pane title
                            JOptionPane.INFORMATION_MESSAGE); //Use information message template
                }
                else { //If the account has already been taken
                    //Create option pane to tell user the username has been taken
                    JOptionPane.showMessageDialog(null,
                            "Sorry, that username or password is invalid",
                            //Sets the message user will see on option pane
                            "Registration Unsuccessful", //Set option pane title
                            JOptionPane.ERROR_MESSAGE); //Use error message template
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        usernameText.setText(""); //Remove existing text from username text field
        passwordText.setText(""); //Remove existing text from password text field
    }

    //Method to check if the user has entered the correct credentials
    public boolean loginCredentials(String username, String password) throws Exception{ //Throw exception for scanner
        Scanner input = new Scanner(accounts); //Create scanner to read username and passwords in the file
        while(input.hasNextLine()){ //While the file still has usernames and passwords
            String[] credentials = input.nextLine().split(";"); //Store the username and passwords and split
            if(credentials[0].equalsIgnoreCase(username) && credentials[1].equals(password)){
                //Check if username and password is equal to account database
                input.close(); //Close the scanner input
                return true; //Username already exists
            }
        }
        input.close(); //Close the scanner input
        return false; //Username does not exist yet
    }

    //Method to check if the username already exists in the account file
    public boolean accountExists(String username, String password) throws Exception{ //Throw exception for scanner
        if(username.equals("") || password.equals("")){ //If the username or password is empty
            return false; //Username or password is empty
        }
        if(username.contains(";") || password.contains(";")){ //Check if username has a semicolon
            return false; //Return that the password is invalid
        }
        Scanner input = new Scanner(accounts); //Create scanner to read username and passwords in the file
        while(input.hasNextLine()){ //While the file still has usernames and passwords
            String[] credentials = input.nextLine().split(";"); //Store the username and passwords and split
            if(credentials[0].equalsIgnoreCase(username)){ //Check if username is equal to account database
                input.close(); //Close the scanner input
                return false; //Username already exists
            }
        }
        input.close(); //Close the scanner input
        return true; //Username does not exist yet
    }
}
