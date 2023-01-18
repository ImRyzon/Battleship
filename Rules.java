import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Rules implements ActionListener {

    JFrame frame = new JFrame();

    JPanel titlePanel = new JPanel();

    JLabel titleText = new JLabel();

    JLabel rulesText = new JLabel();

    JButton menuButton = new JButton("Menu");

    Rules() throws Exception {

        //setting up and customizing the JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("Menu");
        //frame.setResizable(false);

        //setting up and customizing the playButton object
        menuButton.setFocusable(false); //set the focusable to false
        menuButton.setPreferredSize(new Dimension(250, 60)); //set preferred dimensions
        menuButton.setFont(new Font("MV Boli", Font.BOLD, 40)); //sets the text font and text size
        menuButton.addActionListener(this); //adding an ActionListener
        menuButton.setBackground(Color.WHITE); //set the color of the button
        menuButton.setForeground(Color.BLACK); //set the color of the text
        menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); //sets the border for the button

        titleText.setText("Rules");
        titleText.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        titleText.setForeground(Color.BLACK);
        titleText.setBounds(0, 0, 100, 50);

        rulesText.setText("1. Placeholder\n2. Placeholder\n3. Placeholder");
        rulesText.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        rulesText.setForeground(Color.BLACK);
        rulesText.setBounds(0, 0, 100, 20);

        //changing the color of the 2 panel
        titlePanel.setBackground(Color.GRAY);

        //adding the components to the titlePanel object
        titlePanel.add(titleText);

        //adding the panels to the frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(rulesText, BorderLayout.CENTER);
        frame.add(menuButton, BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //if menuButton is clicked
        if(e.getSource() == menuButton) {
            frame.dispose(); //delete the current frame

            //use try and catch to instantiate an object for Game class
            try {
                Menu menu = new Menu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }    
}