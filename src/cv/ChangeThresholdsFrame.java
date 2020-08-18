package cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ChangeThresholdsFrame extends JFrame {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private String filepath = "Resources\\board.jpg";
    private Mat img;
    private Mat imgGray = new Mat();

    JSlider thresh1;
    JSlider thresh2;
    JLabel lab1;
    JLabel lab2;

    private JScrollPane panel;
    private JLabel imgLabel;

    public void init() {
        setTitle(filepath);
        setSize(1000,1000);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Get img
        img = Imgcodecs.imread(filepath);
        if (img.empty()) {
            System.out.println("Couldnt open the image");
            return;
        }
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);

        thresh1 = setUpSlider(0, 255);
        thresh2 = setUpSlider(0, 255);
        lab1 = addSliderToFrame(thresh1);
        lab2 = addSliderToFrame(thresh2);
        thresh1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value1 = thresh1.getValue();
                lab1.setText("Value: " + value1);
                int value2 = thresh2.getValue();
                changeThresholds(value1, value2);
            }
        });
        thresh2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value2 = thresh2.getValue();
                lab2.setText("Value: " + value2);
                int value1 = thresh1.getValue();
                changeThresholds(value1, value2);
            }
        });

        //add sliders to img
        //addSliderToFrame( 200);

        //JScrollPane
        panel = new JScrollPane();
        imgLabel = new JLabel(new ImageIcon(CvUtils.MatToBufferedImage(img)));
        panel.setViewportView(imgLabel);
        add(panel);



        setVisible(true);
        pack();
    }



    private void changeThresholds(int min, int max) {
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, min, max);
        imgLabel.setIcon(new ImageIcon(CvUtils.MatToBufferedImage(edges)));
    }

    private void addImgToFrame(JScrollPane panel, Mat img) {
        JLabel lbl = new JLabel();
        lbl.setSize(img.width(), img.height());
        lbl.setIcon(new ImageIcon(CvUtils.MatToBufferedImage(img)));
        panel.add(lbl);
        this.pack();
    }

    private JSlider setUpSlider(int min, int max) {
        if (min < 0)
            throw new IllegalArgumentException("Min value cant be less than 0");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, 0);
        slider.setMajorTickSpacing((max-min)/10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel addSliderToFrame(JSlider slider) {
        JLabel label = new JLabel("Value: " + slider.getValue());
        this.add(slider);
        this.add(label);
        this.pack();
        return label;
    }

    private void addSliderToFrame(int max) {
        //Slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, max, 0);
        slider.setMajorTickSpacing(max/10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        //label
        JLabel label = new JLabel("Value: " + slider.getValue());
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = slider.getValue();
                label.setText("Value: " + value);
            }
        });
        this.add(slider);
        this.add(label);
        this.pack();
    }

    public static void main(String[] args) {
        ChangeThresholdsFrame frame = new ChangeThresholdsFrame();
        frame.init();
    }
}
