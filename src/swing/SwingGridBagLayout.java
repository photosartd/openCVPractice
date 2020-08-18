package swing;

import javax.swing.*;
import java.awt.*;

public class SwingGridBagLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Autorisation");

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Set layout
        frame.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password:");

        JButton loginButton = new JButton("Sign in");
        JButton registratonButton = new JButton("Sign up");

        JTextField loginTextField = new JTextField(15);
        JPasswordField passwordPasswordField = new JPasswordField(15);

        /*GridBagConstraints c = new GridBagConstraints();*/

        /*//Указывает расположение элемента в сетке
        c.gridx = 0;
        c.gridy = 0;
        //Указывает, сколько позиций занимает элеент в сетке
        c.gridwidth = 1;
        c.gridheight = 1;
        //Как будет происходить изменение расзмера компонента
        c.weightx = 0.0;
        c.weighty = 0.9;
        //Якорь
        c.anchor = GridBagConstraints.NORTH;
        //Как будет распологаться
        c.fill = GridBagConstraints.HORIZONTAL;
        //Отступ от других элементов по 4 направлениям
        var insets = new Insets(20,2,2,2);
        //Отступ
        c.insets = insets;
        //Насколько будет увеличен минимальный размер компонента
        c.ipadx = 0;
        c.ipady = 0;*/


        frame.add(loginLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));
        frame.add(loginTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));
        frame.add(passwordLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));
        frame.add(passwordPasswordField, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));
        frame.add(loginButton, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));
        frame.add(registratonButton, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0));

        frame.setVisible(true);
        //Метод убирает всё лишнее пространство
        frame.pack();
    }
}
