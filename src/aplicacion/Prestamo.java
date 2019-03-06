package aplicacion;

import java.sql.Date;

public class Prestamo {

    private String idUsaurio;
    private Integer idLibro;
    private Integer numEjemplar;
    private Date fecha_prestamo;
    private Date fecha_devolucion;

    public Prestamo(String idUsaurio, Integer idLibro, Integer numEjemplar, Date fecha_prestamo, Date fecha_devolucion) {
        this.idUsaurio = idUsaurio;
        this.idLibro = idLibro;
        this.numEjemplar = numEjemplar;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getIdUsaurio() {
        return idUsaurio;
    }

    public void setIdUsaurio(String idUsaurio) {
        this.idUsaurio = idUsaurio;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getNumEjemplar() {
        return numEjemplar;
    }

    public void setNumEjemplar(Integer numEjemplar) {
        this.numEjemplar = numEjemplar;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }
}
