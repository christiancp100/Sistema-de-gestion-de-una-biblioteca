package gui.Controladores;

import aplicacion.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class VLibroController{


    Button salirBtn;
    Stage vlibro;

    private Integer idLibro;
    private java.util.List<Integer> ejemplaresBorrados;
    private aplicacion.FachadaAplicacion fa;
    private ArrayList<Integer> borrados;

    //libro
    private Libro libroActual;

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

    //Componentes de ejemplares
    @FXML
    TableView<Ejemplar> tablaEjemplares;
    @FXML
    TableColumn<Ejemplar, Integer> idCol;
    @FXML
    TableColumn<Ejemplar, String> localizadorCol;
    @FXML
    TableColumn<Ejemplar, String> anoCompraCol;
    @FXML
    TableColumn<Ejemplar, String> usuarioCol;
    @FXML
    TableColumn<Ejemplar, String> fechaDevCol;
    @FXML
    Button btnPrestar;
    @FXML
    Button btnDevolver;

    java.util.List<String> categorias;
    java.util.List<String> restoCategorias;

    public VLibroController(FachadaAplicacion fa,java.util.List<String> restoCategorias){
        this.fa = fa;
        this.idLibro=null;
        this.ejemplaresBorrados = new java.util.ArrayList<Integer>();
        listaRestoCategorias = new ListView<>();
        listaRestoCategorias.getItems().addAll(restoCategorias);
        this.restoCategorias = restoCategorias;
        borrados = new ArrayList<>();
    }

    public VLibroController(FachadaAplicacion fa, java.util.List<String> categorias,java.util.List<String> restoCategorias){
        this.fa = fa;
        this.idLibro=null;
        this.ejemplaresBorrados = new java.util.ArrayList<Integer>();
        this.restoCategorias = restoCategorias;
        this.categorias = categorias;
        borrados = new ArrayList<>();
    }


    public VLibroController(aplicacion.FachadaAplicacion fa, Libro libro, java.util.List<String> categorias, java.util.List<String> restoCategorias) {
        this.fa = fa;
        this.idLibro=libro.getIdLibro();
        this.ejemplaresBorrados = new java.util.ArrayList<Integer>();
        listaRestoCategorias = new ListView<>();
        listaRestoCategorias.getItems().addAll(restoCategorias);
        this.restoCategorias = restoCategorias;
        borrados = new ArrayList<>();
    }

    public void display(Libro libro){

        libroActual = libro;

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
            //Cargamos los ejemplares
            this.mostrarEjemplares();
            vlibro.show();

            btnDevolver.setDisable(true);
            btnPrestar.setDisable(true);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        //Añadimos la lista de categorías
        listaRestoCategorias.getItems().addAll(restoCategorias);
        //Añadimos todos los campos del libro seleccionado en los campos de edicion
        textoTitulo.setText(libro.getTitulo());
        textoId.setText(libro.getIdLibro().toString());
        textoPaginas.setText(libro.getPaginas().toString());
        textoEditorial.setText(libro.getEditorial());
        textoIsbn.setText(libro.getIsbn());
        textoAno.setText(libro.getAno());

        btnActualizarCat.setDisable(false);

        //Representamos las categorias

        ArrayList<String> categorias = new ArrayList<>();

        for(Categoria cat : libro.getCategorias()){
            categorias.add(cat.getNombre());
        }
        ObservableList<String> categoriasLibro = FXCollections.observableList(categorias);
        listaCategoriasLibro.setItems(categoriasLibro);

        //Representamos los autores

        ArrayList<String> autores = new ArrayList<>();

        for(String autor : libro.getAutores()){
            autores.add(autor);
        }
        ObservableList<String> autoresFinal = FXCollections.observableList(autores);
        listaAutores.setItems(autoresFinal);

        //Activamos los botones de borrar el libro
        btnBorrar1.setDisable(false);
        btnBorrar2.setDisable(false);

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
            //Cargamos los ejemplares
            this.mostrarEjemplares();
            tablaEjemplares.getSelectionModel().select(0);
            this.desactivarPrestar();
            this.desactivarDevolver();
            
            btnDevolver.setDisable(true);
            btnPrestar.setDisable(true);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void desactivarPrestar(){
        if(tablaEjemplares.getSelectionModel().getSelectedItem() != null){
            Ejemplar ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();
            if(fa.estaPrestado(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar())){
                btnPrestar.setDisable(true);
            }else{
                btnPrestar.setDisable(false);
            }
        }else{
            btnPrestar.setDisable(false);
        }
    }

    public void desactivarDevolver(){
        if(tablaEjemplares.getSelectionModel().getSelectedItem() != null){
            Ejemplar ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();
            if(!fa.estaPrestado(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar())){
                btnDevolver.setDisable(true);
            }else{
                btnDevolver.setDisable(false);
            }
        }else{
            btnDevolver.setDisable(false);
        }
    }

    public void clickTabla(){
        this.desactivarPrestar();
        this.desactivarDevolver();
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
        btnBorrar1.setDisable(false);
        btnBorrar2.setDisable(false);
    }


    public void nuevoAutor(){
        if ((textoNuevoAutor.getText()!=null) && !(textoNuevoAutor.getText().isEmpty())) {
            //Añadir autor a la lista
            listaAutores.getItems().addAll(textoNuevoAutor.getText());
        }else{
            fa.muestraExcepcion("No se pudo añadir el autor", 0);
        }
     }

     public void borrarAutor(){

         if(listaAutores.getSelectionModel().getSelectedItems().size() > 0){
             listaAutores.getItems().remove(listaAutores.getSelectionModel().getSelectedItem());

         }else{
             fa.muestraExcepcion("Ningún autor seleccionado", 0);
         }
    }

    public void salirAction(){
        fa.getCp().buscarLibros();
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


    //Parte de los ejemplares

    public void mostrarEjemplares(){

        boolean prestado;
        Prestamo prestamo;

        idCol.setCellValueFactory(new PropertyValueFactory<>("numEjemplar"));
        localizadorCol.setCellValueFactory(new PropertyValueFactory<>("localizador"));
        anoCompraCol.setCellValueFactory(new PropertyValueFactory<>("anoCompra"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fechaDevCol.setCellValueFactory(new PropertyValueFactory<>("fechaDev"));

        tablaEjemplares.setEditable(true);
        localizadorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        anoCompraCol.setCellFactory(TextFieldTableCell.forTableColumn());


        java.util.List<Ejemplar> listaEjemplares = libroActual.getEjemplares();


        for(Ejemplar ejemplar : listaEjemplares){
            prestado = fa.estaPrestado(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar());
            if(prestado){
                prestamo = fa.consultarPrestamos(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar());
                ejemplar.setUsuario(prestamo.getIdUsaurio());

                ejemplar.setFechaDev(fa.getFechaLimitePrestamo(ejemplar.getUsuario(),
                        ejemplar.getLibro().getIdLibro(),
                        ejemplar.getNumEjemplar(),
                        prestamo.getFecha_prestamo()));
            }else{
                ejemplar.setUsuario(null);
                ejemplar.setFechaDev(null);
            }
         }

        javafx.collections.ObservableList<Ejemplar> listaFinal = FXCollections.observableArrayList();
        listaFinal.addAll(listaEjemplares);
        tablaEjemplares.setItems(listaFinal);
    }

    public void edicionDatosLocalizador(TableColumn.CellEditEvent<Ejemplar, String> productStringCellEditEvent ){
       Ejemplar ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();
       ejemplar.setLocalizador(productStringCellEditEvent.getNewValue());
    }

    public void edicionDatosAnoCompra(TableColumn.CellEditEvent<Ejemplar, String> productStringCellEditEvent ){
        Ejemplar ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();
        ejemplar.setAnoCompra(productStringCellEditEvent.getNewValue());
    }

    public void nuevoEjemplar(){
        tablaEjemplares.getItems().add(
                new Ejemplar(libroActual, null ,"", "")
        );
        tablaEjemplares.getSelectionModel().selectLast();
    }

    public void actualizarEjemplaresAction(){

        fa.getCl().actualizarEjemplaresLibro(
                libroActual.getIdLibro(),
                tablaEjemplares.getItems(),
                borrados);
    }

    public void borrarEjemplaresAction(){

        if(tablaEjemplares.getSelectionModel().getSelectedItem() == null){
            tablaEjemplares.getSelectionModel().select(0);
        }

        Ejemplar ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();

        //Comprobamos que no esta prestado
        if(!fa.estaPrestado(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar())){
            //Eliminar el ejemplar
            fa.borrarEjemplar(ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar());
            tablaEjemplares.getItems().remove(ejemplar);
            System.out.println("Borrado");

        }else{
            //mostrar ventana de error
            fa.muestraExcepcion("Error borrando el ejemplar", 0);
        }

    }

    public void prestarBtnAction(){
        fa.abrirPrestamos();
    }

    public Ejemplar getEjemplarSeleccionado(){
        return tablaEjemplares.getSelectionModel().getSelectedItem();
    }


    public Prestamo ejemplarPrestado(Integer idLibro, Integer numEjemplar){
        return fa.consultarPrestamos(idLibro, numEjemplar);
    }

    public void devolverBtnAction(){
        Ejemplar ejemplar;

        if(tablaEjemplares.getSelectionModel().getSelectedItem() == null){
            tablaEjemplares.getSelectionModel().select(0);
        }

        ejemplar = tablaEjemplares.getSelectionModel().getSelectedItem();

        java.util.List<Prestamo> prestamos = fa.consultarPrestamos();
        for(Prestamo prestamo : prestamos){
            if(prestamo.getIdLibro()==ejemplar.getLibro().getIdLibro() &&
                    prestamo.getNumEjemplar()==ejemplar.getNumEjemplar()){
                ejemplar.setUsuario(prestamo.getIdUsaurio());
                fa.devolverPrestamo(ejemplar.getUsuario(), ejemplar.getLibro().getIdLibro(), ejemplar.getNumEjemplar());
                System.out.println("Préstamo devuelto con éxito");
                this.mostrarEjemplares();
                break;
            }
        }

    }


}
