package gui.Controladores;

import aplicacion.FachadaAplicacion;
import aplicacion.Libro;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.text.Element;
import java.util.ArrayList;

public class VPrincipalController {
    VAutenticacionController va;
    FachadaAplicacion fa;
    VLibroController vl;
    //Componentes
    @FXML
    TableView<Libro> tablaLibros;
    @FXML
    TableColumn<Libro, Integer> idCol;
    @FXML
    TableColumn<Libro, String> autoresCol;
    @FXML
    TableColumn<Libro, String> tituloCol;
    @FXML
    TableColumn<Libro, String> editorialCol;
    @FXML
    TableColumn<Libro, String> anoCol;
    @FXML
    private TextField buscaId;
    @FXML
    private TextField buscaTitulo;
    @FXML
    private TextField buscaIsbn;
    @FXML
    private TextField buscaAutor;
    @FXML
    private Label etiquetaSesion;
    @FXML
    public Button botonNuevo;

    //Constructor

    public VPrincipalController(FachadaAplicacion fa){
        this.fa = fa;
    }

    public void botonNuevoAccion(ActionEvent event) {
        fa.nuevoLibro();
    }

    public void setEtiquetaSesion(String nombre){
        etiquetaSesion.setText(nombre);
    }

    public void salirAction(){
        System.exit(0);
    }

    public void buscarLibros(){

        idCol.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("idLibro"));
        autoresCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("autores"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        editorialCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        anoCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("ano"));


        java.util.List<Libro> listaLibros= fa.obtenerLibros((buscaId.getText().isEmpty())?null:Integer.parseInt(buscaId.getText()), buscaTitulo.getText(), buscaIsbn.getText(), buscaAutor.getText());

        javafx.collections.ObservableList<Libro> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaLibros);
        tablaLibros.setItems(listaFinal);
    }


}
