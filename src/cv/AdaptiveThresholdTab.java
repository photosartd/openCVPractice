package cv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import utils.MathUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class AdaptiveThresholdTab extends Tab {

    //TODO() - убрать кнопки trunc, tozero and tozeroinv, они здесь не работают

    private int maxValue = 0;
    private int processingType = Imgproc.THRESH_BINARY;
    private int adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C;
    private int blockSize = 11;
    private double c = 3.0d;

    private JButton threshBinaryButton = new JButton("BINARY");
    private JButton threshBinaryInvButton = new JButton("BINARY INVERTED");
    private JButton methodThreshMeanC = new JButton("Mean C");
    private JButton methodGaussianC = new JButton("Gaussian C");

    private JTextField blockSizeTextField = new JTextField("11");
    private JLabel blockSizeLabel = new JLabel("BlockSize");

    private JTextField cTextField = new JTextField("3");
    private JLabel cLabel = new JLabel("C value (double)");

    Mat img;

    public AdaptiveThresholdTab(Mat img) {
        this.img = img;
        this.fillImgLabel(img);
        this.fillImage();
        fillComponents();
    }

    @Override
    protected void fillComponents() {
        this.setSlider(setUpSlider(0,255));
        this.setSliderLabel(addSliderToPane(getSlider(), getTab()));

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                maxValue = slider.getValue();
                //Check if Text Field has a number
                if (MathUtils.isNumericInt(blockSizeTextField.getText())) {
                    blockSize = Integer.parseInt(blockSizeTextField.getText());
                    if (blockSize % 2 == 0) {
                        System.out.println("Enter an odd number in BlockSize");
                        return;
                    }
                }
                else {
                    System.out.println("Enter an integer number");
                    return;
                }
                if (MathUtils.isNumericDouble(cTextField.getText())) {
                    c = Double.parseDouble(cTextField.getText());
                }
                else {
                    System.out.println("Enter a double number");
                    return;
                }
                ElementsActions.changeAdaptiveThreshold(img, imgLabel,
                        maxValue, adaptiveMethod, processingType,
                        blockSize, c);
            }
        });

        setUpProcessingButtons();
        addProcessingButtons();
        setUpTextFields();
        addTextFields();
    }

    private void setUpProcessingButtons() {
        threshBinaryButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_BINARY);
        threshBinaryInvButton.addActionListener(actionEvent -> processingType = Imgproc.THRESH_BINARY_INV);
        methodThreshMeanC.addActionListener(actionEvent -> adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C);
        methodGaussianC.addActionListener(actionEvent -> adaptiveMethod = Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);
    }

    private void addProcessingButtons() {
        panel.add(threshBinaryButton, new GridBagConstraints(
                11, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(threshBinaryInvButton, new GridBagConstraints(
                11, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(methodThreshMeanC, new GridBagConstraints(
                11, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(methodGaussianC, new GridBagConstraints(
                11, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
    }

    private void setUpTextFields() {
        blockSizeTextField.setEditable(true);
        cTextField.setEditable(true);
    }

    private void addTextFields() {
        panel.add(blockSizeTextField, new GridBagConstraints(
                11, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(blockSizeLabel, new GridBagConstraints(
                12, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(cTextField, new GridBagConstraints(
                11, 9, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
        panel.add(cLabel, new GridBagConstraints(
                12, 9, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),
                0,0
        ));
    }
}
