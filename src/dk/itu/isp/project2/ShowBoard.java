/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.isp.project2;

import javax.swing.*;

public class ShowBoard {

    public static void main(String[] arg) {
        QueensLogic l = new QueensLogic();
        int size = 5;
        if (arg.length == 1) {
            try {
                size = Integer.valueOf(arg[0]);
            } catch (Exception e) {
                System.out.println("Default game board config: 5 * 5");
            }
        }
        l.initializeGame(size);

        QueensGUI g = new QueensGUI(l);

        // Setup of the frame containing the game
        JFrame f = new JFrame();
        f.setSize(200 + size * 100, 200 + size * 100);
        f.setTitle(size + " Queens Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(g);
        f.setVisible(true);
    }
}
