package swing;

import javax.swing.*;
import java.awt.*;

public class SwingGridLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("New form");

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Set layout
        frame.setLayout(new GridLayout(3,3));

        JButton b1 = new JButton("b1");
        JButton b2 = new JButton("b2");
        JButton b3 = new JButton("b3");
        JButton b4 = new JButton("b4");
        JButton b5 = new JButton("b5");

        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);
        frame.add(b5);



        frame.setVisible(true);
    }
}
