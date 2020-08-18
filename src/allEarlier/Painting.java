package allEarlier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

public class Painting {
    public static void paintLines() {
        Mat img = new Mat(300, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.line(img, new Point(50,50), new Point(250,50), CvUtils.COLOR_RED);
        Imgproc.line(img, new Point(50,100), new Point(250,150), CvUtils.COLOR_BLUE, 5);
        //Без сглаживания
        Imgproc.line(img, new Point(50,150), new Point(250,200), CvUtils.COLOR_GREEN,5,Imgproc.LINE_4,0);
        //Со сглаживанием
        Imgproc.line(img, new Point(50,200), new Point(250,250), CvUtils.COLOR_BLACK, 5, Imgproc.LINE_AA, 0);
        CvUtils.showImage(img, "Lines painting");
    }

    public static void paintArrowedLines(){
        Mat img = new Mat(300, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.arrowedLine(img, new Point(50, 50), new Point(250, 50),
                CvUtils.COLOR_RED);
        Imgproc.arrowedLine(img, new Point(50, 70), new Point(250, 120),
                CvUtils.COLOR_BLUE, 5, Imgproc.LINE_8, 0, 0.1);
        // Без сглаживания
        Imgproc.arrowedLine(img, new Point(50, 120), new Point(280, 180),
                CvUtils.COLOR_GREEN, 5, Imgproc.LINE_4, 0, 0.2);
        // Со сглаживанием
        Imgproc.arrowedLine(img, new Point(50, 200), new Point(250, 250),
                CvUtils.COLOR_BLACK, 5, Imgproc.LINE_AA, 0, 0.3);
        CvUtils.showImage(img, "Arrowed lines painting");
    }

    public static void paintRectangles() {
        Mat img = new Mat(300, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.rectangle(img, new Point(50, 10), new Point(250, 50),
                CvUtils.COLOR_BLACK);
        Imgproc.rectangle(img, new Point(50, 80), new Point(250, 120),
                CvUtils.COLOR_BLUE, 15);
        // Заливка без обводки
        Imgproc.rectangle(img, new Point(50, 150), new Point(250, 200),
                CvUtils.COLOR_GREEN, Core.FILLED);
        // Со сглаживанием углов
        Imgproc.rectangle(img, new Point(50, 230), new Point(250, 280),
                CvUtils.COLOR_BLACK, 15, Imgproc.LINE_AA, 0);
        Imgproc.rectangle(img, new Point(50, 230), new Point(250, 280),
                CvUtils.COLOR_WHITE);
        CvUtils.showImage(img, "Рисование прямоугольников");
    }

    public static void paintCircle() {
        Mat img = new Mat(300, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.circle(img, new Point(80, 80), 50, CvUtils.COLOR_BLACK);
        Imgproc.circle(img, new Point(200, 80), 30,
                CvUtils.COLOR_BLUE, 5);
        // Заливка без обводки
        Imgproc.circle(img, new Point(80, 200), 50,
                CvUtils.COLOR_GREEN, Core.FILLED);
        // Со сглаживанием
        Imgproc.circle(img, new Point(200, 200), 50,
                CvUtils.COLOR_BLACK, 5, Imgproc.LINE_AA, 0);
        CvUtils.showImage(img, "Circles painting");
    }

    public static void main(String[] args) {
        Painting.paintCircle();
    }
}
