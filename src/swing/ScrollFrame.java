package swing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import utils.CvUtils;

import javax.swing.*;
import java.awt.*;

public class ScrollFrame extends JFrame {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public ScrollFrame() {
        setTitle("scrollframe");
        setSize(300,250);
        setLayout(new GridLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Mat img = Imgcodecs.imread("Resources\\board.jpg");

        //JPanel panel = new JPanel();
        JLabel lbl = new JLabel(new ImageIcon(CvUtils.MatToBufferedImage(img)));
        //lbl.setBounds(0,0,300,300);
        //panel.add(lbl);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(new JLabel(new ImageIcon(CvUtils.MatToBufferedImage(img))));
        add(pane);


        setVisible(true);
    }

    public static void main(String[] args) {
        ScrollFrame frame = new ScrollFrame();
    }
}
