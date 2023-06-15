package modules.busqueda.filtros;

import data.empresas.estructura.empresa.Empresa;

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
