package gui.Controladores;

import aplicacion.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VGestionUsuariosController {

    private Stage guWindow;
    private FachadaAplicacion fa;

    //Componentes
    @FXML
    MenuButton selectorTipo;
    @FXML
    MenuItem adminSel;
    @FXML
    MenuItem normalSel;
    @FXML
    TextField usuarioBuscarTxt;
    @FXML
    TextField idBuscarTxt;

    @FXML
    TableView<Usuario> tablaUsuarios;
    @FXML
    TableColumn<Usuario, Integer> idCol;
    @FXML
    TableColumn<Usuario, String> nombreCol;
    @FXML
    TableColumn<Usuario, String> emailCol;
    @FXML
    TableColumn<Usuario, TipoUsuario> tipoCol;


    public VGestionUsuariosController(FachadaAplicacion fa){
        guWindow = new Stage();
        guWindow.initModality(Modality.APPLICATION_MODAL);
        this.fa = fa;
    }

    public void display(){

        //Cargamos el FXML
        try {
            guWindow.setTitle("Iniciar Sesion");
            guWindow.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VGestionUsuarios.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            guWindow.setScene(new Scene(root1));
            guWindow.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void btnSalirAction(){
        guWindow.close();
    }

    public void clickAdmin(){
        selectorTipo.setText(adminSel.getText());
    }
    public void clickNormal(){
        selectorTipo.setText(normalSel.getText());
    }

    //Busqueda de usuarios y representacion en tabla
    public void mostrarUsuarios(){

        idCol.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        java.util.List<Usuario> listaUsuarios= fa.getGu().obtenerUsuarios();
        // ((buscaId.getText().isEmpty())?null:Integer.parseInt(buscaId.getText()), buscaTitulo.getText(), buscaIsbn.getText(), buscaAutor.getText());
        javafx.collections.ObservableList<Usuario> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaUsuarios);
        System.out.println(listaFinal.get(0).getTipoUsuario());
        tablaUsuarios.setItems(listaFinal);
    }
}
