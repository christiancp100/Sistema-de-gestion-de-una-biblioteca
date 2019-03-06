package baseDatos;

import aplicacion.Ejemplar;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import aplicacion.Prestamo;

public class DAOPrestamos extends AbstractDAO {


    public DAOPrestamos (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }


    public java.util.List<Prestamo> consultarPrestamos(String idUsuario){
        java.util.List<Prestamo> resultado=new java.util.ArrayList<Prestamo>();
        Connection con;
        PreparedStatement stmPrestamos=null;
        ResultSet rsPrestamos;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "select libro, ejemplar, fecha_prestamo, fecha_devolucion "+
                    "from prestamo "+
                    "where usuario =? ");
            stmPrestamos.setString(1,idUsuario);
            rsPrestamos=stmPrestamos.executeQuery();
            while (rsPrestamos.next())
            {
                resultado.add(
                    new Prestamo(idUsuario,
                        rsPrestamos.getInt("libro"),
                        rsPrestamos.getInt("ejemplar"),
                        rsPrestamos.getDate("fecha_prestamo"),
                        rsPrestamos.getDate("fecha_devolucion"))
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }

    public java.util.List<Prestamo> consultarPrestamos(){
        java.util.List<Prestamo> resultado=new java.util.ArrayList<Prestamo>();
        Connection con;
        PreparedStatement stmPrestamos=null;
        ResultSet rsPrestamos;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "select * from prestamo where fecha_devolucion is null ");
            rsPrestamos=stmPrestamos.executeQuery();
            while (rsPrestamos.next())
            {
                resultado.add(
                        new Prestamo(rsPrestamos.getString("usuario"),
                                rsPrestamos.getInt("libro"),
                                rsPrestamos.getInt("ejemplar"),
                                rsPrestamos.getDate("fecha_prestamo"),
                                rsPrestamos.getDate("fecha_devolucion"))
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }


    public boolean estaPrestado(Integer idLibro, Integer numEjemplar){
        java.util.List<Prestamo> resultado = new ArrayList<>();
        Connection con;
        PreparedStatement stmPrestamos=null;
        ResultSet rsPrestamos;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "select * from prestamo where libro= ? AND ejemplar = ? AND fecha_devolucion IS null");

            stmPrestamos.setInt(1, idLibro);
            stmPrestamos.setInt(2, numEjemplar);
            rsPrestamos=stmPrestamos.executeQuery();
            while (rsPrestamos.next())
            {
                resultado.add(
                        new Prestamo(rsPrestamos.getString("usuario"),
                                rsPrestamos.getInt("libro"),
                                rsPrestamos.getInt("ejemplar"),
                                rsPrestamos.getDate("fecha_prestamo"),
                                rsPrestamos.getDate("fecha_devolucion"))
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        if(resultado.size() > 0){
            return true;
        }
        return false;
    }


    public Prestamo consultarPrestamos(Integer idLibro, Integer numEjemplar){
        Connection con;
        PreparedStatement stmPrestamos=null;
        ResultSet rsPrestamos;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "select * from prestamo where libro= ? AND ejemplar = ? AND fecha_devolucion IS null ");

            stmPrestamos.setInt(1, idLibro);
            stmPrestamos.setInt(2, numEjemplar);
            rsPrestamos=stmPrestamos.executeQuery();
            while (rsPrestamos.next())
            {
                return(
                        new Prestamo(rsPrestamos.getString("usuario"),
                                rsPrestamos.getInt("libro"),
                                rsPrestamos.getInt("ejemplar"),
                                rsPrestamos.getDate("fecha_prestamo"),
                                rsPrestamos.getDate("fecha_devolucion"))
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return null;
    }



    public java.sql.Date getFechaLimitePrestamo(String idUsuario, Integer idLibro, Integer numEjemplar, Date fecha_prestamo){
        java.util.List<Prestamo> resultado=new java.util.ArrayList<Prestamo>();
        Connection con;
        PreparedStatement stmPrestamos=null;
        ResultSet rsPrestamo;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement("select fecha_prestamo + 30 AS fecha "+
                    "from prestamo "+
                    "where usuario = ? AND libro = ? AND ejemplar = ? AND fecha_prestamo = ? " +
                    "AND fecha_devolucion IS null");
            stmPrestamos.setString(1,idUsuario);
            stmPrestamos.setInt(2,idLibro);
            stmPrestamos.setInt(3,numEjemplar);
            stmPrestamos.setDate(4,fecha_prestamo);

            rsPrestamo=stmPrestamos.executeQuery();
            if (rsPrestamo.next())
            {
                return(rsPrestamo.getDate("fecha"));
            }


        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return null;
    }

    public java.util.List<Prestamo> consultarVencidos(String idUsuario){

            java.util.List<Prestamo> resultado=new java.util.ArrayList<Prestamo>();
            Connection con;
            PreparedStatement stmPrestamos=null;
            ResultSet rsPrestamos;

            con=super.getConexion();

            try {
                stmPrestamos =con.prepareStatement(
                        "select libro, ejemplar, fecha_prestamo, fecha_devolucion "+
                                "from prestamo "+
                                "where usuario =? " +
                                "AND (fecha_prestamo+30) < now()" +
                                "AND fecha_devolucion is null ");
                stmPrestamos.setString(1,idUsuario);
                rsPrestamos=stmPrestamos.executeQuery();
                while (rsPrestamos.next())
                {
                    resultado.add(
                            new Prestamo(idUsuario,
                                    rsPrestamos.getInt("libro"),
                                    rsPrestamos.getInt("ejemplar"),
                                    rsPrestamos.getDate("fecha_prestamo"),
                                    rsPrestamos.getDate("fecha_devolucion"))
                    );
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }finally{
                try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
            return resultado;
    }

    public void introducirPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar){
        Connection con;
        PreparedStatement stmPrestamos=null;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "insert into prestamo (usuario, libro, ejemplar, fecha_prestamo, fecha_devolucion) \n" +
                            "values (?, ?, ?, now(), null)");
            stmPrestamos.setString(1,idUsuario);
            stmPrestamos.setInt(2,idLibro);
            stmPrestamos.setInt(3,numEjemplar);

            stmPrestamos.executeQuery();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

    }
    public void devolverPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar){
        Connection con;
        PreparedStatement stmPrestamos=null;

        con=super.getConexion();

        try {
            stmPrestamos =con.prepareStatement(
                    "UPDATE prestamo SET fecha_devolucion=now() " +
                            "WHERE usuario=?" +
                            "AND libro=?" +
                            "AND ejemplar=?");
            stmPrestamos.setString(1,idUsuario);
            stmPrestamos.setInt(2,idLibro);
            stmPrestamos.setInt(3,numEjemplar);

            stmPrestamos.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
            try {stmPrestamos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

}
