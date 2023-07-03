package modules.busqueda.filtros;

import data.empresas.estructura.viaje.Viaje;

public class FiltroCiudadDestino implements Filtros<Viaje>{
    private String ciudad;

    public FiltroCiudadDestino(String ciudad){
        this.ciudad = ciudad;
    }

    @Override
    public boolean filtrar(Viaje element) {
        return element.getDestino().equals(this.ciudad);
    }
}
