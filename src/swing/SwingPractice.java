package swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Arrays;

public class SwingPractice {

    public static JLabel label() {
        //Создание меток
        JLabel label = new JLabel("Information");

        Font font = new Font("Verdana", Font.ITALIC, 25);

        label.setFont(font);
        label.setForeground(Color.BLUE);
        return label;
    }

    public static JButton button(String name) {
        JButton myButton = new JButton(name);
        //Set background
        myButton.setBackground(Color.yellow);
        //Set font color
        myButton.setForeground(Color.blue);
        //Set cursor
        Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
        myButton.setCursor(cursor);

        return myButton;
    }

    public static JTextField textField(String text, boolean editable) {
        JTextField textField = new JTextField(text);
        //Можно ли будет изменять текст внутри
        textField.setEditable(true);
        return textField;
    }

    public static JProgressBar progressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        //Отображение процентажа
        progressBar.setStringPainted(true);
        //Бегает ползунок
        //progressBar.setIndeterminate(true);
        for (int i = progressBar.getMinimum();
        i <= progressBar.getMaximum(); i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressBar.setValue(i);
        }

        return progressBar;
    }

    public static JSlider slider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("This is title form");
        //размер формы
        frame.setSize(100, 200);
        //Указание размера через DImension
        frame.setSize(new Dimension(100,200));
        //Закрытие формы при нажатии на крестик
        //Если мы ничего не укажем, то форма закроется
        //А программа продолжит работать
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Устанавливает относительное расположение формы
        //Сюда можно указать в параметры другую форму
        //Тогда она откроется посередине другой формы
        frame.setLocationRelativeTo(null);

        //Метод add добавляет какие-то компоненты
        //Методы set устанавливают что-либо на форме
        frame.setResizable(true);
        //Название формы
        //frame.setTitle("This is title form");

        //Методы get возвращают разные параметры

        //Добавляем layout
        frame.setLayout(new GridBagLayout());




        //Добавляем метку на фрейм
        frame.add(SwingPractice.label());
        //Добавляем кнопку на фрейм
        frame.add(SwingPractice.button("First button"));
        frame.add(SwingPractice.button("Second button"));
        //Добавляем JTextField на фрейм
        frame.add(SwingPractice.textField("Text field", true));


        //Создание панели
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setBackground(Color.YELLOW);
        panel2.setBackground(Color.GREEN);
        //Создание кнопки для панели
        JButton panelButton = new JButton("Panel button");
        panel.add(panelButton);
        panel.add(panel2);

        frame.add(panel);

        //Перебор компонентов на панели
        Component[] components = panel.getComponents();
        Arrays.stream(components).
                forEach(comp -> {
                    if (comp instanceof JPanel)
                        System.out.println("panel");
                    if (comp instanceof JButton)
                        System.out.println("button");
                });

        JSlider slider = slider();
        frame.add(slider);

        JLabel sliderLabel = new JLabel("Current value: 0");
        frame.add(sliderLabel);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = slider.getValue();
                sliderLabel.setText("Current value: " + value);
            }
        });

        //Отображение формы
        frame.setVisible(true);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        //Отображение процентажа
        progressBar.setStringPainted(true);

        //Add JProgressBar
        frame.add(progressBar);

        //Fullfill progressBar
        for (int i = progressBar.getMinimum();
             i <= progressBar.getMaximum(); i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressBar.setValue(i);
        }


        //Устанавливает значение по умолчанию
        //ICONIFIED - свернутая
        //Thread.sleep(3000);
        //frame.setState(JFrame.ICONIFIED);
        //Thread.sleep(3000);
        //Разворачивание формы на максимальный размер
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Thread.sleep(3000);
        //frame.setExtendedState(JFrame.NORMAL);
    }
}
