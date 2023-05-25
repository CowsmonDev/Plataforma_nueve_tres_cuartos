package data.busqueda.filtros;

import data.empresas.Empresa;

public class FiltroNombreEmpresa implements Filtros<Empresa> {

    private String nombre;

    public FiltroNombreEmpresa(String nombre){
        this.nombre = nombre;
    }

    @Override
    public boolean filtrar(Empresa element) {
        return element.getNombre().equals(this.nombre);
    }
}
