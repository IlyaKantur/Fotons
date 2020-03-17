package sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControllerMain {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> MetodSpisok;

    @FXML
    private Button OneDim;

    @FXML
    private Button TwoDim;

    @FXML
    void initialize()
    {
        OneDim.setOnAction(actionEvent ->
        {
            
        });

        TwoDim.setOnAction(actionEvent ->
        {
            TwoDim.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/TwoDD/Scena/sample2DD.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Two Dimension");
            stage.showAndWait();
        });


    }
}


