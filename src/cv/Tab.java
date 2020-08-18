package cv;

import org.opencv.core.Mat;
import utils.CvUtils;

import javax.swing.*;
import java.awt.*;

public abstract class Tab {

    protected JPanel panel = new JPanel();

    protected JScrollPane imagePane = new JScrollPane();

    protected JLabel imgLabel = null;
    protected JSlider slider = null;

    protected JLabel sliderLabel = null;

    protected abstract void fillComponents();
    protected JSlider setUpSlider(int min, int max) {
        if (min < 0)
            throw new IllegalArgumentException("Min value cant be less than 0");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, 0);
        slider.setMajorTickSpacing((max-min)/10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    protected JLabel addSliderToPane(JSlider slider, Container panel) {
        if (slider == null) {
            throw new NullPointerException("slider was null");
        }
        JLabel label = new JLabel("Value: " + slider.getValue());
        panel.add(slider, new GridBagConstraints(
                11, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0
        ));
        panel.add(label, new GridBagConstraints(
                12, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2),
                0,0
        ));
        return label;
    }

    protected void fillImgLabel(Mat img) {
        imgLabel = new JLabel(new ImageIcon(CvUtils.MatToBufferedImage(img)));
    }
    protected void fillImage() {
        if (imgLabel == null) {
            throw new NullPointerException("imgLabel was null");
        }
        panel.setLayout(new GridBagLayout());
        imagePane.setLayout(new ScrollPaneLayout());
        panel.add(imagePane, new GridBagConstraints(
                0, 0, 10, 6, 10, 10,
                GridBagConstraints.WEST,
                GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0),
                0,0
        ));
        imgLabel.setBounds(5,5,300,300);
        imagePane.setViewportView(imgLabel);
        imagePane.setBounds(5,5,300,300);
        imagePane.setMinimumSize(new Dimension(700, 700));
    }

    //getters
    public JPanel getTab() {
        return panel;
    }
    protected JScrollPane getImagePane() {
        return imagePane;
    }

    protected JLabel getImgLabel() {
        return imgLabel;
    }

    protected JSlider getSlider() {
        return slider;
    }

    protected JLabel getSliderLabel() {
        return sliderLabel;
    }


    //setters
    protected void setPanel(JPanel panel) {
        panel = panel;
    }

    protected void setImagePane(JScrollPane imagePane) {
        imagePane = imagePane;
    }

    protected void setImgLabel(JLabel imgLabel) {
        this.imgLabel = imgLabel;
    }

    protected void setSlider(JSlider slider) {
        this.slider = slider;
    }

    protected void setSliderLabel(JLabel sliderLabel) {
        this.sliderLabel = sliderLabel;
    }
}
