package allEarlier;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

import java.util.ArrayList;

public class CVPart {

    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private static final String directory = "C:\\Документы\\Практика 2020\\Практика-20200728T132152Z-001\\Практика\\Plates photos and video";
    private static final Mat image = Imgcodecs.imread("Resources\\plate4.tiff");
    private static Mat imageGray = new Mat();
    static {
        Imgproc.cvtColor(image, imageGray, Imgproc.COLOR_BGR2GRAY);
    }

    public static void delineationOfBoundaries() {
        Mat img = Imgcodecs.imread("Resources\\board.jpg");
        if (img.empty()) {
            System.out.println("Couldnt load the picture");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        CvUtils.showImage(imgGray, "Gray");
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges,80,200);
        CvUtils.showImage(edges, "Canny");
        Mat img3 = new Mat();
        Imgproc.threshold(imgGray, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        CvUtils.showImage(edges2, "Canny + THRESH_OTSU");
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(imgGray, img4, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 3,5);
        Mat edges3 = new Mat();
        Imgproc.Canny(img4, edges3,80,200);
        CvUtils.showImage(edges3, "Canny + adaptiveThreshold");
        img.release();
        img3.release();
        img4.release();
        imgGray.release();
        edges.release();
        edges2.release();
        edges3.release();
    }

    public static void findAndDrawContours() {
        Mat img = Imgcodecs.imread("Resources\\board.jpg");
        if (img.empty()) {
            System.out.println("Couldnt load the picture");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        Imgproc.threshold(imgGray, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        CvUtils.showImage(edges2, "Canny + THRESH_OTSU");
        //Создаем копию контуров
        Mat edgesCopy = edges2.clone();
        //Создаём список, в котором будут храниться контура
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        //RETR_TREE - айти все контуры и организовать полную
        //иерархию вложенных контуров
        //CHAIN_APPROX_SIMPLE - method, который сжимае горизонтальные,
        //вертикальные и диагональные сегменты и указывает только их конечные точки
        Imgproc.findContours(edgesCopy, contours, hierarchy,
                Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        System.out.println(contours.size());
        System.out.println(hierarchy.size());
        System.out.println(hierarchy.dump());
        //Создадим ещё одно пустое изображение такого же размера, чтобы посмотреть на контуры
        Mat clean = new Mat(img.width(), img.height(), CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.drawContours(clean, contours, -1, CvUtils.COLOR_BLACK);
        CvUtils.showImage(clean, "drawContours");
        //Попробуем пофильтровать незамкнутые контуры
        ArrayList<MatOfPoint> closedContours = new ArrayList<>();
        for (int i = 0; i < contours.size(); i++) {
            /*MatOfPoint2f curr = new MatOfPoint2f(
                    contours.get(i).toArray()
            );
            boolean isClosed = false;
            double arcL = Imgproc.arcLength(curr, isClosed);
            if (isClosed) {
                closedContours.add(contours.get(i));
            }*/
            if (!Imgproc.isContourConvex(contours.get(i))) {
                closedContours.add(contours.get(i));
            }
        }
        Mat clean1 = new Mat(img.width(), img.height(), CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.drawContours(clean1, closedContours, -1, CvUtils.COLOR_BLACK);
        CvUtils.showImage(clean1, "drawContours");
        img.release();
        imgGray.release();
        img3.release();
        edges2.release();
        edgesCopy.release();
        hierarchy.release();
    }

    private static final String board4Tiff = "plate4.tiff";
    private static final String board1i = "plate1i.jpeg";
    public static void plate4() {
        Mat img = Imgcodecs.imread("Resources\\" + board1i);
        if (img.empty()) {
            System.out.println("Couldnt load the picture");
            return;
        }
        CvUtils.showImage(img, "Usual");
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        CvUtils.showImage(imgGray, "Grayed");
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200);
        CvUtils.showImage(edges, "Canny");
        Mat img3 = new Mat();
        Imgproc.threshold(imgGray, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        CvUtils.showImage(edges2, "Canny + THRESH_OTSU");
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(imgGray, img4, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 3,5);
        Mat edges3 = new Mat();
        Imgproc.Canny(img4, edges3,80,200);
        CvUtils.showImage(edges3, "Canny + adaptiveThreshold");
    }

    public static void findColor(){
        Mat img = Imgcodecs.imread("Resources\\" + board1i);
        if (img.empty()) {
            System.out.println("Couldnt load the picture");
            return;
        }
        CvUtils.showImage(img, "Original");
        Mat hsv = new Mat();
        Imgproc.cvtColor(img, hsv, Imgproc.COLOR_BGR2HSV);
        Mat h = new Mat();
        Core.extractChannel(hsv,h,0);
        Mat img2 = new Mat();
        Core.inRange(h, new Scalar(40), new Scalar(80), img2);
        CvUtils.showImage(img2, "Green");
        Core.inRange(h, new Scalar(100), new Scalar(140), img2);
        CvUtils.showImage(img2, "Blue");
        Core.inRange(hsv, new Scalar(0,200,200),
                new Scalar(20, 256, 256), img2);
        CvUtils.showImage(img2, "Red");
        Core.inRange(hsv, new Scalar(0, 0, 0),
                new Scalar(0, 0, 50), img2);
        CvUtils.showImage(img2, "Black");
        img.release();
        img2.release();
        hsv.release();
        h.release();
    }

    public static void thresholdInvest() {
        Mat img = Imgcodecs.imread("Resources\\lena.jpg");
        if (img.empty()) {
            System.out.println("Couldnt open the file");
            return;
        }
        Mat th1 = new Mat();
        /*
        Thresh binary divide pixels so part of them is less than
        thresh and another part is bigger
         */
        Imgproc.threshold(img, th1, 127, 255, Imgproc.THRESH_BINARY);
        CvUtils.showImage(th1, "Thresh binary");
        //convert to gray
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        //also apply the same threshold
        Imgproc.threshold(imgGray, th1, 127, 255, Imgproc.THRESH_BINARY);
        CvUtils.showImage(th1, " Thresh binary for grayed pic");
        //THRESH_BINARY_INV will give us inverse result of usual binary
        /*THRESH_TRUNC
        Here if the pixel value is up to thresh, it wouldnt change
        Otherwise it changes to = thresh
         */
        Mat th2 = new Mat();
        Imgproc.threshold(imgGray, th2, 50, 255,
                Imgproc.THRESH_TRUNC);
        CvUtils.showImage(th2, "Thresh trunc");
        /*
        THRESH_TOZERO makes all pixels that are less than thresh
        = 0 and others remain unchanged
        THRESH_TOZERO_INV makes the opposite (if the value
        of the pixel is bigger than the thresh then it = 0)
         */
        Mat th3 = new Mat();
        Imgproc.threshold(imgGray, th3, 127, 255,
                Imgproc.THRESH_TOZERO);
        CvUtils.showImage(th3, "Thresh to zero");

    }

    /*Adaptive threshold will be needed when we have photos
    (meistens that have bad quality or blicks etc) to properly
    process them
     */
    public static void adaptiveThresholding() {
        /*
        there are TWO types of adaptive methods
        ADAPTIVE_THRESH_MEAN_C - the threshold value is a mean
        (среднее) of the blockSize x blockSize neighborhood
        of the pixel minus C (C is also a parameter)
        ADAPTIVE_THRESH_GAUSSIAN_C - the threshold value
        is a weighted sum (cross-corellation with a Gaussian
        window) of the blockSize x blockSize neighborhood
        of the pixel minus C
         */
        Mat th1 = new Mat();
        Imgproc.adaptiveThreshold(imageGray, th1, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY,
                15, 4);
        CvUtils.showImage(th1, "Adoptive threshold ");

        Mat th2 = new Mat();
        Imgproc.adaptiveThreshold(imageGray, th2, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY,
                15, 4);
        CvUtils.showImage(th2, "Adoptive threshold Gaussian ");
    }

    public static void main(String[] args) {
        CVPart.adaptiveThresholding();
    }
}
