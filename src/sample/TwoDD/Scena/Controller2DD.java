package sample.TwoDD.Scena;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private AreaChart<Integer, Integer> Areac;

    @FXML
    private ComboBox<String> CBA;

    public int ImgRead = 0;
    public int iterO = 0;
    public int Itero;
    public String fgetPath;
    public String dirsavefoto;
    public Container filegetParent;
    public boolean finalrun = true;
    public BufferedImage imagesum = null;
    public int[] mas = null;
    public String[] FilesBMP;

    @FXML
    void initialize()
    {
        ObservableList<String> items = FXCollections.observableArrayList("1","2","3","4");
        CBA.setItems(items);
        XYChart.Series<Integer,Integer> series = new XYChart.Series<>();
        for(int x = 0; x<100;x++) series.getData().add(new XYChart.Data<>(x,x*x));
        Areac.getData().setAll(series);
        series.setName("Тест");
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
            filegetParent = file.getParent();
            TextF.setText("Коллическво файлов: " + FilesBMP.length);
            Comparator<String> stringLengthComparator = new StringLengthSort();
            Arrays.sort(FilesBMP, stringLengthComparator);
            File filImm = new File(fgetPath +"\\"+ FilesBMP[0]);
            try {
                imagesum = ImageIO.read(filImm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String maindir =  System.getProperty("user.dir");
            dirsavefoto = maindir + "\\src\\sample\\TwoDD\\result\\Foto";
        });
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(finalrun){
                    try {
                        if(iterO == 0) Itero = FilesBMP.length-1;
                        else Itero = Integer.parseInt(MaxIter.getText());
                        if(ImgRead == Itero) finalrun = false;
                        String path = fgetPath + "\\" + FilesBMP[ImgRead];
                        String resultfotoname = "resultImage.bmp";
                        File filIm = new File(fgetPath +"\\"+ FilesBMP[ImgRead]);
//                        Вывод фото на экран фото на экран
                        Image image1 = null;
                        try {
                            image1 = new Image(new FileInputStream(path));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        TestIm1.setImage(image1);
//                        Загрузка изображения для обработки
                        BufferedImage sourceImage = null;
                        try {
                            sourceImage = ImageIO.read(filIm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int width = sourceImage.getWidth();
                        int height = sourceImage.getHeight();
                        if(ImgRead == 0) mas = new int[width];
                        int[][] mas0 = new int[width][height];
                        int[][] mas0c = new int[width][height];

//                        Алгоритм заполнения массива интенсивнотси, суммирование снимка
                        int black = Color.BLACK.getRGB();
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width; x++) {
                                Color c = new Color(sourceImage.getRGB(x, y), true);
                                Color c2 = new Color(imagesum.getRGB(x, y), true);
                                if (sourceImage.getRGB(x, y) == black) {
                                    mas0[x][y] = 0;
                                } else {
                                    mas0[x][y] = 1;
                                    int r = c.getRed();
                                    int g = c.getGreen();
                                    int b = c.getBlue();
                                    mas0c[x][y] = (r + g + b) / 3;
                                }
                                if (c2.getRed() < c.getRed()||c2.getGreen() < c.getGreen()||c2.getBlue() < c.getBlue())
                                    imagesum.setRGB(x ,y, c.getRGB());
                            }
                        }
                        for(int x = 0; x < width; x++)
                        {
                            for(int y = 0; y < height; y++)
                            {
                                mas[x] += mas0c[x][y];
                            }
                        }

                        // сохрание нового изображения
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(imagesum, "bmp", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        OutputStream txt = new FileOutputStream(dirsavefoto + "\\" + resultfotoname);
                        txt.write(imageInByte);
//                        try
//                        {
//                            ImageIO.write(sourceImage, "bpm", new File(dirsavefoto + "\\" + resultfotoname));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                        Image image2 = null;
                        try {
                            image2 = new Image(new FileInputStream(dirsavefoto + "\\" + resultfotoname));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        TestIm2.setImage(image2);
                        Tf.appendText(FilesBMP[ImgRead]+"\n");
                        ImgRead ++;
                        Thread.sleep(0); //1000 - 1 сек
                        TextF2.setText("Состаяние: Обработано " + ImgRead);
                    } catch (InterruptedException | FileNotFoundException ex) {
                    } catch (IOException e) {
                        e.printStackTrace();
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
