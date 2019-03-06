/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Categoria;
import aplicacion.FachadaAplicacion;
import aplicacion.Libro;
import gui.Controladores.*;

/**
 *
 * @author alumno
 */
public class FachadaGui {
    aplicacion.FachadaAplicacion fa;
    VPrincipalController cp;
    VLibroController vl;


    public FachadaGui(FachadaAplicacion fa, VPrincipalController cp){
        this.fa = fa;
        this.cp = cp;
    }

    public void iniciaVista(){
       VAutenticacionController va = new VAutenticacionController(fa, cp);
       va.display();
    }

    public void muestraExcepcion(String txtExcepcion, int tipo){
        VAvisoController va;
        va = new VAvisoController(txtExcepcion, tipo );
        va.display();
    }

    public void setUserLabel(String nombre){
        fa.getFgui().setUserLabel(nombre);
    }

    public void visualizaLibro(Libro l, java.util.List<String>restoCategorias){
        java.util.List<String> categorias = new java.util.ArrayList<String>();
        for(Categoria c:l.getCategorias()){
            categorias.add(c.getNombre());
        }
        
        vl=new VLibroController(fa, l, categorias, restoCategorias);
        vl.display(l);
    }

    public void nuevoLibro(java.util.List<String>  restoCategorias){
        vl=new VLibroController(fa, restoCategorias);
        vl.display();
    }

    //Parte de categorias
    public void verCategorias(){
        VGestionCategoriasController vgc = new VGestionCategoriasController(fa);
        vgc.display();
    }

    public void abrirPrestamos(){
        VPrestamosController vp = new VPrestamosController(fa, vl);
        vp.display();
    }
   
}
