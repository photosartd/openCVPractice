package swing;

import javax.swing.*;
import java.awt.*;

public class SwingFlowLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("New form");

        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Set layout
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.GREEN);
        panel.setPreferredSize(new Dimension(600, 100));

        JButton button = new JButton("The button");
        JTextField textField = new JTextField(10);

        panel.add(button);
        panel.add(textField);

        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
