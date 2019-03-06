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

import java.sql.Date;
import java.util.ArrayList;

public class FachadaAplicacion extends Application {

    public static final String fxmlPath = "../gui/FXML/";
    private FachadaGui fgui;
    private GestionUsuarios gu;
    private GesionLibros cl;
    private GestionCategorias cg;
    private GestionPrestamos gp;
    private FachadaBaseDatos fbd;
    private VPrincipalController cp;


    public FachadaAplicacion(){
        cp = new VPrincipalController(this);
        fgui = new FachadaGui(this, cp);
        fbd = new FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
        cl = new GesionLibros(fgui, fbd);
        cg = new GestionCategorias(fbd, fgui);
        gp = new GestionPrestamos(fbd, fgui);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath + "VPrincipal.fxml"));
        fxmlLoader.setController(cp);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Biblioteca de Informática");
        primaryStage.setScene(new Scene(root, 800, 600));
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

    public GestionCategorias getCg() {
        return cg;
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

    public void borrarEjemplares(Integer idLibro, java.util.List<Integer> ejemplares){
        cl.borrarEjemplares(idLibro, ejemplares);
    }

    public void borrarEjemplar(Integer idLibro, Integer numEjemplar){
        java.util.List<Integer> ejemplar = new ArrayList<>();
        ejemplar.add(numEjemplar);
        cl.borrarEjemplares(idLibro, ejemplar);
    }

    public void mostrarVentanaCategorias(){
        cg.verCategorias();
    }

    //Parte de prestamos

    public void abrirPrestamos(){
        cl.abrirPrestamos();
    }

    public Date getFechaLimitePrestamo(String idUsuario, Integer idLibro, Integer numEjemplar, Date fecha_prestamo) {
        return gp.getFechaLimitePrestamo(idUsuario, idLibro, numEjemplar, fecha_prestamo);
    }

    public java.util.List<Prestamo> consultarPrestamos(String idUsuario){
        return gp.consultarPrestamos(idUsuario);
    }


    public Prestamo consultarPrestamos(Integer idLibro, Integer numEjemplar) {
        return gp.consultarPrestamos(idLibro, numEjemplar);
    }

    public java.util.List<Prestamo> consultarPrestamos(){
        return gp.consultarPrestamos();
    }

    public java.util.List<Prestamo> consultarPrestamosVencidos(String idUsuario){
        return gp.consultarPrestamosVencidos(idUsuario);
    }

    public void introducirPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar){
        gp.introducirPrestamo(idUsuario, idLibro, numEjemplar);
    }

    public void devolverPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar) {
        gp.devolverPrestamo(idUsuario, idLibro, numEjemplar);
    }

    public boolean estaPrestado(Integer idLibro, Integer numEjemplar){
        return gp.estaPrestado(idLibro, numEjemplar);
    }




}
