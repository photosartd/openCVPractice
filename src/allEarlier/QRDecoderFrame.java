package allEarlier;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import utils.CvUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class QRDecoderFrame extends JFrame {

    private static final GridBagConstraints SOURCE_IMAGE_CONSTRAINT =
            new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(1,1,1,1),
                    0,0);
    private static final GridBagConstraints QR_CODE_CONSTRAINT =
            new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(1,1,1,1),
                    0,0);
    private static final GridBagConstraints QR_DATA_CONSTRAINT =
            new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(1,1,1,1),
                    0,0);
    private static final GridBagConstraints SAVE_BUTTON_CONSTRAINT =
            new GridBagConstraints(1, 2, 1, 1, 1, 1,
                    GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,
                    new Insets(1,1,1,1),
                    0,0);

    private String filepath;
    private BufferedImage sourceImg;
    private String decodedData;
    private BufferedImage rectifiedQR;

    private JTextField QrData = new JTextField();
    private JButton saveQrImgButton = new JButton("Save QR code");

    public QRDecoderFrame(String filepath) {
        this.filepath = filepath;
    }

    public void init() {
        setTitle(filepath);
        setSize(640,480);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (sourceImg != null) {
            readQR();
            addImgToLabel(sourceImg, QRDecoderFrame.SOURCE_IMAGE_CONSTRAINT);
        }
        if (rectifiedQR != null) {
            addImgToLabel(rectifiedQR, QRDecoderFrame.QR_CODE_CONSTRAINT);
        }
        //Добавление данных с qr
        {
            this.add(QrData, QRDecoderFrame.QR_DATA_CONSTRAINT);
            //Установить границы
            QrData.setBorder(null);
            //Установить прозрачность(фолс)
            QrData.setOpaque(false);
            //Не можем изменять
            QrData.setEditable(false);
        }
        saveQrImgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!saveQrImgButton.isEnabled()){
                    System.out.println("You have already saved the QR!");
                }
                else {
                    int randNum = ThreadLocalRandom.current().nextInt(0, 1001);
                    boolean qrSaved = Imgcodecs.imwrite("Resources\\qr" + String.valueOf(randNum) + ".png", CvUtils.BufferedImageToMat(rectifiedQR));
                    if (!qrSaved) {
                        System.out.println("Couldnt save QR");
                    }
                    else {
                        saveQrImgButton.setEnabled(false);
                    }
                }
            }
        });
        this.add(saveQrImgButton, QRDecoderFrame.SAVE_BUTTON_CONSTRAINT);

        setVisible(true);
        pack();
    }

    private void addImgToLabel(BufferedImage img, GridBagConstraints constraints) {
        JLabel lbl = new JLabel();
        lbl.setSize(img.getWidth(), img.getHeight());
        lbl.setIcon(new ImageIcon(img));
        this.add(lbl, constraints);
        pack();
    }

    private void readQR() {
        Mat img = CvUtils.BufferedImageToMat(sourceImg);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        Mat bbox = new Mat();
        Mat rectifiedImage = new Mat();
        decodedData = qrCodeDetector.detectAndDecode(CvUtils.BufferedImageToMat(sourceImg), bbox, rectifiedImage);
        if (decodedData.length() > 0) {
            rectifiedImage.convertTo(rectifiedImage, CvType.CV_8UC3);
            rectifiedQR = CvUtils.MatToBufferedImage(rectifiedImage);
            QrData.setText(decodedData);
        } else {
            System.out.println("QR code not detected");
        }
    }

    public void setSourceImg(BufferedImage img) {
        sourceImg = img;
    }
}

