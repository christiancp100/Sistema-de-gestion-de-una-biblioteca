/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aplicacion;
import gui.FachadaGui;
import baseDatos.FachadaBaseDatos;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author basesdatos
 */
public class GestionUsuarios {


     
    FachadaGui fgui;
    FachadaBaseDatos fbd;
    
   
    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd){
     this.fgui=fgui;
     this.fbd=fbd;
    }  
    
    
  public Boolean comprobarAutentificacion(String idUsuario, String clave){
      Usuario u;

      u=fbd.validarUsuario(idUsuario, clave);
      if (u!=null){
          return u.getTipoUsuario()==TipoUsuario.Administrador;
      } else return false;
  }


  //Metodos de obtencion, creacion y filtrado de usuarios

  public java.util.List<Usuario> obtenerUsuarios(){
        try {
            return fbd.consultarUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
  }
  
  public String crearUsuario(Usuario usuario){
      return fbd.insertarUsuario(usuario);
  }
  
  public java.util.List<Usuario> filtrarUsuarios(String id, String usuario) throws SQLException{
        try {
            return fbd.filtrarUsuarios(id, usuario);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fbd.consultarUsuarios();
  }

}
