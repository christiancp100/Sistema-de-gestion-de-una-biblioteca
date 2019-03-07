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

import java.sql.SQLException;
import java.util.ArrayList;

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
    TextField idTxt;
    @FXML
    TextField nombreTxt;
    @FXML
    TextField claveTxt;
    @FXML
    TextField direccionTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TableView<Usuario> tablaUsuarios;
    @FXML
    TableColumn<Usuario, Integer> idCol;
    @FXML
    TableColumn<Usuario, String> nombreCol;
    @FXML
    TableColumn<Usuario, String> emailCol;
    @FXML
    TableColumn<Usuario, String> tipoCol;
    @FXML
    CheckBox verContrasena;
    @FXML
    Label mostrarContrasena;


    public VGestionUsuariosController(FachadaAplicacion fa){
        guWindow = new Stage();
        guWindow.initModality(Modality.APPLICATION_MODAL);
        this.fa = fa;
    }

    public void display(){

        //Cargamos el FXML
        try {
            guWindow.setTitle("Gestion Usuarios");
            guWindow.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VGestionUsuarios.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            guWindow.setScene(new Scene(root1));
            mostrarUsuarios();
            verContrasena.setDisable(true);
            //No mostramos la contrasena por defecto
            mostrarContrasena.setVisible(false);
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



    private void crearColumnas() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoStr"));
    }

    public void buscarUsuarios() throws SQLException {

        crearColumnas();

        // ((buscaId.getText().isEmpty())?null:Integer.parseInt(buscaId.getText()), buscaTitulo.getText(), buscaIsbn.getText(), buscaAutor.getText());

        java.util.List<Usuario> listaUsuarios= fa.getGu().filtrarUsuarios(idBuscarTxt.getText(), usuarioBuscarTxt.getText());
        javafx.collections.ObservableList<Usuario> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaUsuarios);
        tablaUsuarios.setItems(listaFinal);
    }


    //Busqueda de usuarios y representacion en tabla
    public void mostrarUsuarios(){

        crearColumnas();

        java.util.List<Usuario> listaUsuarios= fa.getGu().obtenerUsuarios();
        javafx.collections.ObservableList<Usuario> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaUsuarios);
        tablaUsuarios.setItems(listaFinal);
        tablaUsuarios.getSelectionModel().selectFirst();
    }

    public void nuevoUsuario(){
        if(!idTxt.getText().isEmpty() &&
                !nombreTxt.getText().isEmpty() &&
                !emailTxt.getText().isEmpty() &&
                !nombreTxt.getText().isEmpty() &&
                !claveTxt.getText().isEmpty() &&
                !direccionTxt.getText().isEmpty() &&
                !selectorTipo.getText().equals("Tipo")
        ){

            Usuario usuario = obtenerUsuario();
            fa.getGu().crearUsuario(usuario);
        }
    }

    public void editarUsuario(){
        verContrasena.setDisable(false);
        if(tablaUsuarios.getSelectionModel().getSelectedItems().size() > 0){
            Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
            idTxt.setText(usuario.getIdUsuario());
            claveTxt.setText(usuario.getClave());
            nombreTxt.setText(usuario.getNombre());
            emailTxt.setText(usuario.getEmail());
            selectorTipo.setText(usuario.getTipoStr());
            direccionTxt.setText(usuario.getDireccion());
        }
    }

    public void actualizarUsuario(){
        Usuario usuario = obtenerUsuario();
        if(usuario.getIdUsuario() != null){
            fa.getGu().actualizarUsuario(usuario);
        }
    }

    public void borrarUsuario(){
        Usuario usuario = obtenerUsuario();
        if(usuario.getIdUsuario() != null){
            fa.getGu().borrarUsuario(usuario);
            mostrarUsuarios();
        }
    }


    public void clickNuevo(){
        idTxt.setText("");
        claveTxt.setText("");
        nombreTxt.setText("");
        direccionTxt.setText("");
        emailTxt.setText("");
        selectorTipo.setText("Tipo");
        verContrasena.setDisable(true);

    }
    //Auxiliares

    public Usuario obtenerUsuario(){
        return new Usuario(
                idTxt.getText(),
                claveTxt.getText(),
                nombreTxt.getText(),
                direccionTxt.getText(),
                emailTxt.getText(),
                TipoUsuario.valueOf(selectorTipo.getText())
        );
    }

    public void verContrasenaAction(){

        if(verContrasena.isSelected() && !claveTxt.getText().isEmpty()){

            claveTxt.setVisible(false);
            mostrarContrasena.setVisible(true);
            mostrarContrasena.setStyle("-fx-border-color: grey;\n" +
                    "    -fx-border-width: 0 0 1 0; ");
            mostrarContrasena.setText(claveTxt.getText());
        }else{
            claveTxt.setVisible(true);
            mostrarContrasena.setVisible(false);
        }
    }

    public void activarCheckbox(){
        if(claveTxt.getText().isEmpty()){
            verContrasena.setDisable(true);
        }else{
            verContrasena.setDisable(false);
        }
    }

    public void guardarAction() throws SQLException {

        try{
            java.util.List<Usuario> usuarios = fa.getGu().filtrarUsuarios(idTxt.getText(), null);
            //En este caso estarias
            if(usuarios.size() >= 1){
                actualizarUsuario();
            }else{
                nuevoUsuario();
            }
            mostrarUsuarios();
        }catch (Exception e){
            fa.muestraExcepcion("Error, no se ha podido guardar", 0);
        }

    }


}
