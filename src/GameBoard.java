import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class GameBoard extends JFrame implements ActionListener {


    JButton[][] attackGrid = new JButton[10][10];
    JPanel buttonPanel = new JPanel();

    GameBoard() {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setTitle("Game Board");
        this.setVisible(true);
        this.setResizable(false);

        buttonPanel.setLayout(new GridLayout(10, 10, 1, 1));
        buttonPanel.setBounds(10, 10, 650, 650);
        buttonPanel.setBackground(new Color(25, 255, 0));

        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                attackGrid[x][y] = new JButton();
                attackGrid[x][y].setFocusable(false);
                attackGrid[x][y].addActionListener(this);
                attackGrid[x][y].setBackground(Color.BLACK);
                buttonPanel.add(attackGrid[x][y]);
            }
        }

        this.add(buttonPanel);
    }


    @Override
    public void actionPerformed(ActionEvent event) {

    }
}