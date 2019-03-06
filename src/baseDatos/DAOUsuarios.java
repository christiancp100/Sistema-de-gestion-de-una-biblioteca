/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package baseDatos;
import aplicacion.Usuario;
import aplicacion.TipoUsuario;
import java.sql.*;
import aplicacion.TipoUsuario;
/**
 *
 * @author basesdatos
 */
public class DAOUsuarios extends AbstractDAO {


   public DAOUsuarios (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Usuario validarUsuario(String idUsuario, String clave){
        Usuario resultado=null;
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();

        try {
        stmUsuario=con.prepareStatement("select id_usuario, clave, nombre, direccion, email, tipo_usuario "+
                                        "from usuario "+
                                        "where id_usuario = ? and clave = ?");
        stmUsuario.setString(1, idUsuario);
        stmUsuario.setString(2, clave);
        rsUsuario=stmUsuario.executeQuery();
        if (rsUsuario.next())
        {
            resultado = new Usuario(rsUsuario.getString("id_usuario"), rsUsuario.getString("clave"),
                                      rsUsuario.getString("nombre"), rsUsuario.getString("direccion"),
                                      rsUsuario.getString("email"), TipoUsuario.valueOf(rsUsuario.getString("tipo_usuario")));

        }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

    public java.util.List<Usuario> consultarUsuarios() throws SQLException{
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        con = this.getConexion();
        String tipoUsuario;
        
        java.util.List<Usuario> usuarios = new java.util.ArrayList<Usuario>();
        
        try{
            stmUsuario = con.prepareStatement("select * from usuario");
            rsUsuario = stmUsuario.executeQuery();
            while(rsUsuario.next()){
                tipoUsuario = rsUsuario.getString("tipo_usuario");
               usuarios.add(new Usuario(
                       rsUsuario.getString("id_usuario"),
                       rsUsuario.getString("clave"),
                       rsUsuario.getString("nombre"),
                       rsUsuario.getString("direccion"),
                       rsUsuario.getString("email"),
                       TipoUsuario.valueOf(rsUsuario.getString("tipo_usuario"))
               ));
            }
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{stmUsuario.close();}
        
        return usuarios;
    }
    
    public String crearUsuario(Usuario usuario) throws SQLException{
        Connection con = super.getConexion();
        PreparedStatement stmUsuario = null;
        try{
            stmUsuario = con.prepareStatement("INSERT INTO usuario"
                    + "(id_usuario, clave, nombre, direccion, email, tipo_usuario)" +
                    "VALUES (?,?,?,?,?,?)");
            stmUsuario.setString(1, usuario.getIdUsuario());
            stmUsuario.setString(2, usuario.getClave());
            stmUsuario.setString(3, usuario.getNombre());
            stmUsuario.setString(4, usuario.getDireccion());
            stmUsuario.setString(5, usuario.getEmail());
            stmUsuario.setString(6, usuario.getTipoUsuario().toString());
            
            stmUsuario.executeUpdate();


        }catch(SQLException e){
            System.out.println(e.getMessage() + e.getErrorCode());
            if(e.getErrorCode()==0){
                System.out.println("El id de este usuario ya está registrado en la base de datos");
            }
            
        }finally{stmUsuario.close();}
        
        return usuario.getIdUsuario();
    }
       
    
    public java.util.List<Usuario> filtrarUsuarios(String id, String usuario) throws SQLException{
        java.util.List<Usuario> usuarios = new java.util.ArrayList<Usuario>();
        Connection con = super.getConexion();
        ResultSet rsUsuario; 
        PreparedStatement stmUsuario = null;
        String busqueda = usuario;
        String datoUsado = null;
        String consulta = null; 
        
        
                
        if(!id.isEmpty()){
            datoUsado = "id_usuario";
            busqueda = id;
        }else if(id.isEmpty() && !usuario.isEmpty()){
            datoUsado = "nombre";
            busqueda = usuario;
        }
        else{
            System.out.println("No has introducido ningun parámetro de búsqueda");
            return this.consultarUsuarios();
        }
        try{
            consulta = "SELECT * FROM usuario WHERE $tabla LIKE ?";
            consulta = consulta.replace("$tabla", datoUsado);
            stmUsuario = con.prepareStatement(consulta);
            stmUsuario.setString(1, "%" + busqueda + "%");

            rsUsuario = stmUsuario.executeQuery();
            while(rsUsuario.next()){
               usuarios.add(new Usuario(
                       rsUsuario.getString("id_usuario"),
                       rsUsuario.getString("clave"),
                       rsUsuario.getString("nombre"),
                       rsUsuario.getString("direccion"),
                       rsUsuario.getString("email"),
                       TipoUsuario.valueOf(rsUsuario.getString("tipo_usuario"))
               ));
            }
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{stmUsuario.close();}

        
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario){
        Connection con;
        PreparedStatement stmEjemplar=null;

        con=super.getConexion();

        try {
            stmEjemplar=con.prepareStatement("update usuario "+
                    "set"+
                    "   clave=?, "+
                    "   nombre = ?,"+
                    "   direccion=?,"+
                    "   email=?,"+
                    "   tipo_usuario=?"+
                    "where id_usuario=?");
            stmEjemplar.setString(1, usuario.getClave());
            stmEjemplar.setString(2, usuario.getNombre());
            stmEjemplar.setString(3, usuario.getDireccion());
            stmEjemplar.setString(4, usuario.getEmail());
            stmEjemplar.setString(5, usuario.getTipoStr());
            stmEjemplar.setString(6, usuario.getIdUsuario());
            stmEjemplar.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
            try {stmEjemplar.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void borrarUsuario(Usuario usuario){
        Connection con;
        PreparedStatement stmEjemplar=null;

        con=super.getConexion();

        try {
            stmEjemplar=con.prepareStatement("delete from usuario where id_usuario=?");
            stmEjemplar.setString(1, usuario.getIdUsuario());
            stmEjemplar.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage(), 0);
        }finally{
            try {stmEjemplar.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

}
