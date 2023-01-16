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

    ImageIcon icon = new ImageIcon("hammer.png");

    ImageIcon titleIcon = new ImageIcon("titleimage.png");

    static File currentUser = new File("currentUser.txt");
    static Scanner input;

    static {
        try {
            input = new Scanner(currentUser);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static String name = input.nextLine();

    static File userStat = new File(name + ".txt");

    PrintWriter nullOutput = new PrintWriter(new FileWriter(userStat, true));
    BufferedReader br = new BufferedReader(new FileReader(userStat));

    //constructor
    Menu() throws Exception {

        //setting up and customizing the JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("Menu");
        frame.setIconImage(icon.getImage());
        //frame.setResizable(false);

        //set the icon as the JLabel object
        titleImage.setIcon(titleIcon);

        //setting up and customizing the titleText object
        titleText.setText("Welcome " + name + "!");
        titleText.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        titleText.setForeground(Color.BLACK);
        titleText.setBounds(0, 0, 100, 50);

        input.close(); //closing the scanner object

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

        //check if the file is empty, if it is then set the default value to 0,0
        if(br.readLine() == null) {
            nullOutput.println("0,0");
            nullOutput.close();
        }
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
                Game game = new Game();
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
            System.out.println("The rules: ");
            System.out.println("1. There are 3 plates on the table and you smash it");
            System.out.println("2. A random number of plates will be broken");
            System.out.println("3. Based on the number plates broken, you will either win money or lose money");
            System.out.println("4. 0 broken plates lose $10, 1 broken plate lose $5, 2 broken plates wins $10, 3 broken plates wins $15");
            System.out.println("5. Amount of money you have will fluctuate based on the number of plates broken, you can check the the number of plates you have broken and your balance at Profile page");
            System.out.println("6. Please click play and play again slow as java need to write the stats to a file");
            System.out.println();
        }

        //if the exit button is clicked
        if(e.getSource() == exitButton) {
            System.out.println("Goodbye, see you next time!"); //print out a message on screen

            frame.dispose(); //delete the frame
        }
    }
}