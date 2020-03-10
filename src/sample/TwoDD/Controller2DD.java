package sample.TwoDD;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private ImageView TestIm;

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
                java.io.File f = file.getSelectedFile();
//                System.err.println(f.getPath());
//                Test.setText(f.getParent());
                FilesBMP = f.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".bmp");
                    }
                });
                TextF.setText("Коллическво файлов: " + FilesBMP.length);
                int i = 0;
                for(String e :FilesBMP)
                {
                    Tf.appendText(e+"\n");
                    i++;
                }
            }
            Fold.getScene().getWindow().hide();
        });
    }
}
