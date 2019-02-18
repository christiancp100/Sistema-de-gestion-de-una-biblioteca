package gui.Controladores;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VAvisoController {

    public void display(){

        Stage aviso = new Stage();

        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            aviso.initModality(Modality.APPLICATION_MODAL);
            aviso.setTitle("Iniciar Sesion");
            aviso.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VAviso.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            aviso.setScene(new Scene(root1));
            aviso.showAndWait();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        aviso.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }
}
