package gui.Controladores;

import aplicacion.Categoria;
import aplicacion.FachadaAplicacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class VGestionCategoriasController {

    Stage gCategorias;
    FachadaAplicacion fa;

    //Componentes

    @FXML
    TextField nombreCategoria;
    @FXML
    TextField descripcionCategoria;
    @FXML
    ListView<String> listaCategorias;

    public VGestionCategoriasController(FachadaAplicacion fa){
        this.gCategorias = new Stage();
        this.fa = fa;
    }


    public void display(){

        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            gCategorias.initModality(Modality.APPLICATION_MODAL);
            gCategorias.setTitle("Gestión de Categorías");
            gCategorias.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VGestionCategorias.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            gCategorias.setScene(new Scene(root1));
            //mostramos las categorias
            this.mostrarCategoriasExistentes();
            gCategorias.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void mostrarCategoriasExistentes(){

        ArrayList<String> nombreCategorias =  new ArrayList<>();

        for(Categoria cat : fa.getCg().obtenerCategorias()){
            nombreCategorias.add(cat.getNombre());
        }

        ObservableList<String> lista = FXCollections.observableList(nombreCategorias);
        listaCategorias.setItems(lista);
    }

    public void anhadirCategoria(){

        if(!nombreCategoria.getText().isEmpty() && !descripcionCategoria.getText().isEmpty()){
            listaCategorias.getItems().addAll(nombreCategoria.getText());
            fa.getCg().anhadirCategoria(new Categoria(nombreCategoria.getText(),
                    descripcionCategoria.getText()));
        }else{
            //Mostrar la excepcion
        }

    }

    public void borrarSeleccionado(){

        if(listaCategorias.getSelectionModel().getSelectedItems().size() > 0 && !fa.getCg().consultarCategoriaEnUso(
                listaCategorias.getSelectionModel().getSelectedItem()
        )){
            fa.getCg().borrarCategoria(listaCategorias.getSelectionModel().getSelectedItem());
            listaCategorias.getItems().remove(listaCategorias.getSelectionModel().getSelectedItem());
        }

    }


    public void salir(){
        this.gCategorias.close();
    }
}
