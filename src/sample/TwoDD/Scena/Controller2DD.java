package sample.TwoDD.Scena;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.TwoDD.Class.ImageScan;
import sample.TwoDD.Class.StringLengthSort;

import javax.imageio.ImageIO;
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

    @FXML
    private TextField TextF2;

    public int ImgRead = 0;
    public int iterO = 0;
    public int[][] mas;
    public String[] FilesBMP;
    public String fgetPath;
    public boolean finalrun = true;
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
            int i = 0;
            java.io.File f = file.getSelectedFile();
            fgetPath = f.getPath();
            TextF.setText("Коллическво файлов: " + FilesBMP.length);
       });
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(finalrun){
                    try {
                        int Itero;
                        if(iterO == 0) Itero = FilesBMP.length-1;
                        else Itero = Integer.parseInt(MaxIter.getText());
                        if(ImgRead == Itero) finalrun = false;
                        TextF2.setText("Состаяние: Обработано " + ImgRead);
                        Comparator<String> stringLengthComparator = new StringLengthSort();
                        Arrays.sort(FilesBMP, stringLengthComparator);
                        for(String e :FilesBMP)
                        {
                            Tf.appendText(e+"\n");
                        }
                        int save = 1;
                        File filIm = new File(fgetPath +"\\"+ FilesBMP[ImgRead]);
                        BufferedImage sourceImage = null;
                        try {
                            sourceImage = ImageIO.read(filIm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        int width = sourceImage.getWidth();
                        int height = sourceImage.getHeight();
                        int[][] mas0 = new int[width][height];
                        int[][] mas0c = new int[width][height];
//            int red = Color.RED.getRGB();
//            int green = Color.GREEN.getRGB();
//            int blue = Color.BLUE.getRGB();
                        int black = Color.BLACK.getRGB();
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width; x++) {
                                Color c = new Color(sourceImage.getRGB(x, y),true);
                                if (sourceImage.getRGB(x, y) == black) {
                                    mas0[x][y] = 0;
                                }
                                else
                                {
                                    mas0[x][y] = 1;
                                    int r = c.getRed();
                                    int g = c.getGreen();
                                    int b = c.getBlue();
                                    mas0c[x][y] = (r+g+b)/3;
                                }
                            }
                        }

                        String path = fgetPath + "\\" + FilesBMP[ImgRead];
                        Image image1 = null;
                        try {
                            image1 = new Image(new FileInputStream(path));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        TestIm1.setImage(image1);

//            ImageScan imagescan1 = new ImageScan(fgetPath,FilesBMP,save);
//            // сохраняем объект BufferedImage в виде нового изображения
//            try {
//                ImageIO.write((RenderedImage) imagescan1, "bpm", new File(String.valueOf(file.getParent()),"resultImage.bpm"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
                        ImgRead ++;
                        Thread.sleep(1000); //1000 - 1 сек
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });

        Start.setOnAction(actionEvent ->
        {
            Start.getScene().getWindow().hide();
            run.start();
            if(Iter.isSelected()) iterO = 1;
        });
    }
}
