package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

import java.sql.Date;

public class GestionPrestamos {

    private FachadaBaseDatos fbd;
    private FachadaGui fgui;

    public GestionPrestamos(FachadaBaseDatos fbd, FachadaGui fgui) {
        this.fbd = fbd;
        this.fgui = fgui;
    }


    public Date getFechaLimitePrestamo(String idUsuario, Integer idLibro, Integer numEjemplar, Date fecha_prestamo) {
        return fbd.getFechaLimitePrestamo(idUsuario, idLibro, numEjemplar, fecha_prestamo);
    }

    public java.util.List<Prestamo> consultarPrestamos(String idUsuario){
        return fbd.consultarPrestamos(idUsuario);
    }
    public java.util.List<Prestamo> consultarPrestamos(){
        return fbd.consultarPrestamos();
    }

    public Prestamo consultarPrestamos(Integer idLibro, Integer numEjemplar) {
        return fbd.consultarPrestamos(idLibro, numEjemplar);
    }


    public java.util.List<Prestamo> consultarPrestamosVencidos(String idUsuario){
        return fbd.consultarPrestamosVencidos(idUsuario);
    }

    public void introducirPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar){
        fbd.introducirPrestamo(idUsuario, idLibro, numEjemplar);
    }


    public void devolverPrestamo(String idUsuario, Integer idLibro, Integer numEjemplar) {
        fbd.devolverPrestamo(idUsuario, idLibro, numEjemplar);
    }

    public boolean estaPrestado(Integer idLibro, Integer numEjemplar){
        return fbd.estaPrestado(idLibro, numEjemplar);
    }


}
