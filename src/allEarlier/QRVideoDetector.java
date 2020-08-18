package allEarlier;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import utils.CvUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class QRVideoDetector {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    //Стандартный путь для QRDecoderFrame, конкретно здесь он не важен
    private static String defaultFilepath = "C:\\Документы\\opencvTry\\Resources";
    //Флажки для обработки захвата и остановки кадра
    public static boolean isRun = true;
    public static boolean isEnd = false;

    public static void webcamCapture() {
        JFrame window = new JFrame("Press <Esc> to turn off the camera");
        window.setSize(640, 480);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        //Обработка нажатия кнопки закрыть в заголовке окна
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRun = false;
                if (isEnd) {
                    window.dispose();
                    System.exit(0);
                }
                else {
                    System.out.println(
                            "Firstly press <Esc>, after press Exit"
                    );
                }
            }
        });
        //Обработка нажатия кнопки <Esc>
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    isRun = false;
                }
            }
        });

        JLabel label = new JLabel();
        window.setContentPane(label);
        window.setVisible(true);
        //Подключаемся к камере
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            window.setTitle("Cant connect to the camera");
            isRun = false;
            isEnd = true;
            return;
        }
        else {
            try {
                //Задаём размеры кадра
                camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
                camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
                //Считываем кадры
                Mat frame = new Mat();
                BufferedImage img = null;
                while (isRun) {
                    if (camera.read(frame)) {
                        //Здесь можно вставить код обработки кадра
                        //Проверяем, есть ли qr:
                        QRCodeDetector qrCodeDetector = new QRCodeDetector();
                        //Для хранения определенных контурных точек QR
                        Mat bbox = new Mat();
                        //Для хранения конкретно самого QR
                        Mat rectifiedImg = new Mat();
                        //List with detected info
                        List<String> decodedInfo = new LinkedList<String>();
                        //boolean qrDetected = qrCodeDetector.detectAndDecodeMulti(frame, decodedInfo);
                        String decodedData = qrCodeDetector.detectAndDecode(frame, bbox, rectifiedImg);
                        //Если нашли qr, отображаем нашу форму с qr
                        if (decodedData.length() > 0) {
                            //Создаем QRDecoderFrame для отображения QR
                            QRDecoderFrame fr = new QRDecoderFrame(defaultFilepath);
                            //Перевод картинки из Mat в BufferedImg
                            BufferedImage imgWithQR = CvUtils.MatToBufferedImage(frame);
                            fr.setSourceImg(imgWithQR);
                            fr.init();
                            break;
                        }
                        img = CvUtils.MatToBufferedImage(frame);
                        if (img != null) {
                            ImageIcon imageIcon = new ImageIcon(img);
                            label.setIcon(imageIcon);
                            label.repaint();
                            window.pack();
                        }
                        try {
                            Thread.sleep(100);//10 frames per sec
                        } catch (InterruptedException e) {}
                    }
                    else {
                        System.out.println("Couldnt take a frame");
                        break;
                    }
                }
            }
            finally {
                camera.release();
                isRun = false;
                isEnd = true;
            }
            window.setTitle("Camera is turned off");
        }
    }

    public static void main(String[] args) {
        //Здесь можно запустить видеораспознавание QR
        QRVideoDetector.webcamCapture();
    }
}
