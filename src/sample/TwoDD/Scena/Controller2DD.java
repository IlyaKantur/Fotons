package sample.TwoDD.Scena;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main.ControllerMain;
import sample.Main.Main;
import sample.TwoDD.Class.StringLengthSort;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private AnchorPane AP1;

    @FXML
    private LineChart<String, Number> LC;



    public int ImgRead = 0;
    public int iterO = 0;
    public int IterFinal;
    public int width;
    public int height;
    public String fgetPath;
    public String dirsavefoto;
    public Container filegetParent;
    public boolean finalrun = false;
    public boolean prog = true;
    public BufferedImage imagesum = null;
    public int[] mas = null;
    public String[] FilesBMP;

    @FXML
    void initialize() throws IOException
    {
//        var xAxis = new NumberAxis();
//        xAxis.setLabel("NPixel");
//        var yAxis = new NumberAxis();
//        yAxis.setLabel("Intensiveness");
        var data = new XYChart.Series<Number,Number>();
//        LC.getData().add(data);
        data.setName("Data Experiment");
        Fold.setOnAction(actionEvent ->
        {
            Fold.getScene().getWindow().hide();
            JFileChooser file = new JFileChooser();
            file.setMultiSelectionEnabled(true);
            file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            file.setFileHidingEnabled(false);

            if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
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
            File filImm = new File(fgetPath + "\\" + FilesBMP[0]);
            try {
                imagesum = ImageIO.read(filImm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String maindir = System.getProperty("user.dir");
            dirsavefoto = maindir + "\\src\\sample\\TwoDD\\result\\Foto";
            finalrun = true;
        });

        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(finalrun)
                {
                    try {
                        data.getData().clear();
                        if(iterO == 1) IterFinal = Integer.valueOf(MaxIter.getText());
                        else IterFinal = FilesBMP.length;
                        String path = fgetPath + "\\" + FilesBMP[ImgRead];
                        String resultfotoname = "resultImage.bmp";
                        File filIm = new File(fgetPath + "\\" + FilesBMP[ImgRead]);
                        //       Вывод фото на экран фото на экран
                        Image image1 = null;
                        try {
                            image1 = new Image(new FileInputStream(path));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        TestIm1.setImage(image1);
                        //       Загрузка изображения для обработки
                        BufferedImage sourceImage = null;
                        try {
                            sourceImage = ImageIO.read(filIm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        width = sourceImage.getWidth();
                        height = sourceImage.getHeight();
                        if (ImgRead == 0) mas = new int[width];
                        int[][] mas0 = new int[width][height];
                        int[][] mas0c = new int[width][height];

                        //       Алгоритм заполнения массива интенсивнотси, суммирование снимка
                        Color cblack = new Color(sourceImage.getRGB(0,0));
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
                                       if(!BF.isSelected()) mas0c[x][y] -= (cblack.getRed() + cblack.getBlue() + cblack.getGreen()) / 3;

                                }
                                if (c2.getRed() < c.getRed() || c2.getGreen() < c.getGreen() || c2.getBlue() < c.getBlue())
                                    imagesum.setRGB(x, y, c.getRGB());
                            }
                        }
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                mas[x] += mas0c[x][y];
                            }
                                data.getData().add(new XYChart.Data<Number, Number>(x,mas[x]));
                        }
                        // сохрание нового изображения
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(imagesum, "bmp", baos);
                            baos.flush();
                            byte[] imageInByte = baos.toByteArray();
                            baos.close();
                            OutputStream txt = new FileOutputStream(dirsavefoto + "\\" + resultfotoname);
                            txt.write(imageInByte);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Image image2 = null;
                        try {
                            image2 = new Image(new FileInputStream(dirsavefoto + "\\" + resultfotoname));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        TestIm2.setImage(image2);
                        Tf.appendText(FilesBMP[ImgRead] + "\n");
                        ImgRead++;
                        TextF2.setText("Состаяние: Обработано " + ImgRead);
                        if(ImgRead == IterFinal) finalrun = false;
                        Thread.sleep(1); //1000 - 1 сек
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });

        Start.setOnAction(actionEvent ->
        {
            Start.getScene().getWindow().hide();
            Stage primaryStage = new Stage();
//            initUI(primaryStage);
            init2(primaryStage);
//            run.start();
            if(Iter.isSelected()) iterO = 1;
        });
    }
//    public void initUI(Stage primaryStage) {
//        LC.setAnimated(false);
//        LC.getData().add(new XYChart.Series<>("Temp1", FXCollections.observableArrayList(new XYChart.Data<Object, Object>(1, 1), new XYChart.Data<Object, Object>(2, 2), new XYChart.Data<Object, Object>(3, 4))));
//        ((XYChart.Series) LC.getData().get(0)).getData().addAll(new XYChart.Data<>(5, 5));
//        ObservableList<XYChart.Data> dataList = ((XYChart.Series) LC.getData().get(0)).getData();
//        dataList.forEach(data->{
//            Node node = data.getNode();
//            Tooltip tooltip = new Tooltip('('+data.getXValue().toString()+';'+data.getYValue().toString()+')');
//            Tooltip.install(node, tooltip);
//        });
//
//    }

    ObservableList<XYChart.Data<String, Integer>> xyList1 = FXCollections.observableArrayList();
    ObservableList<String> myXaxisCategories = FXCollections.observableArrayList();

    int i;
    private Task<Date> task;
    private XYChart.Series xySeries1;
    public CategoryAxis xAxis;
    private int lastObservedSize;
    private LineChart<String,Number> lineChart;


    public void init2(Stage primatyStage)
    {
        xyList1.addListener((ListChangeListener<XYChart.Data<String, Integer>>) change -> {
            if (change.getList().size() - lastObservedSize > 2) {
                lastObservedSize += 2;
                xAxis.getCategories().remove(0,1);
            }
        });

        primatyStage.setTitle("Line Chart Sample");
        xAxis = new CategoryAxis();
        xAxis.setLabel("Month");

        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Woohoo, 2010");
        lineChart.setAnimated(false);

        task = new Task<Date>() {

            @Override
            protected Date call() throws Exception {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException iex) {
                        Thread.currentThread().interrupt();
                    }
                    if (isCancelled()) {
                        break;
                    }
                    updateValue(new Date());
                }
                return new Date();
            }
        };

        task.valueProperty().addListener(new ChangeListener<Date>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Random random = new Random();

            @Override
            public void changed(ObservableValue<? extends Date> observableValue, Date oldDate, Date newDate) {
                String strDate = dateFormat.format(newDate);
                myXaxisCategories.add(strDate);

                for(int i = 0; i < 2; i++)
                {
                    xyList1.add(new XYChart.Data(strDate, Integer.valueOf(newDate.getMinutes() + random.nextInt(10) + i)));
                }
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);

        Scene scene  = new Scene(lineChart,800,600);

        xAxis.setCategories(myXaxisCategories);
//        xAxis.setAutoRanging(false);

        xySeries1 = new XYChart.Series(xyList1);
        xySeries1.setName("Series 1");

        lineChart.getData().addAll(xySeries1);

        i = 0;
        primatyStage.setScene(scene);
        primatyStage.show();

        primatyStage.setOnCloseRequest(windowEvent -> {

            task.cancel();

        });
    }

//    public void lineChartOnScroll(ScrollEvent event) {
//        NumberAxis axis;
//        // scroll amount
//        double translateX = (event.getDeltaX())/40;
//        double translateY = event.getDeltaY()/40;
//
//
//        double mousX = LC.getXAxis().getValueForDisplay(event.getX() - LC.getXAxis().getLayoutX()).doubleValue();
//        double mousY = LC.getYAxis().getValueForDisplay(event.getY() - LC.getYAxis().getLayoutY()).doubleValue();
//
//        if (zoomX.isSelected()) {
//            axis = (NumberAxis) LC.getXAxis();
//            axis.setAutoRanging(false);
//            double delta  = (mousX - axis.getLowerBound());
//            double delta1 = (axis.getUpperBound() - mousX);
//            double zoomFactor = 2;
//
//            if (translateY > 0) {
//                delta = delta / zoomFactor;
//                delta1 = delta1 / zoomFactor;
//            }
//            else {
//                delta = delta * zoomFactor;
//                delta1 = delta1 * zoomFactor;
//            }
//            axis.setLowerBound(mousX - delta);
//            axis.setUpperBound(mousX + delta1);
//        }
//        if (zoomY.isSelected()) {
//
//        }
//
//        event.consume();
//    }
}
