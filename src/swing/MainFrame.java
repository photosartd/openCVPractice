package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2269971701250845501L;

    private String title;
    private Dimension d;

    private int buttonPressedCounter = 0;

    private JTextField textField1 = new JTextField(10);
    private JTextField textField2 = new JTextField(10);
    private JButton MyButton = new JButton("Button");
    private JLabel label = new JLabel(String.valueOf(buttonPressedCounter));

    public MainFrame(String title, Dimension d) {
        this.title = title;
        this.d = d;
    }

    public void init() {
        setTitle(title);
        setSize(d);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Добавление слушателя
        MyButton.addActionListener(new MyButtonActionListener());

        add(textField1, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1),
                0,0));
        add(textField2, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1),
                0,0));
        add(MyButton, new GridBagConstraints(0, 1, 2, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1),
                0,0));
        add(label, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1),
                0,0));

        setVisible(true);
        pack();
    }

    public class MyButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String temp = textField1.getText();
            textField1.setText(textField2.getText());
            textField2.setText(temp);
            buttonPressedCounter++;
            label.setText(String.valueOf(buttonPressedCounter));
        }
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("ActionListener", new Dimension(200,200));
        frame.init();
    }
}
