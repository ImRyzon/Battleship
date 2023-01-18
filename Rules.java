/**
 * @author Mark Wang
 * 2022-12-13
 *
 * This class will display the rules of Break A Plate
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rules extends JFrame implements ActionListener {

    /**
     * These are the necessary objects that help make the Rules page function properly
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the rules page
     * title --> The title of this page, which is "Rules"
     * rules --> the array containing all the rules to be outputted
     * backToMenu --> the button that redirects the user back to the menu screen
     * rules --> the image icon used for this page
     */
    JPanel panel = new JPanel();
    JLabel title = new JLabel("Rules");
    JLabel[] rules = new JLabel[6];
    JButton backToMenu = new JButton("Menu");
    ImageIcon rulesIcon = new ImageIcon("rules.png");

    /**
     * This constructor will allow other classes to instantiate an object of the menu class
     * as well as implement the necessary logic to make the menu function as intended
     */
    Rules() {
        // Set the frame size, close operation, visibility, title, background, icon, and add the panel
        this.setSize(525, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Rules");
        this.setBackground(Color.WHITE);
        this.setIconImage(rulesIcon.getImage());

        // Set the mode of the panel to absolute positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(255, 204, 203));

        // Customize the title text field and add it to the panel
        title.setBounds(215, 10, 420, 50);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 203), 0));
        title.setBackground(new Color(255, 204, 203));
        title.setVisible(true);
        panel.add(title);

        // Set and customize the rules array, then add it to the panel
        rules[0] = new JLabel("The objective is to break as many plates as possible\n");
        rules[1] = new JLabel("1. A random amount of plates will be broken\n");
        rules[2] = new JLabel("2. If all plates are broken, you win a Tiger Plush\n");
        rules[3] = new JLabel("3. If two plates are broken, you win a sticker\n");
        rules[4] = new JLabel("4. If one plate is broken, you win a tattoo\n");
        rules[5] = new JLabel("5. If none of the plates are broken, you lose\n");

        int y = 95;
        for (int i = 0; i < 6; i++, y += 50) {
            rules[i].setBounds(50, y, 450, 55);
            rules[i].setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            rules[i].setBorder(BorderFactory.createLineBorder(new Color(255, 204, 203), 0));
            rules[i].setBackground(new Color(255, 204, 203));
            rules[i].setVisible(true);
            panel.add(rules[i]);
        }

        // Customize the menu button and add it to the panel
        backToMenu.setText("Menu");
        backToMenu.setBounds(195, 425, 100, 50);
        backToMenu.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        backToMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        backToMenu.setBackground(Color.WHITE);
        backToMenu.addActionListener(this);
        panel.add(backToMenu);

        // Make the panel visible to the user
        panel.setVisible(true);
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
