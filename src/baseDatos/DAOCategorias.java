/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baseDatos;
import aplicacion.Categoria;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author basesdatos
 */
public class DAOCategorias extends AbstractDAO {
   

    public DAOCategorias (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Categoria> consultarCategorias(){
        java.util.List<Categoria> resultado = new java.util.ArrayList<Categoria>();
        Categoria categoriaActual;
        Connection con;
        PreparedStatement stmCategorias=null;
        ResultSet rsCategorias;

        con=this.getConexion();

        try  {
        stmCategorias=con.prepareStatement("select nombre, descripcion from categoria");
        rsCategorias=stmCategorias.executeQuery();
        while (rsCategorias.next())
        {
            categoriaActual = new Categoria(rsCategorias.getString("nombre"), rsCategorias.getString("descripcion"));
            resultado.add(categoriaActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
          try {stmCategorias.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

    public void crearCategoria(Categoria categoria){
        Connection con;
        PreparedStatement stmCategorias=null;

        con=this.getConexion();

        try  {
            stmCategorias=con.prepareStatement("INSERT INTO categoria"
                    + "(nombre, descripcion)" +
                    "VALUES (?,?)");
            stmCategorias.setString(1, categoria.getNombre());
            stmCategorias.setString(2, categoria.getDescripcion());
            stmCategorias.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
            try {stmCategorias.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return;
    }

    public void borrarCategoria(String nombre){
        //System.out.println("el nombre es " + nombre);
        Connection con;
        PreparedStatement stmCategorias=null;

        con=this.getConexion();

        try  {
            stmCategorias=con.prepareStatement("delete from categoria where nombre = ?");
            stmCategorias.setString(1, nombre);
            stmCategorias.executeQuery();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
            try {stmCategorias.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return;
    }

    public boolean comprobarCategoriaEnUso(String nombre){
        Connection con;
        PreparedStatement stmCategorias=null;
        ResultSet numeroCategorias;
        int cuenta = 0;
        con=this.getConexion();
        System.out.println(nombre);
        try  {
            stmCategorias=con.prepareStatement("select COUNT(*) from cat_tiene_libro where categoria = ?");
            stmCategorias.setString(1, nombre);
            numeroCategorias = stmCategorias.executeQuery();

            while(numeroCategorias.next()){
              cuenta = numeroCategorias.getInt(1);
            }
            if(cuenta > 0)
            {
                //System.out.println("Comprobado que existe");
                return true;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
            try {stmCategorias.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        //System.out.println("Parece que no existe " + cuenta );
        return false;
    }
}
