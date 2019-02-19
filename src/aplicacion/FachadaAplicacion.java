package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.Controladores.VAutenticacionController;
import gui.Controladores.VAvisoController;
import gui.Controladores.VPrincipalController;
import gui.FachadaGui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FachadaAplicacion extends Application {

    public static final String fxmlPath = "../gui/FXML/";
    private FachadaGui fgui;
    private GestionUsuarios gu;
    private GesionLibros cl;
    private FachadaBaseDatos fbd;
    private VPrincipalController cp;


    public FachadaAplicacion(){
        cp = new VPrincipalController(this);
        fgui = new FachadaGui(this, cp);
        fbd = new FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
        cl = new GesionLibros(fgui, fbd);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath + "VPrincipal.fxml"));
        fxmlLoader.setController(cp);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Biblioteca de Informática");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        fgui.iniciaVista();
    }


    public static void main(String[] args) {
        launch(args);
    }


    //Getters
    public VPrincipalController getCp() {
        return cp;
    }

    public FachadaGui getFgui() {
        return fgui;
    }

    public GesionLibros getCl() {
        return cl;
    }

    public GestionUsuarios getGu() {
        return gu;
    }

    //Metodos
    public void muestraExcepcion(String txtExcepcion){
        fgui.muestraExcepcion(txtExcepcion);
    }

    public Boolean comprobarAutentificacion(String idUsuario, String clave){
        return gu.comprobarAutentificacion(idUsuario, clave);
    }


    public java.util.List<Libro> obtenerLibros(Integer id, String titulo, String isbn, String autor){
        return cl.obtenerLibros(id, titulo,  isbn,  autor);
    };

    public void visualizarLibro(Integer idLibro){
        cl.visualizarLibro(idLibro);
    }

    public void nuevoLibro(){
        cl.nuevoLibro();
    }

    public Integer actualizarLibro(Libro l){
        return cl.actualizarLibro(l);
    }

    public void borrarLibro(Integer idLibro){
        cl.borrarLibro(idLibro);
    }

    public void actualizarCategoriasLibro(Integer idLibro, java.util.List<String> categorias){
        cl.actualizarCategoriasLibro(idLibro, categorias);
    }

    public java.util.List<Ejemplar> actualizarEjemplaresLibro(Integer idLibro, java.util.List<Ejemplar> ejemplares, java.util.List<Integer> borrar){
        return cl.actualizarEjemplaresLibro(idLibro, ejemplares, borrar);
    }
}
