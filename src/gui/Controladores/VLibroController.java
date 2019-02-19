package gui.Controladores;

import aplicacion.FachadaAplicacion;
import aplicacion.Libro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VLibroController{


    Button salirBtn;
    Stage vlibro;

    private Integer idLibro;
    private java.util.List<Integer> ejemplaresBorrados;
    private aplicacion.FachadaAplicacion fa;


    @FXML
    TextField textoTitulo;
    @FXML
    TextField textoIsbn;
    @FXML
    TextField textoEditorial;
    @FXML
    TextField textoPaginas;
    @FXML
    TextField textoAno;
    @FXML
    TextField textoId;
    @FXML
    TextField textoNuevoAutor;
    @FXML
    ListView<String> listaAutores;
    @FXML
    ListView<String> listaRestoCategorias;
    @FXML
    ListView<String> listaCategoriasLibro;
    @FXML
    Button btnActualizarCat;
    @FXML
    Button btnBorrar1;
    @FXML
    Button btnBorrar2;

    java.util.List<String> categorias;
    java.util.List<String> restoCategorias;

    public VLibroController(FachadaAplicacion fa,java.util.List<String> restoCategorias){
        this.fa = fa;
        this.idLibro=null;
        this.ejemplaresBorrados = new java.util.ArrayList<Integer>();
        listaRestoCategorias = new ListView<>();
        listaRestoCategorias.getItems().addAll(restoCategorias);
        this.restoCategorias = restoCategorias;
    }

    public VLibroController(FachadaAplicacion fa, java.util.List<String> categorias,java.util.List<String> restoCategorias){
        this.fa = fa;
        this.idLibro=null;
        this.ejemplaresBorrados = new java.util.ArrayList<Integer>();
        this.restoCategorias = restoCategorias;
        this.categorias = categorias;
    }

    public void display(){

        vlibro = new Stage();

        //Cargamos el FXML
        try {

            //Bloquea las otras ventanas
            vlibro.initModality(Modality.APPLICATION_MODAL);
            vlibro.setTitle("Gestión de Libros");
            vlibro.setMinWidth(250);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/FXML/VLibro.fxml"));
            //fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            Parent root1 = (Parent) fxmlLoader.load();
            vlibro.setScene(new Scene(root1));
            vlibro.show();
            //Añadimos la lista de categorías
            listaRestoCategorias.getItems().addAll(restoCategorias);
            listaCategoriasLibro.getItems().addAll(categorias);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Actualizar de la pestaña libros

    public void actualizarBtnAction(){
        Libro l;
        l=new Libro(idLibro, textoTitulo.getText(), textoIsbn.getText(), textoEditorial.getText(),
                Integer.parseInt(textoPaginas.getText()), textoAno.getText());
        l.setAutores(listaAutores.getItems());
        idLibro=fa.actualizarLibro(l);
        textoId.setText(idLibro.toString());
        btnActualizarCat.setDisable(false);
        //btnActualizarCategoriasLibro.setEnabled(true);
        //btnActualizarEjemplaresLibro.setEnabled(true);
        btnBorrar1.setDisable(false);
        btnBorrar2.setDisable(false);
    }


    public void nuevoAutor(){
        if ((textoNuevoAutor.getText()!=null) && !(textoNuevoAutor.getText().isEmpty())) {
            //Añadir autor a la lista
            listaAutores.getItems().addAll(textoNuevoAutor.getText());
        }
     }

     public void borrarAutor(){

         if(listaAutores.getSelectionModel().getSelectedItems().size() > 0){
             listaAutores.getItems().remove(listaAutores.getSelectionModel().getSelectedItem());
         }
    }

    public void salirAction(){
        vlibro.close();
    }

    public void botonDerechoAction(){
        if(listaRestoCategorias.getSelectionModel().getSelectedItems().size() > 0){
            listaCategoriasLibro.getItems().addAll(listaRestoCategorias.getSelectionModel().getSelectedItem());
            listaRestoCategorias.getItems().remove(listaRestoCategorias.getSelectionModel().getSelectedIndex());
        }
    }

    public void botonIzquierdoAction(){
        if(listaCategoriasLibro.getSelectionModel().getSelectedItems().size() > 0){
            listaRestoCategorias.getItems().addAll(listaCategoriasLibro.getSelectionModel().getSelectedItem());
            listaCategoriasLibro.getItems().remove(listaCategoriasLibro.getSelectionModel().getSelectedIndex());
        }
    }

    //Actualizar de la pestaña categorias
    public void btnActualizarCategoriasLibroAction() {
        fa.actualizarCategoriasLibro(idLibro, listaCategoriasLibro.getItems());
    }

   public void btnBorrarLibroAction() {
        if(idLibro != null){
            fa.borrarLibro(idLibro);
        }
        fa.getCp().buscarLibros();
        vlibro.close();
    }

}
