package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

import java.util.ArrayList;

public class GestionCategorias {

    FachadaBaseDatos fbd;
    FachadaGui fgui;

    public GestionCategorias(FachadaBaseDatos fbd, FachadaGui fgui){
        this.fbd = fbd;
        this.fgui = fgui;
    }

    public void verCategorias(){
        fgui.verCategorias();
    }

    public java.util.ArrayList<Categoria> obtenerCategorias(){
        return new ArrayList<>(fbd.consultarCategorias());
    }

    public void anhadirCategoria(Categoria categoria){
        fbd.anhadirCategoria(categoria);
    }

    public void borrarCategoria(String nombre){
        fbd.borrarCategoria(nombre);
    }

    public boolean consultarCategoriaEnUso(String categoria){
        return fbd.consultarCategoriaEnUso(categoria);
    }
}
