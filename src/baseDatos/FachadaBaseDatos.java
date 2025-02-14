/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baseDatos;

import aplicacion.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author basesdatos
 */
public class FachadaBaseDatos {


    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOLibros daoLibros;
    private DAOCategorias daoCategorias;
    private DAOUsuarios daoUsuarios;
    private DAOPrestamos daoPrestamos;

    public FachadaBaseDatos (aplicacion.FachadaAplicacion fa){
        
        Properties configuracion = new Properties();
        this.fa=fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();
     

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion=java.sql.DriverManager.getConnection("jdbc:"+gestor+"://"+
                    configuracion.getProperty("servidor")+":"+
                    configuracion.getProperty("puerto")+"/"+
                    configuracion.getProperty("baseDatos"),
                    usuario);

            daoLibros = new DAOLibros(conexion, fa);
            daoCategorias = new DAOCategorias(conexion, fa);
            daoUsuarios = new DAOUsuarios(conexion, fa);
            daoPrestamos = new DAOPrestamos(conexion, fa);
          


        } catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage(), 0);
        } catch (IOException i){
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage(), 0);
        } 
        catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage(), 0);
        }
        
    }

    public Usuario validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }

    public void actualizarUsuario(Usuario usuario){
        daoUsuarios.actualizarUsuario(usuario);
    }

    public void borrarUsuario(Usuario usuario){
        daoUsuarios.borrarUsuario(usuario);
    }

    public void muestraExcepcion(String txtExcepcion){

    }


    public java.util.List<Libro> consultarCatalogo(Integer id, String titulo, String isbn, String autor){
        return daoLibros.consultarCatalogo(id, titulo, isbn, autor);
    }

    public Libro consultarLibro(Integer idLibro){
        return daoLibros.consultarLibro(idLibro);
    }
    public java.util.List<Ejemplar> consultarEjemplaresLibro(Integer idLibro){
        return daoLibros.consultarEjemplaresLibro(idLibro);
    }
    public java.util.List<String> obtenerRestoCategorias(Integer idLibro){
        return daoLibros.obtenerRestoCategorias(idLibro);
    }
    public Integer insertarLibro(Libro libro){
       return daoLibros.insertarLibro(libro);
    }
    public void borrarLibro(Integer idLibro){
        daoLibros.borrarLibro(idLibro);
    }
    public void modificarLibro(Libro libro){
         daoLibros.modificarLibro(libro);
    }
    public void modificarCategoriasLibro(Integer idLibro, java.util.List<String> categorias){
       daoLibros.modificarCategoriasLibro(idLibro, categorias);
    }
    public void insertarEjemplarLibro(Integer idLibro, Ejemplar ejemplar){
        daoLibros.insertarEjemplarLibro(idLibro, ejemplar);
    }
    public void borrarEjemplaresLibro(Integer idLibro, java.util.List<Integer> numsEjemplar){
        daoLibros.borrarEjemplaresLibro(idLibro, numsEjemplar);
    }
    public void modificarEjemplarLibro(Integer idLibro, Ejemplar ejemplar){
        daoLibros.modificarEjemplarLibro(idLibro, ejemplar);
    }


    //Consultar los usuarios para escribir el panel de gestion de usuarios
    public java.util.List<Usuario> consultarUsuarios() throws SQLException{
        return daoUsuarios.consultarUsuarios();
    }
    
    //Crear un nuevo usuario en la base de datos
    public String insertarUsuario(Usuario usuario){
        try {
            return daoUsuarios.crearUsuario(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(FachadaBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public java.util.List<Usuario> filtrarUsuarios(String id, String usuario) throws SQLException{
        try {
            return daoUsuarios.filtrarUsuarios(id, usuario);
        } catch (SQLException ex) {
            Logger.getLogger(FachadaBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return consultarUsuarios();
    }
   
    public java.util.List<Categoria> consultarCategorias(){
        return daoCategorias.consultarCategorias();
    }


    public void anhadirCategoria(Categoria categoria){
        daoCategorias.crearCategoria(categoria);
    }

    public void borrarCategoria(String nombre){
        daoCategorias.borrarCategoria(nombre);
    }

    public boolean consultarCategoriaEnUso(String categoria){
        return daoCategorias.comprobarCategoriaEnUso(categoria);
    }

    //Parte de prestamos

    public Date getFechaLimitePrestamo(String idUsuario, Integer idLibro, Integer numEjemplar, Date fecha_prestamo) {
        return daoPrestamos.getFechaLimitePrestamo(idUsuario, idLibro, numEjemplar, fecha_prestamo);
    }

    public java.util.List<Prestamo> consultarPrestamos(String idUsuario) {
        return daoPrestamos.consultarPrestamos(idUsuario);
    }

    public java.util.List<Prestamo> consultarPrestamos() {
        return daoPrestamos.consultarPrestamos();
    }

    public Prestamo consultarPrestamos(Integer idLibro, Integer numEjemplar) {
        return daoPrestamos.consultarPrestamos(idLibro, numEjemplar);
    }



    public boolean estaPrestado(Integer idLibro, Integer numEjemplar){
        return daoPrestamos.estaPrestado(idLibro, numEjemplar);
    }

    public java.util.List<Prestamo> consultarPrestamosVencidos(String idUsuario){
        return daoPrestamos.consultarVencidos(idUsuario);
    }

    public void introducirPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar){
        daoPrestamos.introducirPrestamo(idUsuario, idLibro, numEjemplar);
    }

    public void devolverPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar) {
        daoPrestamos.devolverPrestamo(idUsuario, idLibro, numEjemplar);
    }

}
