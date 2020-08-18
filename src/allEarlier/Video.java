package allEarlier;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import utils.CvUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Video {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static boolean isRun = true;
    public static boolean isEnd = false;

    public static void openVideo() {
        //Создаём новый фрейм
        JFrame window = new JFrame("Watching video");
        //Устанавливаем размер формы
        window.setSize(1000, 600);
        //устанавливаем действие при нажатии на крестик
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Устанавливаем относительное положение посередине экрана
        window.setLocationRelativeTo(null);
        JLabel label = new JLabel();
        window.setContentPane(label);
        //Отображение формы
        window.setVisible(true);

        VideoCapture capture = new VideoCapture("Resources\\testVideo.mp4");
        if (!capture.isOpened()) {
            System.out.println("Не удалось открыть видео");
            return;
        }
        Mat frame = new Mat();
        BufferedImage img = null;
        while (capture.read(frame)) {
            Imgproc.resize(frame, frame, new Size(960, 540));
            // Здесь можно вставить код обработки кадра
            img = CvUtils.MatToBufferedImage(frame);
            if (img != null) {
                ImageIcon imageIcon = new ImageIcon(img);
                label.setIcon(imageIcon);
                label.repaint();
                window.pack();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
        System.out.println("Выход");
        capture.release();
    }

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
        Video.webcamCapture();
    }
}
