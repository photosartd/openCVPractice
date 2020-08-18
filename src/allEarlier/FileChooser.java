package allEarlier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import org.opencv.objdetect.QRCodeDetector;

public class FileChooser {
    public FileChooser(String filepath) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QRDecoderFrame frame = new QRDecoderFrame(filepath);
                BufferedImage img = null;
                //try to open an image
                try {
                    img = ImageIO.read(new File(filepath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //set source Image
                frame.setSourceImg(img);
                frame.init();
            }
        });
    }

    public static void main(String[] args) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);
        String filepath = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filepath = fc.getSelectedFile().getAbsolutePath();
        }
        else {
            System.out.println("User clicked CANCEL");
            System.exit(1);
        }
        new FileChooser(filepath);
    }
}
