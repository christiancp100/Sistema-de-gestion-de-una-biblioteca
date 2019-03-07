package gui.Controladores;

import aplicacion.FachadaAplicacion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class VAutenticacionController {


    private aplicacion.FachadaAplicacion fa;
    private VPrincipalController cp;

    @FXML
    private Button entrar;
    @FXML
    public Button cancelar;
    @FXML
    public TextField usuariotxt;
    @FXML
    public TextField passwordtxt;



    public VAutenticacionController(FachadaAplicacion fa, VPrincipalController cp){
        this.fa = fa;
        this.cp = cp;
    }

    public void display(){

        Stage autenticacion = new Stage();


        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            autenticacion.initModality(Modality.APPLICATION_MODAL);
            autenticacion.setTitle("Iniciar Sesion");
            autenticacion.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VAutenticacion.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            autenticacion.setScene(new Scene(root1));
            autenticacion.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        autenticacion.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }


    public void closeOnCancel(){
        System.exit(0);
    }




    public void entrarAccion() throws InterruptedException {

        System.out.println(usuariotxt.getText());

        if(fa.comprobarAutentificacion(usuariotxt.getText(), passwordtxt.getText())){

            cp.setEtiquetaSesion(usuariotxt.getText());
            cp.buscarLibros();
            Stage stage = (Stage) entrar.getScene().getWindow();
            stage.close();
            System.out.println("Se ha realizado con éxito la autentificacion");

        }else{
            VAvisoController vAviso = new VAvisoController("Usuario o contraseña INCORRECTOS", 0);
            vAviso.display();
        }

    }
}
