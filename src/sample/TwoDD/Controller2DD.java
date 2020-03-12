package sample.TwoDD;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.swing.*;

public class Controller2DD {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Fold;

    @FXML
    private CheckBox OnlyLast;

    @FXML
    private TextField Xx;

    @FXML
    private TextField XX;

    @FXML
    private TextField Yy;

    @FXML
    private TextField YY;

    @FXML
    private CheckBox BPIx;

    @FXML
    private CheckBox BF;

    @FXML
    private Button Start;

    @FXML
    private CheckBox Delt;

    @FXML
    private ImageView TestIm1;
    @FXML
    private ImageView TestIm2;

    @FXML
    private Button Clear;

    @FXML
    private TextArea Tf;

    @FXML
    private TextField TextF;

    @FXML
    private CheckBox Gran;

    @FXML
    private Button Save;

    @FXML
    private CheckBox Iter;

    @FXML
    private Button Fonfold;

    @FXML
    private TextField DFon;

    @FXML
    private TextField MaxIter;


    public String[] FilesBMP;
    @FXML
    void initialize()
    {
        Fold.setOnAction(actionEvent ->
        {
            Fold.getScene().getWindow().hide();
            JFileChooser file = new JFileChooser();
            file.setMultiSelectionEnabled(true);
            file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            file.setFileHidingEnabled(false);

            if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

//                System.err.println(f.getPath());
//                Test.setText(f.getParent());
                java.io.File f = file.getSelectedFile();
                FilesBMP = f.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".bmp");
                    }
                });
            }
            java.io.File f = file.getSelectedFile();
            TextF.setText("Коллическво файлов: " + FilesBMP.length);
            int i = 0;
            Comparator<String> stringLengthComparator = new StringLengthSort();
            Arrays.sort(FilesBMP, stringLengthComparator);
            for(String e :FilesBMP)
            {
                Tf.appendText(e+"\n");
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(f.getPath(), FilesBMP[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // явно указываем расширение файла для простоты реализации
            try {
                ImageIO.write(image, "bmp", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                baos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String base64String = Base64.encode(baos.toByteArray());
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // декодируем полученную строку в массив байт
            byte[] resByteArray = Base64.decode(base64String);

            // считываем полученный массив в объект BufferedImage
            BufferedImage resultImage = null;
            try {
                resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // сохраняем объект BufferedImage в виде нового изображения
            try {
                ImageIO.write(resultImage, "bpm", new File(String.valueOf(file.getParent()),"resultImage.bpm"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = f.getPath() + "\\" + FilesBMP[0];
            Image image1 = null;
            try {
                image1 = new Image(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            TestIm1.setImage(image1);
        });
    }
}
