package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private GridPane panelWatcher;

    @FXML
    private Button btnSeleccionar;

    @FXML
    private Label lblRuta;

    @FXML
    private ToggleButton btnEncender;

    private String rutaCarpeta = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       btnSeleccionar.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               rutaCarpeta = Chooser.seleccionarCarpeta(panelWatcher);
               lblRuta.setText(rutaCarpeta);
               if (!rutaCarpeta.isEmpty())
                    btnEncender.setDisable(false);
           }
       });
       btnEncender.selectedProperty().addListener((observable, oldValue, newValue) -> {
           Watcher watcher = new Watcher(rutaCarpeta);
           Thread thread = new Thread(watcher);
           if (btnEncender.isSelected()){
               thread.start();
           }
       });
    }
}