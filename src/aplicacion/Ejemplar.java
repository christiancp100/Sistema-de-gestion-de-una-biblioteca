/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aplicacion;

import aplicacion.Libro;

import java.sql.Date;

/**
 *
 * @author basesdatos
 */
public class Ejemplar {

    private Integer numEjemplar;
    private String localizador;
    private String anoCompra;
    private Libro libro;
    private String usuario;
    private Date fechaDev;

    public Ejemplar(Libro libro, Integer numEjemplar, String localizador, String anoCompra){
        this.numEjemplar=numEjemplar;
        this.anoCompra=anoCompra;
        this.localizador=localizador;
        this.libro=libro;
        this.usuario = null;
        this.fechaDev = null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaDev() {
        return fechaDev;
    }

    public void setFechaDev(Date fechaDev) {
        this.fechaDev = fechaDev;
    }

    public Integer getNumEjemplar(){
        return this.numEjemplar;
    }

    public String getAnoCompra(){
        return this.anoCompra;
    }

    public String getLocalizador(){
        return this.localizador;
    }

    public Libro getLibro(){
        return this.libro;
    }

    public void setLocalizador(String l){
        localizador =l;
    }
    
    public void setAnoCompra(String a){
        anoCompra = a;
    }
    
}
