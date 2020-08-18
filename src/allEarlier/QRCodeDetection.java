package allEarlier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;

public class QRCodeDetection {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private static Mat readImg(String filename) {
        return Imgcodecs.imread("Resources\\" + filename);
    }

    private static Mat readImg(String filename, String folder) {
        return Imgcodecs.imread(folder + '\\' + filename);
    }

    private static String filename = "Resources\\qrCodeBaseModule.png";

    /*
    Позволяет считать QR с картинки, которая находится по пути в filename
     */
    public static void ReadQR() {
        Mat img = Imgcodecs.imread(filename);
        QRCodeDetector qrdecoder = new QRCodeDetector();
        //Rectified image - qr code inside the rect
        Mat bbox = new Mat();
        Mat rectifiedImage = new Mat();
        String data = qrdecoder.detectAndDecode(img, bbox, rectifiedImage);
        if (data.length() > 0) {
            System.out.println("Decoded Data: " + data);
            rectifiedImage.convertTo(rectifiedImage, CvType.CV_8UC3);
            HighGui.imshow("Rectified QRCode", rectifiedImage);
            //Сохраняем картинку по пути и под названием, указанном в методе imwrite
            boolean savedBox = Imgcodecs.imwrite("Resources\\rectified.png", rectifiedImage);
            if (!savedBox) {
                System.out.println("Couldnt save rectified img");
            }

            HighGui.waitKey(0);
        }
        else
            System.out.println("QR code not detected");
    }

    public static void main(String[] args) {
        QRCodeDetection.ReadQR();
    }
}
