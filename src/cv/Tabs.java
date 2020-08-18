package cv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Tabs {



    private Container pane;
    private Mat img;

    private JTabbedPane tabbedPane = new JTabbedPane();

    //Tab1
    private JPanel thresholdTab = new JPanel();
    private JScrollPane thresholdImagePane = new JScrollPane() {
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width += 100;
            return size;
        }
    };
    private JLabel imgLabel = null;
    private JSlider thresholdSlider = null;
    private JLabel thresholdSliderLabel = null;
    private int processingType = Imgproc.THRESH_BINARY;
    private JButton threshBinaryButton = new JButton("BINARY");
    private JButton threshBinaryInvButton = new JButton("BINARY INVERTED");
    private JButton threshTruncButton = new JButton("TRUNC");
    private JButton threshToZeroButton = new JButton("TO ZERO");
    private JButton threshToZeroInvButton = new JButton("TO ZERO INVERTED");

    //Tab2
    private JPanel tab2 = null;


    public Tabs(Container pane, Mat img){
        this.pane = pane;
        this.img = img;
        fillImgLabel(this.img);
        fillThresholdTab();
        setUpProcessingButtons();
        tab2 = new AdaptiveThresholdTab(img).getTab();
    }

    private void fillThresholdTab() {
        if (imgLabel == null) {
            throw new NullPointerException("imgLabel was null");
        }
        thresholdTab.setLayout(new GridBagLayout());
        thresholdImagePane.setLayout(new ScrollPaneLayout());
        thresholdTab.add(thresholdImagePane, new GridBagConstraints(
                 0, 0, 10, 6, 10, 10,
                GridBagConstraints.WEST,
                GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0),
                0,0
        ));
        thresholdSlider = setUpSlider(0, 255);
        thresholdSliderLabel = addSliderToPane(thresholdSlider, thresholdTab);
        thresholdSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = thresholdSlider.getValue();
                int maxValue = thresholdSlider.getMaximum();
                thresholdSliderLabel.setText("Value: " + value);
                ElementsActions.changeThreshold(img, imgLabel,
                        value, maxValue, processingType);
            }
        });
        thresholdTab.add(threshBinaryButton, new GridBagConstraints(
                11, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        thresholdTab.add(threshBinaryInvButton, new GridBagConstraints(
                11, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        thresholdTab.add(threshTruncButton, new GridBagConstraints(
                11, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        thresholdTab.add(threshToZeroButton, new GridBagConstraints(
                11, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        thresholdTab.add(threshToZeroInvButton, new GridBagConstraints(
                11, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        imgLabel.setBounds(5,5,300,300);
        thresholdImagePane.setViewportView(imgLabel);
        thresholdImagePane.setBounds(5,5,300,300);
        thresholdImagePane.setMinimumSize(new Dimension(700,700));
    }

    private void fillImgLabel(Mat img) {
        imgLabel = new JLabel(new ImageIcon(CvUtils.MatToBufferedImage(img)));
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

    private JLabel addSliderToPane(JSlider slider, Container pane) {
        if (slider == null) {
            throw new NullPointerException("slider was null");
        }
        JLabel label = new JLabel("Value: " + slider.getValue());
        pane.add(slider, new GridBagConstraints(
                11, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0
        ));
        pane.add(label, new GridBagConstraints(
                12, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0
        ));
        return label;
    }

    private void setUpProcessingButtons() {
        threshBinaryButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_BINARY);
        threshBinaryInvButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_BINARY_INV);
        threshTruncButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_TRUNC);
        threshToZeroButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_TOZERO);
        threshToZeroInvButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_TOZERO_INV);
    }


    public void addTabbedPane() {
        tabbedPane.addTab("Threshold", thresholdTab);
        tabbedPane.addTab("AdaptiveThreshold", tab2);
        pane.add(tabbedPane);
    }
}
