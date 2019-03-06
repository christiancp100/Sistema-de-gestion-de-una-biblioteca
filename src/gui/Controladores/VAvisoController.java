package gui.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class VAvisoController {

    private Stage aviso;

    private String mensaje;
    private int tipo;

    @FXML
    Label mensajeTxt;
    @FXML
    BorderPane fondo;

    //En el tipo de aviso, suponemos que 1 es una ejecucion correcta y 0 un error
    public VAvisoController(String mensaje, int tipo){
        this.mensaje = mensaje;
        this.tipo = tipo;
    }

    public void display(){

        aviso = new Stage();

        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            aviso.initModality(Modality.APPLICATION_MODAL);
            aviso.setTitle("AVISO");
            aviso.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VAviso.fxml"));
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            aviso.setScene(new Scene(root1, 300, 150));
            aviso.show();

            mensajeTxt.setText(mensaje);

            if(tipo==0){
                fondo.setStyle("-fx-background-color:#f4414a;");
            }else{
                fondo.setStyle("-fx-background-color:#42f48c ;");
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void cerrarVentana(){
        aviso.close();
    }
}
