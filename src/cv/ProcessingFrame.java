package cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;

/*
Позволяет применить несколько методов обработки к картинке, которые описаны в Tabs. Для этого запустите main
 */
public class ProcessingFrame extends JFrame {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private static String filepath = "Resources\\plate4.tiff";
    private static Mat img;
    private static Mat imgGray = new Mat();
    static {
        img = Imgcodecs.imread(filepath);
        if (img.empty()) {
            throw new IllegalArgumentException("Image hasnt been opened");
        }
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        if (imgGray == null) {
            throw new IllegalStateException("imgGray was null");
        }
    }

    ProcessingFrame() {
        setTitle(filepath);
        setSize(1000,1000);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Tabs tabs = new Tabs(this, imgGray);
        tabs.addTabbedPane();

        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        ProcessingFrame processingFrame = new ProcessingFrame();
    }
}
