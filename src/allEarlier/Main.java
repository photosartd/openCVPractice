package allEarlier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Main {
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void openImage() {
        Mat img = Imgcodecs.imread("Resources\\lena.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        if (img.empty()) {
            System.out.println("Couldnt load an image");
        }
        else {
            System.out.println(img.width());
            System.out.println(img.height());
            System.out.println(CvType.typeToString(img.type()));
            System.out.println(img.channels());
        }
    }

    public static void openAndSave() {
        Mat img = Imgcodecs.imread("Resources\\board.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        if (img.empty()) {
            System.out.println("Couldnt load an image");
        }
        else {
            boolean st = Imgcodecs.imwrite("Resources\\boardGrayscale.png", img);
            if (!st) {
                System.out.println("Couldnt save an image");
            }
        }
    }

    /*
    To be able to open a lot of video formats we need to
    copy the file opencv_videoio_ffmpeg440_64.dll
    from build\x64 to the folder with our project
     */
    public static void openVideo(){
        VideoCapture cap = new VideoCapture("Resources\\testVideo.mp4");
        if (!cap.isOpened()){
            System.out.println("Couldnt open the video");
        }
    }

    public static void main(String[] args) {
        /*//Read image
        Mat imgLena = Imgcodecs.imread("Resources\\lena.jpg");
        //Show image
        HighGui.imshow("Lena", imgLena);
        HighGui.waitKey(0);*/

        //Read video
        /*VideoCapture vid = new VideoCapture("Resources\\testVideo.mp4");
        if (!vid.isOpened())
            throw new IllegalStateException;
        //Show video
        while (true) {
            vid.read();
        }*/
        Main.openAndSave();

    }


}
