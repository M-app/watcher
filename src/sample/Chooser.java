package sample;

import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Chooser {

    public static String seleccionarCarpeta(Pane pane){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) pane.getScene().getWindow();
        File directorioSeleccionado = directoryChooser.showDialog(stage);
        if (directorioSeleccionado != null){
            return directorioSeleccionado.getAbsolutePath();
        }else{
            return "";
        }
    }
}
