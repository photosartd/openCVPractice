package allEarlier;

import cv.Tab;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.CvUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TabletsDetecting {

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    private static String filename = "Resources\\24.bmp";
    private static Mat img = Imgcodecs.imread(filename);
    private static Mat imgGray = TabletsDetecting.toGrayScale(img);
    private static Mat thresholdedImg = new Mat();
    private static Mat tabletTemplate = Imgcodecs.imread("Resources\\tabletTemplate.png");
    static {
        Imgproc.cvtColor(tabletTemplate, tabletTemplate, Imgproc.COLOR_BGR2GRAY);
    }

    private static void init() {
        //Open an image
        showImage(img);
        //To gray
        //imgGray = TabletsDetecting.toGrayScale(img);
        //Open grayscaled image
        showImage(imgGray);
        //Threshold
        Imgproc.threshold(imgGray, thresholdedImg, 200, 255, Imgproc.THRESH_BINARY_INV);
        //Open thresholdedImg
        showImage(thresholdedImg);
        //Dilated img
        Mat dilatedImg = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(11,11));
        Imgproc.dilate(thresholdedImg, dilatedImg, kernel, new Point(-1,-1), 3, Core.BORDER_ISOLATED);
        showImage(dilatedImg);
        //erode
        Mat erodeImg = new Mat();
        Imgproc.erode(thresholdedImg, erodeImg, kernel, new Point(-1,-1), 1, Core.BORDER_ISOLATED);
        showImage(erodeImg);
        //Opening (erosion followed by dilation)
        Mat opening = new Mat();
        Imgproc.morphologyEx(thresholdedImg, opening, Imgproc.MORPH_OPEN, kernel);
        showImage(opening);
        //Closing (dilation followed by erosion)
        Mat closing = new Mat();
        Imgproc.morphologyEx(thresholdedImg, closing, Imgproc.MORPH_CLOSE, kernel);
        //Morph gradient
        Mat morphGradientImg = new Mat();
        Imgproc.morphologyEx(thresholdedImg, morphGradientImg, Imgproc.MORPH_GRADIENT, kernel);
        showImage(morphGradientImg);
    }

    private static void blurring() {
        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Mat kernel2 = Mat.ones(new Size(5,5), CvType.CV_32FC1);
        Mat kernel3 = new Mat();
        Core.divide(kernel2, new Scalar(15), kernel2);
        Mat homogeniousFilterImg = new Mat();
        Imgproc.filter2D(imgGray, homogeniousFilterImg, -1, kernel2);
        showImage(imgGray);
        showImage(homogeniousFilterImg);

        Mat blurFilter = new Mat();
        Imgproc.blur(imgGray, blurFilter, new Size(5,5));
        CvUtils.showImage(blurFilter, "Blurred Image");

        Mat gBlurFilter = new Mat();
        Imgproc.GaussianBlur(imgGray, gBlurFilter, new Size(5,5), 0);
        CvUtils.showImage(gBlurFilter, "Gaussian Blur");

        //Median filter is good for salt and pepper images
        Mat medianFilter = new Mat();
        Imgproc.medianBlur(imgGray, medianFilter, 5);
        CvUtils.showImage(medianFilter, "Median filter");

        //Bilateral filter is good for blurring and allowing the edges to remain sharp
        Mat bilateralFilter = new Mat();
        Imgproc.bilateralFilter(imgGray, bilateralFilter, 9, 75d, 75d);
        CvUtils.showImage(bilateralFilter, "Bilateral filter");

        Imgproc.threshold(homogeniousFilterImg, thresholdedImg, 200, 255, Imgproc.THRESH_BINARY_INV);
        //Open thresholdedImg
        showImage(thresholdedImg);
        //Dilated img
        Mat dilatedImg = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(11,11));
        Imgproc.dilate(thresholdedImg, dilatedImg, kernel, new Point(-1,-1), 3, Core.BORDER_ISOLATED);
        showImage(dilatedImg);
    }

    public static void gradientsAndEdgeDetection() {
        Mat lap = new Mat();
        Imgproc.Laplacian(imgGray, lap, CvType.CV_32F, 3);
        Mat imgLap = new Mat();
        Core.convertScaleAbs(lap, imgLap);
        CvUtils.showImage(imgLap, "Laplacian");

        //Sobel
        Mat sobelX = new Mat();
        Mat sobelY = new Mat();
        Imgproc.Sobel(imgGray, sobelX, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(imgGray, sobelY, CvType.CV_32F, 0, 1);
        Core.convertScaleAbs(sobelX, sobelX);
        Core.convertScaleAbs(sobelY, sobelY);
        CvUtils.showImage(sobelX, "SobelX");
        CvUtils.showImage(sobelY, "SobelY");
        Mat sobelCombined = new Mat();
        Core.bitwise_or(sobelX, sobelY, sobelCombined);
        CvUtils.showImage(sobelCombined, "Sobel Combined");

        Mat cannyImg = new Mat();
        Imgproc.Canny(imgGray, cannyImg, 100d, 200d);
        CvUtils.showImage(cannyImg, "Canny");
    }

    public static void imagePyramids() {
        Mat lowResImg = new Mat();
        Imgproc.pyrDown(imgGray, lowResImg);
        CvUtils.showImage(imgGray, "Original");
        CvUtils.showImage(lowResImg, "Low res Img");

        //Laplacian pyrmid works as edge detection
        ArrayList<Mat> imgPyramid = new ArrayList<>();
        Mat layer = imgGray.clone();
        imgPyramid.add(layer);
        for (int i = 0; i < 5; i++) {
            Imgproc.pyrDown(layer, layer);
            Mat lowResImage = layer.clone();
            imgPyramid.add(lowResImage);
            System.out.println(i + " - " + lowResImage.size());
        }

        for (int i = 5; i > 0; i--) {
            Mat gaussianExtended = new Mat();
            System.out.println(i + " " + imgPyramid.get(i).size());
            Imgproc.pyrUp(imgPyramid.get(i), gaussianExtended);
            Mat laplacian = new Mat();
            System.out.println(i + " - " + gaussianExtended.size());
            /*Эта часть не работает, потому что он как-то криво делит инты и получается,
            что при pyrUp and pyrDown значения отличаются на 1 (опять же деление),
            а он такую возможность почему-то не учитывает
             */
            //Работает только когда размеры случайно полностью совпадают
            if (i == 3) {
                Core.subtract(imgPyramid.get(i-1), gaussianExtended, laplacian);
                CvUtils.showImage(laplacian, String.valueOf(i));
            }
        }
    }

    public static Mat SobelCombined(Mat img) {
        //Sobel
        Mat sobelX = new Mat();
        Mat sobelY = new Mat();
        Imgproc.Sobel(img, sobelX, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(img, sobelY, CvType.CV_32F, 0, 1);
        Core.convertScaleAbs(sobelX, sobelX);
        Core.convertScaleAbs(sobelY, sobelY);
        //CvUtils.showImage(sobelX, "SobelX");
        //CvUtils.showImage(sobelY, "SobelY");
        Mat sobelCombined = new Mat();
        Core.bitwise_or(sobelX, sobelY, sobelCombined);
        //CvUtils.showImage(sobelCombined, "Sobel Combined");
        return sobelCombined;
    }

    private static void shapes() {
        Mat thresholded = new Mat();
        Imgproc.threshold(imgGray, thresholded, 216, 255, Imgproc.THRESH_BINARY);
        CvUtils.showImage(thresholded, "Thresholded");

        Mat dilatedImg = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.dilate(thresholded, dilatedImg, kernel, new Point(-1,-1), 1, Core.BORDER_ISOLATED);
        showImage(dilatedImg);
    }

    private static void detectingShapes() {
        Mat sobel = TabletsDetecting.SobelCombined(imgGray);
        //CvUtils.showImage(sobel, "sob");
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Mat thresholded = new Mat();
        Imgproc.threshold(imgGray, thresholded, 216, 255, Imgproc.THRESH_TOZERO);
        CvUtils.showImage(thresholded, "Thresholded");
        /*Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.erode(thresholded, thresholded, kernel, new Point(-1,-1), 1, Core.BORDER_ISOLATED);
        showImage(thresholded);*/
        Imgproc.findContours(thresholded, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        contours.stream().forEach(contour ->
                System.out.println(Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), false)));
        contours.stream()
                .forEach(contour ->
                        System.out.println(Imgproc.contourArea(contour)));
        /*List<MatOfPoint> filteredContours = contours.stream().filter(contour ->
                Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), false) > 6d).collect(Collectors.toList());
        Imgproc.drawContours(sobel, filteredContours, -1, CvUtils.COLOR_RED, 5);
        CvUtils.showImage(sobel, "With contours");*/
        List<MatOfPoint> filteredContours = contours.stream()
                .filter(contour ->
                        Imgproc.contourArea(contour) > 2100d).collect(Collectors.toList());
        /*ArrayList<MatOfPoint> filtConts = new ArrayList<>();
        for (MatOfPoint contour : filteredContours) {
            MatOfPoint2f apprCont = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), apprCont, 0.01d, true);
            filtConts.add(new MatOfPoint(apprCont));
        }*/
        HashMap<MatOfPoint, Rect> contoursWithRects = new HashMap<>();
        ArrayList<MatOfPoint> contsWithDefects = new ArrayList<>();
        double avArea = 0;
        double avRectPer = 0;
        double minArea = 100000, maxArea = 0;
        double minPerim = 10000, maxPerim = 0;
        int tabletsCounter = 0;
        for (MatOfPoint contour : filteredContours) {
            Rect boundingRect = Imgproc.boundingRect(contour);
            if (Checking.isSquareLike(boundingRect) && Checking.isRectPerimeterRight(boundingRect)) {
                contoursWithRects.put(contour, boundingRect);
                double contourArea = Imgproc.contourArea(contour);
                double contourPerim = 2*(boundingRect.width + boundingRect.height);
                avArea += contourArea;
                System.out.println("Area: " + contourArea);
                avRectPer += contourPerim;
                System.out.println("Perim: " + contourPerim);
                //Min and max values of perim and area
                if (minArea > contourArea) {
                    minArea = contourArea;
                }
                if (maxArea < contourArea) {
                    maxArea = contourArea;
                }
                if (minPerim > contourPerim) {
                    minPerim = contourPerim;
                }
                if (maxPerim < contourPerim) {
                    maxPerim = contourPerim;
                }
                tabletsCounter++;
                MatOfInt4 convexityDefects = Methods.convexityDefects(contour);
                System.out.println(tabletsCounter);
                System.out.println(convexityDefects.dump());
                System.out.println(convexityDefects.toString());
                System.out.println(convexityDefects.get(0,0)[1]);
                for (int row = 0; row < convexityDefects.rows(); row++) {
                    if (convexityDefects.get(row, 0)[3] > 3000) {
                        contsWithDefects.add(contour);
                        System.out.println("DEFECT: " + convexityDefects.get(row, 0)[3]);
                        break;
                    }
                }
            }
        }
        avArea /= tabletsCounter;
        avRectPer /= tabletsCounter;
        System.out.println("Average area: " + avArea + ", average rect perimeter: " + avRectPer);
        System.out.println("Min area: " + minArea + ", max area: " + maxArea +
                ", dispersion: " + minArea/avArea + "% - " + maxArea/avArea + "%");
        System.out.println("Min rect perimeter: " + minPerim + ", max rect perimeter: " + maxPerim +
                ", dispersion: " + minPerim/avRectPer + "% - " + maxPerim/avRectPer + "%");

        /*for (MatOfPoint key : contoursWithRects.keySet()) {
            Rect boundingRect = contoursWithRects.get(key);
            if (!Checking.isSquareLike(boundingRect)) {
                contours.remove(key);
            }
        }*/
        ArrayList<MatOfPoint> conts = new ArrayList<>(contoursWithRects.keySet());
        System.out.println(conts.size());
        Imgproc.drawContours(img, conts, -1, CvUtils.COLOR_RED, 5);
        Imgproc.drawContours(img, contsWithDefects, -1, CvUtils.COLOR_GREEN, 7);
        /*Imgproc.drawContours(img, conts, 19, CvUtils.COLOR_GREEN, 7);
        Imgproc.drawContours(img, conts, 20, CvUtils.COLOR_GREEN, 7);*/
        /*Imgproc.drawContours(img, conts, 21, CvUtils.COLOR_GREEN, 7);
        Imgproc.drawContours(img, conts, 22, CvUtils.COLOR_GREEN, 7);
        Imgproc.drawContours(img, conts, 23, CvUtils.COLOR_GREEN, 7);*/
        CvUtils.showImage(img, "With contours");
    }

    private static class Checking {
        private static double SQUARE_LIKE_RATIO = 1.4;

        private static double RECT_PERIMETER_AVERAGE = 245;
        private static double RECT_PERIMTER_LOWERBOUND = 0.7;
        private static double RECT_PERIMETER_UPPERBOUND = 1.3;

        public static boolean isSquareLike(Rect rect) {
            if ((double)rect.height/rect.width > SQUARE_LIKE_RATIO || (double)rect.width/rect.height > SQUARE_LIKE_RATIO) {
                return false;
            }
            return true;
        }

        public static boolean isRectPerimeterRight(Rect rect) {
            double perimeter = 2*(rect.height + rect.width);
            if (RECT_PERIMTER_LOWERBOUND*RECT_PERIMETER_AVERAGE < perimeter &&
            RECT_PERIMETER_UPPERBOUND*RECT_PERIMETER_AVERAGE > perimeter) {
                return true;
            }
            return false;
        }
    }

    private static class Methods {
        public static MatOfInt4 convexityDefects(MatOfPoint contour) {
            MatOfInt convexHull = new MatOfInt();
            Imgproc.convexHull(contour, convexHull);
            MatOfInt4 convexityDefects = new MatOfInt4();
            Imgproc.convexityDefects(contour, convexHull, convexityDefects);
            return convexityDefects;
        }
    }

    /*private static void histogram() {
        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);

        ArrayList<Mat> image = new ArrayList<>();
        image.add(img);

        MatOfInt channels = new MatOfInt(0, 1);

        //Quantize the hue to 30 lvls
        //and the saturation to 32 lvls
        int hbins = 30, sbins = 32;
        MatOfInt histSize = new MatOfInt(hbins, sbins);

        float hranges[] = {0, 180};
        float sranges[] = {0, 256};
        MatOfFloat ranges = new MatOfFloat(0, 180, 0, 256);
        Imgproc.calcHist();
    }*/

    private static void manualProcessing() {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HLS);
        showImage(img);
        System.out.println(img.get(0,0)[2]);
        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                double[] hls = img.get(row, col);
                double[] newHLS = {0, hls[1], 0};
                img.put(row, col, newHLS);
            }
        }
        System.out.println(img.toString());
        showImage(img);
        //mat for signed image
        Mat signedImg = new Mat();
        //Unsigned 16bit img
        Mat unsigned16bitImg = new Mat();
        img.convertTo(signedImg, CvType.CV_16SC3, 32767.0/255);
        img.convertTo(unsigned16bitImg, CvType.CV_16UC3, 65535.0/255);
        //sharpen (convolution?)
        Mat img2 = new Mat();
        Imgproc.GaussianBlur(unsigned16bitImg, img2, new Size(7,7), 0);
        Mat sharpenResult = new Mat();
        Core.addWeighted(unsigned16bitImg, 1.5, img2, -0.5, 0, sharpenResult);
        CvUtils.showImage(sharpenResult, "Sharpened");
        //Threshold
        Mat thresholdedImg = new Mat();
        //sharpenResult.convertTo(sharpenResult, CvType.CV_8UC1, 255/32767.0);
        Mat sharpen1Ch = new Mat(sharpenResult.rows(), sharpenResult.cols(), CvType.CV_8UC1);
        for (int row = 0; row < sharpen1Ch.rows(); row++) {
            for (int col = 0; col < sharpen1Ch.cols(); col++) {
                double[] hls = sharpenResult.get(row, col);
                sharpen1Ch.put(row, col, hls[1]*255/32767.0);
            }
        }
        CvUtils.showImage(sharpen1Ch, "Sharp CV8");
        System.out.println(sharpen1Ch.toString());
        Imgproc.adaptiveThreshold(sharpen1Ch, thresholdedImg, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY, 11, 3);
        CvUtils.showImage(thresholdedImg, "Thresholded");
    }

    private static void templatesMatching() {
        //Doesnt work properly with many ähnliche items
        //CvUtils.showImage(tabletTemplate, "Template");
        Mat result = new Mat();
        Mat thresholded = new Mat();
        Imgproc.threshold(imgGray, thresholded, 216, 255, Imgproc.THRESH_BINARY);
        Imgproc.matchTemplate(imgGray, tabletTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        System.out.println(result.toString());
        System.out.println(result.size());

        HashMap<Integer, Integer> points = new HashMap<>();
        //double threshold = 0.638d;
        double threshold = 0.4d;
        for (int row = 0; row < result.rows(); row++) {
            for (int col = 0; col < result.cols(); col++) {
                if (result.get(row, col)[0] >= threshold) {
                    points.put(row, col);
                }
            }
        }
        System.out.println(points);
        int width = tabletTemplate.width();
        int height = tabletTemplate.height();
        for (int key : points.keySet()) {
            Imgproc.rectangle(img, new Point(points.get(key), key),
                    new Point(points.get(key) + width, key + height),
                    CvUtils.COLOR_GREEN, 2);
        }
        CvUtils.showImage(img, "Rectangled");
        //System.out.println(Arrays.toString(result.get(0, 0)));
    }

    private static void openImage() {
        if (img.empty()) {
            throw new IllegalStateException("Image hasnt been opened");
        }
        showImage(img);
    }

    private static void showImage(Mat image) {
        CvUtils.showImage(image, "");
    }

    private static Mat toGrayScale(Mat image) {
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        return imgGray;
    }

    public static void main(String[] args) {
        //TabletsDetecting.blurring();
        //TabletsDetecting.gradientsAndEdgeDetection();
        //TabletsDetecting.imagePyramids();
        //TabletsDetecting.detectingShapes();
        //TabletsDetecting.templatesMatching();
        //TabletsDetecting.shapes();
        TabletsDetecting.manualProcessing();
    }
}
