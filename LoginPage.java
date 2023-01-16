import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginPage implements ActionListener {

    /**
     * frame: the window for the login page, all the other components are added here
     *
     * loginButton: a JButton that logs the user in if the credentials are valid. It has an ActionListener on it
     * and when clicked, calls the action performed method
     *
     * registerButton: a JButton that registers a new account with a unique username and password. It has an ActionListener
     * on it and when clicked, calls the action performed method
     *
     * textPanel: a container used to store the userLabel, passwordLabel, userText, and passwordText. Added to the frame
     *
     * buttonPanel: a container used to store the all the buttons. Added to the frame
     *
     * titlePanel: a container used to store the title components. Added to the frame
     *
     * userLabel: a JLabel object with the text "User". Added to the panel
     *
     * passwordLabel: a JLabel object with the text "Password". Added to the panel
     *
     * userText: a JTextField object used for user to enter their username. Added to the panel
     *
     * passwordText: a JPasswordField object used for user to enter their password. Added to the panel
     *
     * titleText: a JLabel object that displays a title
     *
     * database: a text file used to store the credentials of registered users, the object is static as it is used in methods
     *
     * currentUser: a text file used to store the name of the current user for later uses
     *
     * output: a PrintWriter object used to append user info to the database to store them
     *
     * output2: a PrintWriter object used to write the current logged in username to the currentUser file
     *
     * icon: an ImageIcon object used to paint icons from images, in this case, from login.jpg
     */

    JFrame frame = new JFrame();

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    JPanel textPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel titlePanel = new JPanel();

    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userText = new JTextField();
    JPasswordField passwordText = new JPasswordField();

    JLabel titleText = new JLabel();

    static File database = new File("database.txt");
    static File currentUser = new File("currentUser.txt");
    PrintWriter output = new PrintWriter(new FileWriter(database, true));
    PrintWriter output2 = new PrintWriter(currentUser);

    ImageIcon icon = new ImageIcon("login.jpg");

    //constructor
    LoginPage() throws Exception {

        //customizing and setting up the JFrame object
        frame.setSize(750, 350); //setting the size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //configuring it so when the window is closed, the program stops running
        frame.setTitle("Login Page"); //setting the title to "Login Page"
        frame.setResizable(false); //configuring it so it cannot be resized
        frame.setIconImage(icon.getImage()); //sets the icon of the frame to be icon
        frame.setLayout(new BorderLayout());

        //configuring the titlePanel
        titlePanel.setBackground(Color.DARK_GRAY); //changes the background color

        //configuring the textPanel
        textPanel.setLayout(null); //setting the panel layout to null
        textPanel.setBackground(Color.GRAY); //changes the background color

        //configuring the buttonPanel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10)); //setting the panel layout to FlowLayout
        buttonPanel.setBackground(Color.GRAY); //changes the background color

        //configuring the loginButton
        loginButton.setPreferredSize(new Dimension(200, 50)); //setting the location and size
        loginButton.setFocusable(false); //set the focusable to false
        loginButton.addActionListener(this); //add an ActionListener to listen on this button
        loginButton.setBackground(Color.WHITE); //set the color of the button
        loginButton.setForeground(Color.BLACK); //set the color of the text
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); //sets the border for the button
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //configuring the registerButton
        registerButton.setPreferredSize(new Dimension(200, 50)); //setting the location and size
        registerButton.setFocusable(false); //set the focusable to false
        registerButton.addActionListener(this); //add an ActionListener to listen on this button
        registerButton.setBackground(Color.WHITE); //set the color of the button
        registerButton.setForeground(Color.BLACK); //set the color of the text
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); //sets the border for the button
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 20));

        //customizing the JLabels
        userText.setBounds(370, 40, 200, 30);
        passwordText.setBounds(370, 90, 200, 30);
        userText.setFont(new Font("Times New Roman", Font.BOLD, 25));
        passwordText.setFont(new Font("Times New Roman", Font.BOLD, 25));

        //setting the location for the labels
        userLabel.setBounds(180, 40, 150, 25);
        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        userLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(180, 90, 150, 25);
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        passwordLabel.setForeground(Color.BLACK);

        //customizing the titleText object
        titleText.setText("Welcome to Smashing Plates!");
        titleText.setFont(new Font("Times New Roman", Font.BOLD, 50));
        titleText.setForeground(Color.BLACK);

        //adding the components to panel
        textPanel.add(userLabel);
        textPanel.add(userText);
        textPanel.add(passwordLabel);
        textPanel.add(passwordText);

        //adding the buttons to buttonPanel
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        //adding the title to the titlePanel
        titlePanel.add(titleText);

        //adding the panels to frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(textPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true); //set the frame to visible
    }

    /**
     * actionPerformed method will perform certain actions based on what the user did
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //declares and initializes String variables to store the username and password entered in the text fields
        String name = userText.getText();
        String password = passwordText.getText();

        //if the loginButton is clicked
        if(e.getSource() == loginButton) {

            boolean authenticated = false; //declares and initializes boolean variable to check if the user is authenticated

            //use try and catch with the checkAuthentication method in which it checks whether the user is credentials are valid
            //if they are then set authenticated to true

            //if not then set it to false
            try {
                authenticated = checkAuthentication(name, password);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            //if the user is authenticated
            if(authenticated) {

                output2.print(name); //write the name to the currentUser file
                output2.close(); //close the object so the file saves
                frame.dispose(); //delete the current frame
                JOptionPane.showMessageDialog(null, "Hi " + userText.getText() + ", you have successfully logged in.", "Login Success", JOptionPane.INFORMATION_MESSAGE); //open the JOptionPane and print out a success message
                //Game myWindow = new Game(); //Instantiate myWindow object for the Game class

                //Instantiate myWindow object for the Menu class and surrounding it with try and catch
                try {
                    Menu myWindow = new Menu();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            //if the user is not authenticated
            else {
                JOptionPane.showMessageDialog(null, "You username or password is incorrect, please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE); //open the JOptionPane and print out a failed message

                //clears the texts in the text fields
                userText.setText("");
                passwordText.setText("");
            }
        }

        //if the registerButton is clicked
        if(e.getSource() == registerButton) {

            //declare and initialize a boolean variable to store whether the name already exists
            boolean nameExist = false;

            //use try and catch with the checkAvail method in which it checks whether the username is already taken
            //if the name is taken, set the nameExist variable to true

            //if the name is not taken, set the nameExist variable to false
            try {
                nameExist = checkAvail(name);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            //if the name is taken or invalid
            if(nameExist) {
                JOptionPane.showMessageDialog(null, "Sorry, that username is already taken or is invalid, try again.", "Register Failed", JOptionPane.ERROR_MESSAGE); //use JOptionPane to print out a failed message

                //remove the texts in the text fields
                userText.setText("");
                passwordText.setText("");
            }

            //if password is invalid
            else if(passwordText.getText().equalsIgnoreCase("") || password.indexOf(":") != -1) {

                JOptionPane.showMessageDialog(null, "Sorry, that password is invalid, try again.", "Register Failed", JOptionPane.ERROR_MESSAGE); //use JOptionPane to print out a failed message

                //remove the texts in the text fields
                userText.setText("");
                passwordText.setText("");
            }

            //if the name is not taken
            else {
                output.println(userText.getText() + ":" + passwordText.getText()); //use the PrintWriter object to append the credentials to the database
                output.close(); //close the PrintWriter object to save changes

                JOptionPane.showMessageDialog(null, "Hi " + userText.getText() + ", you have successfully registered!", "Register Success", JOptionPane.INFORMATION_MESSAGE); //use JOptionPane to print out a success message

                //remove the texts in the text fields
                userText.setText("");
                passwordText.setText("");

                //make the register button unclickable as the user already registered, so they have to log in now
                registerButton.setEnabled(false);

                System.out.println("Please proceed to log in");
                System.out.println();
            }
        }
    }

    /**
     * This method checks if the String variable name in the argument is in the database file
     * Method has a scanner to read through the file using a while loop
     * @param name
     * @return boolean
     * @throws Exception
     */
    public static boolean checkAvail(String name) throws Exception {

        Scanner input = new Scanner(database); //instantiate scanner object to read the file

        //using while loop to loop through every line of the file
        while(input.hasNext()) {
            String arr[] = input.nextLine().split(":"); //split each line into 2 parts: username and password

            if(arr[0].equalsIgnoreCase(name)) { //checks if the username matches the name variable
                input.close(); //close the scanner so it starts at the beginning again
                return true; //return true because there is already this name in the database
            }
        }

        //name cannot be empty or have special character :
        if(name.equalsIgnoreCase("")) {
            return true;
        }
        else if(name.indexOf(":") != -1) {
            return true;
        }

        return false; //returns false if the username is not in the database
    }

    /**
     * This method checks if the credentials the user entered (as the arguments) are valid
     * Method has a scanner and uses while loop to loop though each line to check if the credentials the user entered are valid
     * @param name
     * @param password
     * @return boolean
     * @throws Exception
     */
    public static boolean checkAuthentication(String name, String password) throws Exception {

        Scanner input = new Scanner(database); //instantiate scanner object to read through the database

        //uses while loop to loop though each line of the file
        while(input.hasNext()) {
            String arr[] = input.nextLine().split(":"); //use array to split the line into 2 parts: username and password

            //checks if the credentials are valid
            if(arr[0].equalsIgnoreCase(name) && arr[1].equalsIgnoreCase(password)) {
                input.close(); //closing the scanner so it starts from the beginning again
                return true; //return true because the credentials are valid
            }
        }

        return false; //return false if the credentials not valid
    }
}
