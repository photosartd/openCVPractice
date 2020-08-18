package cv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

import javax.swing.*;

public class ElementsActions {
    public static void changeThreshold(Mat src, JLabel imgLabel, int value, int maxValue,
                                       int type) {
        Mat thresholdedImg = new Mat();
        Imgproc.threshold(src, thresholdedImg, value, maxValue, type);
        imgLabel.setIcon(new ImageIcon(CvUtils.MatToBufferedImage(thresholdedImg)));
    }

    public static void changeAdaptiveThreshold(Mat src, JLabel imgLabel,
                                               int maxValue,
                                               int adaptiveMethod,
                                               int thresholdType,
                                               int blockSize,
                                               double c) {
        Mat adaptThreshold = new Mat();
        Imgproc.adaptiveThreshold(src, adaptThreshold, maxValue,
                adaptiveMethod, thresholdType, blockSize, c);
        imgLabel.setIcon(new ImageIcon(CvUtils.MatToBufferedImage(adaptThreshold)));
    }
}
