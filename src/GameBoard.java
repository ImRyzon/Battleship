import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class GameBoard extends JFrame implements ActionListener {

    JButton[][] attackGrid = new JButton[11][11];
    JPanel attackPanel = new JPanel();
    JButton[][] defenseGrid = new JButton[11][11];
    JPanel defensePanel = new JPanel();
    ImageIcon gameIcon = new ImageIcon("GameIcon.png");//Create the game icon image

    GameBoard() {
        attackPanel.setLayout(new GridLayout(11, 11, 0, 0));
        attackPanel.setBounds(10, 10, 650, 650);
        attackPanel.setBackground(new Color(52, 152, 235));

        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                attackGrid[x][y] = new JButton();
                attackGrid[x][y].setFocusable(false);
                attackGrid[x][y].addActionListener(this);
                attackGrid[x][y].setBackground(Color.BLACK);
                attackPanel.add(attackGrid[x][y]);
                if(x == 0){
                    attackGrid[x][y].setEnabled(false);
                    if(y != 0){
                        attackGrid[x][y].setText(String.valueOf(y));
                    }
                }
                if(y == 0){
                    attackGrid[x][y].setEnabled(false);
                }
            }
        }

        defensePanel.setLayout(new GridLayout(11, 11, 2, 2));
        defensePanel.setBounds(700, 10, 300, 300);
        defensePanel.setBackground(new Color(52, 152, 235));

        for(int y = 0; y < 11; y++) {
            for(int x = 0; x < 11; x++) {
                defenseGrid[x][y] = new JButton();
                defenseGrid[x][y].setFocusable(false);
                defenseGrid[x][y].addActionListener(this);
                defenseGrid[x][y].setBackground(Color.BLACK);
                defensePanel.add(defenseGrid[x][y]);
                if(x == 0){
                    defenseGrid[x][y].setEnabled(false);
                    if(y != 0){
                        defenseGrid[x][y].setText(String.valueOf(y));
                    }
                }
                if(y == 0){
                    defenseGrid[x][y].setEnabled(false);
                }
            }
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040, 700);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setTitle("Game Board");
        this.setResizable(false);

        this.add(attackPanel);
        this.add(defensePanel);
        this.setIconImage(gameIcon.getImage()); //Set the game icon image
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }
}