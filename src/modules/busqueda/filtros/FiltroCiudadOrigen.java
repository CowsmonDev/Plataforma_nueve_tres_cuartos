package modules.busqueda.filtros;

import data.empresas.Viaje;

public class FiltroCiudadOrigen implements Filtros<Viaje>{
    private String ciudad;

    public FiltroCiudadOrigen(String ciudad){
        this.ciudad = ciudad;
    }

    @Override
    public boolean filtrar(Viaje element) {
        return element.getOrigen().equals(this.ciudad);
    }
}
