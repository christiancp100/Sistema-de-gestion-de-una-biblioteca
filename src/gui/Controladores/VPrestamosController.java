package gui.Controladores;

import aplicacion.Ejemplar;
import aplicacion.FachadaAplicacion;
import aplicacion.Prestamo;
import aplicacion.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class VPrestamosController {

    private Stage vprestamos;
    private FachadaAplicacion fa;
    private Ejemplar ejemplar;
    private VLibroController vLibro;

    @FXML
    TableView<Usuario> tablaPrestamos;
    @FXML
    TableColumn<Usuario, String> idCol;
    @FXML
    TableColumn<Usuario, String> nombreCol;
    @FXML
    TableColumn<Usuario, String> emailCol;
    @FXML
    TableColumn<Usuario, Integer> prestamoVencidoCol;
    @FXML
    TextField idBuscarTxt;
    @FXML
    TextField usuarioBuscarTxt;


    public VPrestamosController(FachadaAplicacion fa, VLibroController vLibro){
        this.vLibro = vLibro;
        this.ejemplar = vLibro.getEjemplarSeleccionado();
        this.fa = fa;
    }

    public void display(){

        vprestamos = new Stage();

        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            vprestamos.initModality(Modality.APPLICATION_MODAL);
            vprestamos.setTitle("Gestión de Prestamos");
            vprestamos.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VPrestamos.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            vprestamos.setScene(new Scene(root1));
            vprestamos.show();

            //Ponemos usuarios en la tabla
            this.mostrarUsuarios();


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void salirBtnAction(){
        vprestamos.close();
    }

    //funciones auxiliares


    private void crearColumnas() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        prestamoVencidoCol.setCellValueFactory(new PropertyValueFactory<>("prestamosVencidos"));
    }

    public void mostrarUsuarios(){


        crearColumnas();

        java.util.List<Usuario> listaUsuarios= fa.getGu().obtenerUsuarios();

        asignarVencidosCelda(listaUsuarios);

        javafx.collections.ObservableList<Usuario> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaUsuarios);
        tablaPrestamos.setItems(listaFinal);
        tablaPrestamos.getSelectionModel().selectFirst();
    }

    public void buscarUsuarios() throws SQLException {

        crearColumnas();

        // ((buscaId.getText().isEmpty())?null:Integer.parseInt(buscaId.getText()), buscaTitulo.getText(), buscaIsbn.getText(), buscaAutor.getText());

        java.util.List<Usuario> listaUsuarios= fa.getGu().filtrarUsuarios(idBuscarTxt.getText(), usuarioBuscarTxt.getText());
        asignarVencidosCelda(listaUsuarios);
        javafx.collections.ObservableList<Usuario> listaFinal = FXCollections.observableArrayList();

        listaFinal.addAll(listaUsuarios);
        tablaPrestamos.setItems(listaFinal);
    }



    public void prestarAction(){
        Usuario usuario;
        if(tablaPrestamos.getSelectionModel().getSelectedItem() == null){
            tablaPrestamos.getSelectionModel().select(0);
        }
        usuario = tablaPrestamos.getSelectionModel().getSelectedItem();

        if(fa.consultarPrestamosVencidos(usuario.getIdUsuario()).size() > 0){
            System.out.println("No se puede realizar el prestamo");
        }else{
            if(!ejemplarPrestado(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar())){
                System.out.println("Se puede realizar el prestamo");
                fa.introducirPrestamo(usuario.getIdUsuario(), ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar());
            }else{
                System.out.println("El prestamo no puede efectuarse, ejemplar prestado");
                //Ventana de error
            }
        }
        vLibro.mostrarEjemplares();
    }


    //Auxiliares

    public void asignarVencidosCelda(java.util.List<Usuario> listaUsuarios){
        for(Usuario usuario : listaUsuarios){
            usuario.setPrestamosVencidos(fa.consultarPrestamosVencidos(usuario.getIdUsuario()).size());
        }
    }

    public boolean ejemplarPrestado(Integer idLibro, Integer numEjemplar){

        java.util.List<Prestamo> ejemplaresPrestados = fa.consultarPrestamos();
        for(Prestamo prestamo : ejemplaresPrestados){
            if(prestamo.getIdLibro()==idLibro && prestamo.getNumEjemplar()==numEjemplar){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
